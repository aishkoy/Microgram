package kg.attractor.instagram.repository;

import kg.attractor.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.avatar FROM User u WHERE u.id = :id")
    Optional<String> findAvatarById(@Param("id") Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
