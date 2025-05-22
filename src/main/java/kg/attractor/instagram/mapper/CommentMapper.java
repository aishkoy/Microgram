package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.CommentDto;
import kg.attractor.instagram.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "user.followings", ignore = true)
    @Mapping(target = "user.followers", ignore = true)
    @Mapping(target = "post.user.followers", ignore = true)
    @Mapping(target = "post.user.followings", ignore = true)
    @Mapping(target = "post.likes", ignore = true)
    @Mapping(target = "post.comments", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(target = "user.followings", ignore = true)
    @Mapping(target = "user.followers", ignore = true)
    @Mapping(target = "post.user.followers", ignore = true)
    @Mapping(target = "post.user.followings", ignore = true)
    @Mapping(target = "post.likes", ignore = true)
    @Mapping(target = "post.comments", ignore = true)
    CommentDto toDto(Comment entity);
}
