package kg.attractor.instagram.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditUserDto {
    Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё-]+$", message = "Имя может содержать только буквы и дефис")
    @Size(max = 55, message = "Длина имени не может превышать 55 символов")
    String name;

    @Size(max = 55, message = "Длина фамилии не может превышать 55 символов")
    String surname;

    @Size(max = 255, message = "Длина биографии не может превышать 255 символов")
    String bio;

    MultipartFile avatarFile;

    boolean deleteAvatar;
}