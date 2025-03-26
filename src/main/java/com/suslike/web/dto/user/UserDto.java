package com.suslike.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String gender;
    private String name;
    private String surname;
    private String username;
    private String avatar;
    private String aboutMe;
}
