package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "WHERE LOWER(u.name) LIKE LOWER(CONCAT(:query, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT(:query, '%')) " +
            "OR LOWER(u.username) LIKE LOWER(CONCAT(:query, '%'))")
    List<User> searchUsers(@Param("query") String query);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.avatar FROM User u WHERE u.id = :id")
    Optional<String> findAvatarById(@Param("id") Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
