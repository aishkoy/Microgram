package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {
    User toEntity(UserDto dto);

    User toEntity(CreateUserDto createDto);

    UserDto toDto(User entity);
}
