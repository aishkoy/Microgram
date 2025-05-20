package kg.attractor.instagram.dto.user;

import jakarta.validation.constraints.*;
import kg.attractor.instagram.dto.RoleDto;
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
    @Max(55)
    String name;

    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё-]+$")
    @Max(55)
    String surname;

    @NotBlank
    @Max(100)
    String username;

    @Max(255)
    String email;

    String password;

    @Max(255)
    String bio;

    @Builder.Default
    String avatar = null;

    @Builder.Default
    Boolean enabled = true;

    @NotNull
    RoleDto role;
}