package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.EditUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean existsUsername(String username);
    boolean existsEmail(String email);
    void registerUser(CreateUserDto createUserDto);
    EditUserDto getEditUserDto(String username);
    UserDto findByUsername(String username);
    void updateUserWithAvatar(String username, EditUserDto dto, MultipartFile avatarFile);
    ResponseEntity<?> getAvatarByUserId(Long userId);
}