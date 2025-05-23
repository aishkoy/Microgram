package kg.attractor.instagram.controller.api;

import jakarta.validation.Valid;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.FollowService;
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
    private final FollowService followService;

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<?> getUserAvatar(@PathVariable @Valid Long userId) {
        return userService.getAvatarByUserId(userId);
    }

    @GetMapping("search")
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ofNullable(userService.searchUsers(query));
    }

    @PostMapping("{userId}/follow")
    public String followUser(@PathVariable Long userId,
                             @RequestParam(value = "isFollow") boolean isFollow) {
        followService.toggleFollow(userService.getAuthId(), userId, isFollow);
        return "redirect:/users/" + userId;
    }

    @GetMapping("{userId}/followings")
    public ResponseEntity<List<UserDto>> getUserFollows(@PathVariable Long userId) {
        return ResponseEntity.ofNullable(userService.getUserFollowers(userId));
    }

    @GetMapping("{userId}/followers")
    public ResponseEntity<List<UserDto>> getUserFollowings(@PathVariable Long userId) {
        return ResponseEntity.ofNullable(userService.getUserFollowings(userId));
    }
}