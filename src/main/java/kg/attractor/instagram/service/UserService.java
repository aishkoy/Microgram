package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    boolean existsUsername(String username);

    boolean existsEmail(String email);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    void registerUser(CreateUserDto createUserDto);

    UserDto findByUsername(String username);

    void updateUser(String username, UserDto dto);

    UserDto getAuthUser();

    Long getAuthId();

    ResponseEntity<?> getAvatarByUserId(Long userId);

    List<UserDto> searchUsers(String query);
}