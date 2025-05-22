package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.Follow;
import kg.attractor.instagram.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    Optional<Follow> findByFollower_idAndFollowing_id(Long followerId, Long followingId);
}
