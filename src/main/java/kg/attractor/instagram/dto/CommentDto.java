package kg.attractor.instagram.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CommentDto {
    Long id;
    String content;
    String username;
    String avatar;
    String createdAt;
}
