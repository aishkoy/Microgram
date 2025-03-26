package com.suslike.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddDto {
	@Email(message = "Enter email")
	@NotBlank
	private String email;
	
	private String gender;
	private String name;
	private String surname;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_-]{3,16}$",
			message = "Username must be between 3 and 16 characters" +
					" and can contain only letters, numbers, underscores, and hyphens")
	private String username;
	@NotBlank
	@Size(min = 4, max = 24, message = "Length must be >= 4 and <= 24")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
			message = "Should contain at least one uppercase letter, one number")
	private String password;
}
