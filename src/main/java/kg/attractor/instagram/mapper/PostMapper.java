package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.enitity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(PostDto dto);
    PostDto toDto(Post entity);
}
