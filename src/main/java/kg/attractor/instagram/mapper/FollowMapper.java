package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.FollowDto;
import kg.attractor.instagram.entity.Follow;
import kg.attractor.instagram.entity.FollowId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    @Mapping(target = "follower.followers", ignore = true)
    @Mapping(target = "follower.followings", ignore = true)
    @Mapping(target = "following.followers", ignore = true)
    @Mapping(target = "following.followings", ignore = true)
    FollowDto toDto(Follow follow);

    @Mapping(target = "id", expression = "java(createFollowId(dto))")
    @Mapping(target = "follower.followers", ignore = true)
    @Mapping(target = "follower.followings", ignore = true)
    @Mapping(target = "following.followers", ignore = true)
    @Mapping(target = "following.followings", ignore = true)
    Follow toEntity(FollowDto dto);

    default FollowId createFollowId(FollowDto dto) {
        if (dto.getFollower() == null || dto.getFollowing() == null || 
            dto.getFollower().getId() == null || dto.getFollowing().getId() == null) {
            return null;
        }

        return FollowId.builder()
                .followerId(dto.getFollower().getId())
                .followingId(dto.getFollowing().getId())
                .build();
    }
}