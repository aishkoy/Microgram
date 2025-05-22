package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.entity.User;
import kg.attractor.instagram.exception.nsee.UserNotFoundException;
import kg.attractor.instagram.mapper.UserMapper;
import kg.attractor.instagram.repository.UserRepository;
import kg.attractor.instagram.service.RoleService;
import kg.attractor.instagram.service.UserService;
import kg.attractor.instagram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kg.attractor.instagram.util.FileUtil.DEFAULT_AVATAR;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь по id не найден"));

        log.info("Получен пользователь с id {}", user.getId());
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь по email не найден"));
        log.info("Получен пользователь с email {}", user.getEmail());
        return userMapper.toDto(user);
    }

    @Override
    public void registerUser(CreateUserDto createUserDto) {
        createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        createUserDto.setRole(roleService.getByName("USER"));

        User user = userMapper.toEntity(createUserDto);
        log.info("Создан пользователь с никнеймом {}", user.getUsername());
        userRepository.save(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        log.info("Получен пользователь с никнеймом {}", user.getUsername());
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public void updateUser(String username, UserDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBio(dto.getBio());

        log.info("Обновлен пользователь с никнеймом {}", user.getUsername());
        userRepository.save(user);
    }

    @Override
    public UserDto getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            log.warn("Неудачная попытка авторизации: {}", authentication);
            throw new UserNotFoundException("Пользователь не авторизован");
        }

        String email = authentication.getName();
        log.debug("Authenticated user email: {}", email);

        return getUserByEmail(email);
    }


    @Override
    public ResponseEntity<?> getAvatarByUserId(Long userId) {
        String avatarPath = userRepository.findAvatarById(userId)
                .orElse(DEFAULT_AVATAR);

        log.info("Получен аватар пользователя {}", avatarPath);
        return FileUtil.getOutputFile(avatarPath, MediaType.IMAGE_JPEG);
    }
}
