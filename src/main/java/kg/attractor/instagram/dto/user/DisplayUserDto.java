package kg.attractor.instagram.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisplayUserDto {
    Long id;
    String name;
    String surname;
    String username;
    String email;
    String bio;
    String avatar;
    long followersCount;
    long followingCount;
}