package com.suslike.web.service;

import com.suslike.web.dao.UserDao;
import com.suslike.web.dto.user.UserAddDto;
import com.suslike.web.dto.user.UserDto;
import com.suslike.web.dto.user.UserEditDto;
import com.suslike.web.models.UserModel;
import com.suslike.web.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao dao;
    private final FileUtil util;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUserById(Long userId) {
        UserModel userModel = dao.getUserById(userId).orElseThrow(() -> new NoSuchElementException("could not find user by id: " + userId));
        return getUserDto(userModel);
    }

    public UserDto getUserByUsername(String username) {
        UserModel userModel = dao.getUserByUsername(username).orElseThrow(() -> new NoSuchElementException("Could not find user with username: " + username));
        return getUserDto(userModel);
    }

    private UserDto getUserDto(UserModel u) {
        return UserDto.builder()
                .id(u.getId())
                .email(u.getEmail())
                .name(u.getName())
                .surname(u.getSurname())
                .gender(u.getGender())
                .username(u.getUsername())
                .avatar(u.getAvatar())
                .aboutMe(u.getAboutMe())
                .build();
    }


    public void editUser(UserEditDto edit, String email) {
        UserModel user = UserModel.builder()
                .email(email)
                .name(edit.getName())
                .surname(edit.getSurname())
                .username(edit.getUsername())
                .gender(edit.getGender())
                .aboutMe(edit.getAboutMe())
                .build();
        dao.editUser(user);
    }

    public Boolean userIsExist(String email) {
        return dao.getUserByEmail(email).isPresent();
    }

    public void addUser(UserAddDto dto){
        if (Boolean.FALSE.equals(userIsExist(dto.getEmail()))) {
            try {
                Long userId = dao.createUser(UserModel.builder()
                        .email(dto.getEmail())
                        .username(dto.getUsername())
                        .name(dto.getName())
                        .surname(dto.getSurname())
                        .avatar(null)
                        .gender(dto.getGender())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .build());

                dao.addAuthority(userId, "USER");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else throw new IllegalArgumentException("User already exists!");
    }

    public List<UserDto> getAllFollowers(String username) {
        return dao.getAllFollowers(getId(username)).stream().map(this::getUserDto).toList();
    }

    public List<UserDto> getAllFollowings(String username) {
        return dao.getAllFollowings(getId(username)).stream().map(this::getUserDto).toList();
    }

    private Long getId(String username) {
        return dao.getUserByUsername(username)
                .map(UserModel::getId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<UserDto> searchByUsernameOrEmail(String search) {
        return dao.searchByUsernameOrEmail(search).stream().map(this::getUserDto).toList();
    }

    public void addAvatar(MultipartFile avatar, String authUserName) {
        if (dao.searchByUsernameOrEmail(authUserName).isEmpty())
            throw new NoSuchElementException("User not found: " + authUserName);

        long maxFileSizeBytes = 3 * 1024 * 1024;
        if (avatar.getSize() > maxFileSizeBytes)
            throw new IllegalArgumentException("File size exceeds the maximum allowed size (3MB)");

        String fileName = util.saveUploadedFile(avatar, "img");

        dao.saveAvatar(fileName, getId(authUserName));

        log.info("Avatar saved for user: {}", authUserName);
    }
}

