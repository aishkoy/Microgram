package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.EditUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.entity.Role;
import kg.attractor.instagram.entity.User;
import kg.attractor.instagram.mapper.UserMapper;
import kg.attractor.instagram.repository.UserRepository;
import kg.attractor.instagram.service.FollowService;
import kg.attractor.instagram.service.RoleService;
import kg.attractor.instagram.service.UserService;
import kg.attractor.instagram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static kg.attractor.instagram.util.FileUtil.DEFAULT_AVATAR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final FollowService followService;

    @Override
    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void registerUser(CreateUserDto createUserDto) {
        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        Role userRole = roleService.getByName("USER");
        user.setRole(userRole);
        userRepository.save(user);
    }

    @Override
    public EditUserDto getEditUserDto(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return EditUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .bio(user.getBio())
                .build();
    }

    @Override
    public UserDto findByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto dto = userMapper.toDisplayDto(user);
        dto.setFollowersCount(followService.countFollowers(user.getId()));
        dto.setFollowingCount(followService.countFollowing(user.getId()));

        return dto;
    }

    @Override
    @Transactional
    public void updateUserWithAvatar(String username, EditUserDto dto, MultipartFile avatarFile) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBio(dto.getBio());

        try {
            String avatarPath = FileUtil.saveUploadFile(avatarFile, FileUtil.IMAGES_SUBDIR);
            user.setAvatar(avatarPath);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении аватара: " + e.getMessage());
        }

        userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> getAvatarByUserId(Long userId) {
        String avatarPath = userRepository.findById(userId)
                .map(User::getAvatar)
                .orElse(DEFAULT_AVATAR);
        return FileUtil.getOutputFile(avatarPath, MediaType.IMAGE_JPEG);
    }
}
