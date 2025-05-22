package kg.attractor.instagram.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Изображение обязательно")
    String image;

    String description;

    UserDto user;

    @Builder.Default
    Timestamp createdAt = Timestamp.from(Instant.now());

    @Builder.Default
    Integer comments = 0;

    @Builder.Default
    Integer likes = 0;
}
