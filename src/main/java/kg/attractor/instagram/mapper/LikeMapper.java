package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.LikeDto;
import kg.attractor.instagram.entity.Like;
import kg.attractor.instagram.entity.LikeId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(target = "user.followings", ignore = true)
    @Mapping(target = "user.followers", ignore = true)
    @Mapping(target = "post.user.followers", ignore = true)
    @Mapping(target = "post.user.followings", ignore = true)
    @Mapping(target = "post.likes", ignore = true)
    @Mapping(target = "post.comments", ignore = true)
    LikeDto toDto(Like like);

    @Mapping(target = "id", expression = "java(createLikeId(dto))")
    @Mapping(target = "user.followings", ignore = true)
    @Mapping(target = "user.followers", ignore = true)
    @Mapping(target = "post.user.followers", ignore = true)
    @Mapping(target = "post.user.followings", ignore = true)
    @Mapping(target = "post.likes", ignore = true)
    @Mapping(target = "post.comments", ignore = true)
    Like toEntity(LikeDto dto);

    default LikeId createLikeId(LikeDto dto) {
        if (dto.getPost() == null || dto.getUser() == null || 
            dto.getPost().getId() == null || dto.getUser().getId() == null) {
            return null;
        }

        return LikeId.builder()
                .postId(dto.getPost().getId())
                .userId(dto.getUser().getId())
                .build();
    }
}