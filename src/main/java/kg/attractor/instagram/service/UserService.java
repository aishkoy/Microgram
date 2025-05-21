package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.DisplayUserDto;
import kg.attractor.instagram.dto.user.EditUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean existsUsername(String username);
    boolean existsEmail(String email);
    void registerUser(CreateUserDto createUserDto);
    DisplayUserDto getByUsername(String username);
    EditUserDto getEditUserDto(String username);
    void updateUserWithAvatar(String username, EditUserDto dto, MultipartFile avatarFile);
    ResponseEntity<?> getAvatarByUserId(Long userId);
}