package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "user.followings", ignore = true)
    @Mapping(target = "user.followers", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Post toEntity(PostDto dto);

    @Mapping(target = "user.followings", ignore = true)
    @Mapping(target = "user.followers", ignore = true)
    @Mapping(target = "likes", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    @Mapping(target = "comments", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    PostDto toDto(Post post);
}
