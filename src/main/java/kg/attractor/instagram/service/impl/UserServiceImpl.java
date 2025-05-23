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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

import static kg.attractor.instagram.util.FileUtil.DEFAULT_AVATAR;

@Service("userService")
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
    public List<UserDto> getUserFollowers(Long userId) {
        List<UserDto> users = userRepository.getFollowers(userId)
                .stream().map(userMapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователи не найдены");
        }
        log.info("Получено подписчиков пользователя {}", users.size());
        return users;
    }

    @Override
    public List<UserDto> getUserFollowings(Long userId) {
        List<UserDto> users = userRepository.getFollowing(userId)
                .stream().map(userMapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователи не найдены");
        }
        log.info("Получено подписок пользователя {}", users.size());
        return users;
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
    public void updateUser(UserDto dto) {
        UserDto user = getUserById(getAuthId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBio(dto.getBio());

        log.info("Обновлен пользователь с никнеймом {}", user.getUsername());
        userRepository.save(userMapper.toEntity(user));
    }

    @Override
    public UserDto getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            log.warn("Неудачная попытка авторизации: {}", authentication);
            throw new UserNotFoundException("Пользователь не авторизован");
        }

        String login = authentication.getName();
        log.debug("Authenticated user email: {}", login);

        try {
            return getUserByEmail(login);
        } catch (NoSuchElementException e) {
            return findByUsername(login);
        }
    }

    @Override
    public Long getAuthId() {
        return getAuthUser().getId();
    }

    @Override
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getUserId();
            return userId.equals(currentUserId);
        }

        return false;
    }

    @Override
    public ResponseEntity<?> getAvatarByUserId(Long userId) {
        String avatarPath = userRepository.findAvatarById(userId)
                .orElse(DEFAULT_AVATAR);

        log.info("Получен аватар пользователя {}", avatarPath);
        return FileUtil.getOutputFile(avatarPath, MediaType.IMAGE_JPEG);
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        List<UserDto> users = userRepository.searchUsers(query)
                .stream().map(userMapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователи не найдены");
        }

        log.info("Получены пользователи по запросу {}", users.size());
        return users;
    }

    @Override
    public void uploadAvatar(MultipartFile file, Long authId) {
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new IllegalArgumentException("Только файлы JPEG и PNG разрешены для загрузки");
        }

        log.info("Загружен аватар пользователя с id {}", authId);
        userRepository.updateUserAvatar(authId, saveImage(file));
    }

    public String saveImage(MultipartFile file) {
        return FileUtil.saveUploadFile(file, "images/");
    }

}
