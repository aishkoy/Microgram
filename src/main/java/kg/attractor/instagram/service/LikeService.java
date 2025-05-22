package kg.attractor.instagram.service;

import org.springframework.transaction.annotation.Transactional;

public interface LikeService {
    boolean isPostLikedByUser(Long postId, Long userId);

    Long getPostLikesCount(Long postId);

    @Transactional
    void toggleLike(Long postId, Long userId);
}
