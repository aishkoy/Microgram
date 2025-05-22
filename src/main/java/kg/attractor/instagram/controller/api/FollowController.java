package kg.attractor.instagram.controller.api;

import kg.attractor.instagram.service.FollowService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("restFollow")
@RequestMapping("api/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    @GetMapping("{userId}")
    public boolean isFollowing(@PathVariable Long userId) {
        return followService.isUserFollowed(userService.getAuthId(), userId);
    }
}
