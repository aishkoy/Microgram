package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.LikeDto;
import kg.attractor.instagram.enitity.Like;
import kg.attractor.instagram.enitity.LikeId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    LikeDto toDto(Like like);

    @Mapping(target = "id", expression = "java(createLikeId(dto))")
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

    @AfterMapping
    default void setLikeId(@MappingTarget Like entity, LikeDto dto) {
        if (entity.getId() == null) {
            entity.setId(createLikeId(dto));
        }
    }
}