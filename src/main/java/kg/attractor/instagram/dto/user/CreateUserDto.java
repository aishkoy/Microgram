package kg.attractor.instagram.dto.user;

import jakarta.validation.constraints.*;
import kg.attractor.instagram.validation.annotation.UniqueEmail;
import kg.attractor.instagram.validation.annotation.ValidUserByRole;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@FieldDefaults(level = AccessLevel.PRIVATE)


public class CreateUserDto extends UserDto {
    @Email
    @NotBlank
    @UniqueEmail
    String email;

    @NotBlank
    @UniqueUsername
    String username;

    @NotBlank
    @Size(min = 8, max = 20")
    @Pattern(
            regexp = "^(?=.*[A-Za-zА-Яа-я])(?=.*\\d)[A-Za-zА-Яа-я\\d@#$%^&+=!]{8,}$")
    String password;
}