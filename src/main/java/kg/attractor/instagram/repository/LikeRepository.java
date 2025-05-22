package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.Like;
import kg.attractor.instagram.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    boolean existsByPost_IdAndUser_Id(Long postId, Long userId);
    Long countByIdPostId(Long postId);
}