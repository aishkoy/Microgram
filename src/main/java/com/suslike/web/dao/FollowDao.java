package com.suslike.web.dao;

import com.suslike.web.models.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class FollowDao {
	private final JdbcTemplate template;

	public int countFollowers(Long userId) {
		String sql = "SELECT COUNT(*) FROM follows WHERE actual_user = ?";
		return template.queryForObject(sql, Integer.class, userId);
	}

	public int countFollowing(Long userId) {
		String sql = "SELECT COUNT(*) FROM follows WHERE follower = ?";
		return template.queryForObject(sql, Integer.class, userId);
	}
	
	public void addFollow(Follow follow) {
		String sql = "INSERT INTO follows (follower, actual_user) VALUES (?, ?)";
		template.update(sql, follow.getFollower(), follow.getActualUser());
	}
	
	public void removeFollow(Long follower, Long actualUser) {
		String sql = "DELETE FROM follows WHERE FOLLOWER = ? and ACTUAL_USER = ?";
		template.update(sql, follower, actualUser);
	}

    public List<Follow> getUserFollowing(Long follower) {
		String sql = "SELECT * FROM FOLLOWS WHERE FOLLOWER = ?";
		return template.query(sql, new BeanPropertyRowMapper<>(Follow.class), follower);
    }
}
