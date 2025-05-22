package kg.attractor.instagram.controller.api;

import jakarta.validation.Valid;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("restUser")
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<?> getUserAvatar(@PathVariable @Valid Long userId) {
            return userService.getAvatarByUserId(userId);
    }

    @GetMapping("search")
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam(value = "q", required = false) String query){
        return ResponseEntity.ofNullable(userService.searchUsers(query));
    }
}