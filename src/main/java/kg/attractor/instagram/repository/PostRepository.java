package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    @Query("SELECT p FROM Post p " +
            "JOIN Follow f ON p.user.id = f.following.id " +
            "WHERE f.follower.id = :userId " +
            "ORDER BY p.createdAt DESC")
    List<Post> findPostsByFollowedUsers(@Param("userId") Long userId);


    boolean existsByIdAndUser_Id(Long id, Long userId);
}