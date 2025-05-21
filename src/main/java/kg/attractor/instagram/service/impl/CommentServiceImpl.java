package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.CommentDto;
import kg.attractor.instagram.entity.Comment;
import kg.attractor.instagram.entity.Post;
import kg.attractor.instagram.entity.User;
import kg.attractor.instagram.mapper.CommentMapper;
import kg.attractor.instagram.repository.CommentRepository;
import kg.attractor.instagram.repository.PostRepository;
import kg.attractor.instagram.repository.UserRepository;
import kg.attractor.instagram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getPostComments(Long postId) {
        return commentRepository.findByPostIdOrderByIdDesc(postId).stream()
                .map(comment -> {
                    CommentDto dto = commentMapper.toDto(comment);
                    dto.setUsername(comment.getUser().getUsername());
                    dto.setAvatar("/api/users/" + comment.getUser().getId() + "/avatar");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Long getPostCommentsCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Transactional
    @Override
    public CommentDto addComment(Long postId, Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Пост не найден"));

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();

        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = commentMapper.toDto(savedComment);
        dto.setUsername(user.getUsername());
        dto.setAvatar("/api/users/" + user.getId() + "/avatar");
        return dto;
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Комментарий не найден"));

        if (!comment.getUser().getId().equals(userId) &&
            !comment.getPost().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("У вас нет прав для удаления этого комментария");
        }

        commentRepository.delete(comment);
    }
}