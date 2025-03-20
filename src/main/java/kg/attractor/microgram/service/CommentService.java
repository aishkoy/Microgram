package kg.attractor.microgram.service;

import kg.attractor.microgram.dao.CommentDao;
import kg.attractor.microgram.dto.comments.CommentCreateDto;
import kg.attractor.microgram.dto.comments.CommentDto;
import kg.attractor.microgram.models.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao dao;
    private final UserService userService;

    private CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .commenter(userService.getUserByEmail(comment.getCommenter()))
                .content(comment.getContent())
                .commentedTime(comment.getCommentedTime())
                .build();
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        return dao.getCommentsByPostId(postId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CommentDto getCommentById(Long id) {
        Comment comment = dao.getCommentById(id);
        return this.toDto(comment);
    }


    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = dao.getCommentById(commentDto.getId());
        if (comment != null) {
            comment.setContent(commentDto.getContent());
            comment.setCommentedTime(LocalDateTime.now());
            dao.updateComment(comment);
            log.info("Updated comment with ID {}", commentDto.getId());
            return this.toDto(comment);
        } else {
            log.error("Comment with ID {} not found", commentDto.getId());
            return null;
        }
    }

    public Long addComment(CommentCreateDto dto, String email) {
        log.info("user commented to post " + dto.getPostId());
        return dao.addComment(
                Comment.builder()
                        .postId(dto.getPostId())
                        .content(dto.getContent())
                        .commenter(email)
                        .commentedTime(LocalDateTime.now())
                        .build()
        );
    }

    public void deleteComment(Long id, String email) {
        if (dao.getCommentById(id).getCommenter().equals(email)) {
            dao.deleteComment(id);
            log.info("Deleted comment with ID {}", id);
        }
            log.info("Couldn't delete comment with ID {} cause is not belongs to user "+email, id);

    }

    public CommentDto getCommentsLastCommentsByPost(Long lastId) {
        Comment comments = dao.getNewComments(lastId).orElseThrow(NoSuchElementException::new);
        return toDto(comments);
    }
}
