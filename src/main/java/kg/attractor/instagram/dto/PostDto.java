package kg.attractor.instagram.dto;

import kg.attractor.instagram.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class PostDto {
    Long id;
    String image;
    String description;
    UserDto user;

    @Builder.Default
    Timestamp createdAt = Timestamp.from(Instant.now());
    Integer comments;
    Integer likes;
}
