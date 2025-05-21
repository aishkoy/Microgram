package kg.attractor.instagram.dto.user;

import jakarta.validation.constraints.*;
import kg.attractor.instagram.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserDto {
    Long id;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё-]+$")
    @Size(max = 55)
    String name;

    @Size(max = 55)
    String surname;

    @NotBlank
    @Max(100)
    String username;

    @Size(max = 55)
    String email;

    String password;

    @Size(max = 255)
    String bio;

    @Builder.Default
    String avatar = null;

    @Builder.Default
    Boolean enabled = true;

    Role role;
}