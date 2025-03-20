package kg.attractor.microgram.dao;

import kg.attractor.microgram.models.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FollowDao {
	private final JdbcTemplate template;
	
	public Optional<Follow> findById(Long followId) {
		String sql = "SELECT * FROM follows WHERE id = ?";
		return Optional.ofNullable(
				DataAccessUtils.singleResult(
						template.query(sql, new BeanPropertyRowMapper<>(Follow.class), followId)
				));
	}
	
	public List<Follow> findAll() {
		return template.query("select * from follows", new BeanPropertyRowMapper<>(Follow.class));
	}
	
	public void addFollow(Follow follow) {
		String sql = "INSERT INTO follows (follower, actual_user) VALUES (?, ?)";
		template.update(sql, follow.getFollowerEmail(), follow.getActualUser());
	}
	
	public void removeFollow(String follower, String actualUser) {
		String sql = "DELETE FROM follows WHERE FOLLOWER = ? and ACTUAL_USER = ?";
		template.update(sql, follower, actualUser);
	}

    public List<Follow> getUserFollowing(String email) {
		String sql = "SELECT * FROM FOLLOWS WHERE FOLLOWER = ?";
		return template.query(sql, new BeanPropertyRowMapper<>(Follow.class), email);
    }
}
