package kg.attractor.instagram.dto;

import jakarta.validation.constraints.NotNull;
import kg.attractor.instagram.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class FollowDto {
    @NotNull
    UserDto follower;

    @NotNull
    UserDto following;
}
