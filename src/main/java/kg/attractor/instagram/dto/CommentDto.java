package kg.attractor.instagram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

public class CommentDto {
    Long id;

    @NotBlank(message = "Комментарий не может быть пустым")
    String content;

    @NotNull
    UserDto user;

    @NotNull
    PostDto post;

    @Builder.Default
    Timestamp createdAt = Timestamp.from(Instant.now());
}
