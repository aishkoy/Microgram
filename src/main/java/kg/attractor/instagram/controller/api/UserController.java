package kg.attractor.instagram.controller.api;

import jakarta.validation.Valid;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("restUser")
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<?> getUserAvatar(@PathVariable @Valid Long userId) {
            return userService.getAvatarByUserId(userId);
    }
}