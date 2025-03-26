package com.suslike.web.service;

import com.suslike.web.dao.FollowDao;
import com.suslike.web.dto.FollowDto;
import com.suslike.web.models.Follow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {
	private final FollowDao dao;

	public int getFollowersCount(Long userId) {
		return dao.countFollowers(userId);
	}

	public int getFollowingCount(Long userId) {
		return dao.countFollowing(userId);
	}
	
	public void createFollow(FollowDto dto) {
		dao.addFollow(Follow.builder()
				.follower(dto.getFollower())
				.actualUser(dto.getActualUser())
				.build());
		log.info("Follow created: {}", dto);
	}

	public List<FollowDto> getUserFollowing (Long id) {
		List<Follow> following = dao.getUserFollowing(id);
		return following.stream().map(this::toDto).toList();
	}
	
	public void deleteFollow(FollowDto dto) {
		dao.removeFollow(dto.getFollower(), dto.getActualUser());
	}
	
	private FollowDto toDto(Follow follow) {
		return FollowDto.builder()
				.id(follow.getId())
				.follower(follow.getFollower())
				.actualUser(follow.getActualUser())
				.build();
	}
}
