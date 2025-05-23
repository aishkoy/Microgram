package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.CommentDto;
import kg.attractor.instagram.entity.Comment;
import kg.attractor.instagram.exception.nsee.CommentNotFoundException;
import kg.attractor.instagram.mapper.CommentMapper;
import kg.attractor.instagram.repository.CommentRepository;
import kg.attractor.instagram.service.CommentService;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final PostService postService;

    @Override
    public Boolean hasAccessToComment(Long commentId, Long userId) {
        return commentRepository.existsByIdAndUserId(commentId, userId);
    }

    private Boolean isPostOwner(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий не найден"));

        return postService.hasAccessToPost(comment.getPost().getId(), userId);
    }

    @Override
    public List<CommentDto> getPostComments(Long postId) {
        List<CommentDto> comments = commentRepository.findByPostIdOrderByIdDesc(postId)
                .stream().map(commentMapper::toDto)
                .toList();

        if (comments.isEmpty()) {
            throw new CommentNotFoundException("Комментарии этого поста не были найдены!");
        }
        log.info("Получено комментариев: {}", comments.size());
        return comments;
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий не найден"));
        log.info("Получен комментарий с id {}", comment.getId());
        return commentMapper.toDto(comment);
    }

    @Override
    public Long getPostCommentsCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Transactional
    @Override
    public CommentDto addComment(Long postId, Long userId, String content) {
        postService.getPostById(postId);

        CommentDto dto = CommentDto.builder()
                .user(userService.getUserById(userId))
                .post(postService.getPostById(postId))
                .content(content)
                .build();

        Comment savedComment = commentRepository.save(commentMapper.toEntity(dto));
        log.info("Создан комментарий с id {} для поста {}", savedComment.getId(), postId);
        return commentMapper.toDto(savedComment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId, Long userId) {
        CommentDto comment = getCommentById(commentId);

        Boolean isCommentOwner = hasAccessToComment(commentId, userId);
        Boolean isPostOwner = isPostOwner(commentId, userId);

        if (Boolean.TRUE.equals(isCommentOwner) || Boolean.TRUE.equals(isPostOwner)) {
            commentRepository.deleteById(commentId);
            log.info("Удален комментарий с id {} пользователем {}", commentId, userId);
        } else {
            throw new AccessDeniedException("У вас нет права удалять этот комментарий!");
        }
    }
}