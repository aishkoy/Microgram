package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.Follow;
import kg.attractor.instagram.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
}
