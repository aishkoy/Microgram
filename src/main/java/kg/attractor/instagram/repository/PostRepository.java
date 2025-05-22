package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    List<Post> findAllByOrderByCreatedAtDesc();

    boolean existsByIdAndUser_Id(Long id, Long userId);
}