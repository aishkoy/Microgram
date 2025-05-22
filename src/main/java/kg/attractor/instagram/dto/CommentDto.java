package kg.attractor.instagram.dto;

import kg.attractor.instagram.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CommentDto {
    Long id;
    String content;
    UserDto user;
    PostDto post;
    Timestamp createdAt;
}
