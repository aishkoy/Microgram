package kg.attractor.microgram.service;

import kg.attractor.microgram.dao.FollowDao;
import kg.attractor.microgram.dto.FollowDto;
import kg.attractor.microgram.models.Follow;
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
	
	public List<FollowDto> findAll() {
		return dao.findAll().stream()
				.map(this :: toDto)
				.collect(toList());
	}
	
	public FollowDto findById(Long followId) {
		if (dao.findById(followId).isEmpty())
			throw new NoSuchElementException("follow with id " + followId + " not found");
		return toDto(dao.findById(followId).get());
	}
	
	public void createFollow(FollowDto dto) {
		dao.addFollow(Follow.builder()
				.followerEmail(dto.getFollowerEmail())
				.actualUser(dto.getActualUser())
				.build());
		log.info("Follow created: {}", dto);
	}

	public List<FollowDto> getUserFollowing (String email) {
		List<Follow> following = dao.getUserFollowing (email);
		return following.stream().map(this::toDto).toList();
	}
	
	public void deleteFollow(FollowDto dto) {
		dao.removeFollow(dto.getFollowerEmail(), dto.getActualUser());
	}
	
	private FollowDto toDto(Follow follow) {
		return FollowDto.builder()
				.id(follow.getId())
				.followerEmail(follow.getFollowerEmail())
				.actualUser(follow.getActualUser())
				.build();
	}
}
