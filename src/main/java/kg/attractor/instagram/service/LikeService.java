package kg.attractor.instagram.service;

import kg.attractor.instagram.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikeService {
    @Transactional(readOnly = true)
    boolean isPostLikedByUser(Long postId, Long userId);

    @Transactional(readOnly = true)
    Long getPostLikesCount(Long postId);

    @Transactional
    void toggleLike(Long postId, Long userId);

    @Transactional(readOnly = true)
    List<User> getPostLikers(Long postId);
}
