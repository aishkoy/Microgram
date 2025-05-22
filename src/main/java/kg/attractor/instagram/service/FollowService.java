package kg.attractor.instagram.service;


import kg.attractor.instagram.dto.FollowDto;

public interface FollowService {
    void followUser(Long followerId, Long followedId);

    FollowDto getFollow(Long followerId, Long followedId);

    void unfollowUser(Long followerId, Long followedId);
}
