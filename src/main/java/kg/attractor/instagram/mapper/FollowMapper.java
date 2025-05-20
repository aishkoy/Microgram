package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.FollowDto;
import kg.attractor.instagram.enitity.Follow;
import kg.attractor.instagram.enitity.FollowId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    FollowDto toDto(Follow follow);

    @Mapping(target = "id", expression = "java(createFollowId(dto))")
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

    @AfterMapping
    default void setFollowId(@MappingTarget Follow entity, FollowDto dto) {
        if (entity.getId() == null) {
            entity.setId(createFollowId(dto));
        }
    }
}