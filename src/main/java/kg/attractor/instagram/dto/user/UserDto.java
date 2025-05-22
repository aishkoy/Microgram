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

    @NotBlank(message = "Имя не может быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё-]+$", message = "Имя может содержать только буквы и дефис")
    @Size(max = 55, message = "Длина имени не может превышать 55 символов")
    String name;

    @Size(max = 55, message = "Длина фамилии не может превышать 55 символов")
    String surname;

    String username;

    String email;

    String password;

    @Size(max = 255, message = "Длина биографии не может превышать 255 символов")
    String bio;

    @Builder.Default
    String avatar = null;

    @Builder.Default
    Boolean enabled = true;

    RoleDto role;

    Integer followers;
    Integer followings;
}