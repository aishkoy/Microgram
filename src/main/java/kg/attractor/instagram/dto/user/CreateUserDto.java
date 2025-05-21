package kg.attractor.instagram.dto.user;

import jakarta.validation.constraints.*;
import kg.attractor.instagram.entity.RoleType;
import kg.attractor.instagram.validation.annotation.UniqueEmail;
import kg.attractor.instagram.validation.annotation.UniqueUsername;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserDto {

    @NotBlank(message = "Имя не может быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё-]+$", message = "Имя может содержать только буквы и дефис")
    @Size(max = 55, message = "Имя не может быть длиннее 55 символов")
    String name;

    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё-]+$", message = "Фамилия может содержать только буквы и дефис")
    @Size(max = 55, message = "Фамилия не может быть длиннее 55 символов")
    String surname;

    @Email(message = "Введите корректный email")
    @NotBlank(message = "Email не может быть пустым")
    @UniqueEmail(message = "Этот email уже занят")
    String email;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @UniqueUsername(message = "Это имя пользователя уже занято")
    String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 20, message = "Пароль должен быть от 6 до 20 символов")
    String password;
}