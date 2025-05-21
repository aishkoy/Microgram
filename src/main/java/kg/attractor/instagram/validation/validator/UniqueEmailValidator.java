package kg.attractor.instagram.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.instagram.service.UserService;
import kg.attractor.instagram.validation.annotation.UniqueEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        userService.existsEmail(email);
        return email != null && !userService.existsEmail(email);
    }
}
