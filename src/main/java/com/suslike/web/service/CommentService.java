package com.suslike.web.service;

import com.suslike.web.dao.CommentDao;
import com.suslike.web.dto.comments.CommentCreateDto;
import com.suslike.web.dto.comments.CommentDto;
import com.suslike.web.models.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao dao;
    private final UserService userService;

    public List<CommentDto> getAllCommentsByPostId(Long id) {
        return dao.getCommentsByPostId(id).stream().map(this::toDto).toList();
    }
    private CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .post(comment.getPost())
                .commenter(userService.getUserById(comment.getCommenter()))
                .content(comment.getContent())
                .commentedTime(comment.getCommentedTime())
                .build();
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        return dao.getCommentsByPostId(postId).stream()
                .map(this::toDto)
                .toList();
    }

    public Long addComment(CommentCreateDto dto, Long id) {
        log.info("user commented to post {}", dto.getPostId());
        return dao.addComment(
                Comment.builder()
                        .post(dto.getPostId())
                        .content(dto.getContent())
                        .commenter(id)
                        .commentedTime(LocalDateTime.now())
                        .build()
        );
    }

    public void deleteComment(Long id, Long commenterId) {
        if (dao.getCommentById(id).getCommenter().equals(commenterId)) {
            dao.deleteComment(id);
            log.info("Deleted comment with ID {}", id);
        }
        log.info("Couldn't delete comment with ID {} cause is not belongs to user {}", id, commenterId);
    }

    public CommentDto getLastCommentByPost(Long lastId) {
        Comment comments = dao.getNewComments(lastId).orElseThrow(NoSuchElementException::new);
        return toDto(comments);
    }
}
