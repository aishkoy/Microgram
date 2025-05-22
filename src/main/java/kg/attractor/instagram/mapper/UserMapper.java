package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface UserMapper {
    User toEntity(UserDto dto);

    User toEntity(CreateUserDto createDto);

    @Mapping(target = "followers", expression = "java(user.getFollowers() != null ? user.getFollowers().size() : 0)")
    @Mapping(target = "followings", expression = "java(user.getFollowings() != null ? user.getFollowings().size() : 0)")
    UserDto toDto(User user);
}
