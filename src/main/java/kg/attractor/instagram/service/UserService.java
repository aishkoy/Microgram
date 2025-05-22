package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    boolean existsUsername(String username);

    boolean existsEmail(String email);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    void registerUser(CreateUserDto createUserDto);

    UserDto findByUsername(String username);

    @Transactional
    void updateUser(UserDto dto);

    UserDto getAuthUser();

    Long getAuthId();

    boolean isCurrentUser(Long userId);

    ResponseEntity<?> getAvatarByUserId(Long userId);

    List<UserDto> searchUsers(String query);

    void uploadAvatar(MultipartFile file, Long userId);
}