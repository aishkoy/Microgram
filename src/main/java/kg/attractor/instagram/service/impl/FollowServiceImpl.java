package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.FollowDto;
import kg.attractor.instagram.entity.Follow;
import kg.attractor.instagram.exception.nsee.FollowNotFoundException;
import kg.attractor.instagram.mapper.FollowMapper;
import kg.attractor.instagram.repository.FollowRepository;
import kg.attractor.instagram.service.FollowService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final FollowMapper followMapper;
    private final UserService userService;

    @Override
    public void followUser(Long followerId, Long followedId) {
        FollowDto dto = FollowDto.builder()
                .follower(userService.getUserById(followerId))
                .following(userService.getUserById(followedId))
                .build();

        Follow entity = followMapper.toEntity(dto);
        followRepository.save(entity);
        log.info("Пользователь {} подписался на пользователя {}", followerId, followedId);
    }

    @Override
    public FollowDto getFollow(Long followerId, Long followedId) {
        Follow follow = followRepository.findByFollower_idAndFollowing_id(followerId, followedId)
                .orElseThrow(() -> new FollowNotFoundException("Пользователь " + followerId + " не подписан на пользователя " + followedId));
        log.info("Получена подписка на пользователя {} по идентификатору {}", followedId, follow.getId());
        return followMapper.toDto(follow);
    }

    @Override
    public void unfollowUser(Long followerId, Long followedId) {
        FollowDto dto = getFollow(followerId, followedId);
        Follow entity = followMapper.toEntity(dto);
        followRepository.delete(entity);

        log.info("Пользователь {} отписался от пользователя {}", followerId, followedId);
    }
}
