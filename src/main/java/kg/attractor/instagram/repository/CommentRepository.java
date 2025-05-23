package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByIdDesc(Long postId);

    Optional<Comment> findByIdAndUserId(Long id, Long userId);

    Long countByPostId(Long postId);

    boolean existsByIdAndUserId(Long id, Long userId);

    int deleteByPostId(Long postId);

}