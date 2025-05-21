package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.DisplayUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.entity.User;
import kg.attractor.instagram.mapper.UserMapper;
import kg.attractor.instagram.repository.FollowRepository;
import kg.attractor.instagram.repository.UserRepository;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
        userRepository.save(user);
    }


    @Override
    public DisplayUserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DisplayUserDto dto = userMapper.toDisplayDto(user);
        dto.setFollowersCount(followRepository.countFollowers(user.getId()));
        dto.setFollowingCount(followRepository.countFollowing(user.getId()));

        return dto;
    }
}