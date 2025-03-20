package kg.attractor.microgram.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {
    private String gender;
    private String name;
    private String surname;
    private String username;
    private String aboutMe;
}
