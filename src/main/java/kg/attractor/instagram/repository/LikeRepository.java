package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.Like;
import kg.attractor.instagram.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    boolean existByPostIdAndUserId(Long postId, Long userId);
    List<Like> findByIdPostId(Long postId);
    Long countByIdPostId(Long postId);
}