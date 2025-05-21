package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.CommentDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    @Transactional(readOnly = true)
    List<CommentDto> getPostComments(Long postId);

    @Transactional(readOnly = true)
    Long getPostCommentsCount(Long postId);

    @Transactional
    CommentDto addComment(Long postId, Long userId, String content);

    @Transactional
    void deleteComment(Long commentId, Long userId);
}
