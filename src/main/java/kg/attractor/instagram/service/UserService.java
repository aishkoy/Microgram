package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.DisplayUserDto;

public interface UserService {
    boolean existsUsername(String username);
    boolean existsEmail(String email);
    void registerUser(CreateUserDto createUserDto);
    DisplayUserDto getByUsername(String username);
}
