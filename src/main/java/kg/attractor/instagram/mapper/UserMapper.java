package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.DisplayUserDto;
import kg.attractor.instagram.dto.user.EditUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {
    User toEntity(UserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "username")
    User toEntity(CreateUserDto createDto);

    UserDto toDto(User entity);

    @Mapping(target = "followersCount", ignore = true)
    @Mapping(target = "followingCount", ignore = true)
    DisplayUserDto toDisplayDto(User user);

    void updateUserFromEditDto(EditUserDto editUserDto, @MappingTarget User user);
}
