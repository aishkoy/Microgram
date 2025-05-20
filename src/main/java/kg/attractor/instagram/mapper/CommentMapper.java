package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.CommentDto;
import kg.attractor.instagram.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentDto dto);

    CommentDto toDto(Comment entity);
}
