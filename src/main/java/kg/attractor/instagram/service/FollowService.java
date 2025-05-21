package kg.attractor.instagram.service;

public interface FollowService {
    long countFollowers(Long userId);
    long countFollowing(Long userId);
}
