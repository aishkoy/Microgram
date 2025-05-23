package kg.attractor.instagram.service;


import kg.attractor.instagram.dto.FollowDto;

public interface FollowService {
    boolean isUserFollowed(Long followerId, Long followedId);

    void followUser(Long followerId, Long followedId);

    void toggleFollow(Long followerId, Long followedId, boolean isFollow);

    FollowDto getFollow(Long followerId, Long followedId);

    void unfollowUser(Long followerId, Long followedId);
}
