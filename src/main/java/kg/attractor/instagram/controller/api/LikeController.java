package kg.attractor.instagram.controller.api;

import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.LikeService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId) {
        try {
            UserDto currentUser = userService.getAuthUser();
            likeService.toggleLike(postId, currentUser.getId());

            boolean isLiked = likeService.isPostLikedByUser(postId, currentUser.getId());
            Long likesCount = likeService.getPostLikesCount(postId);

            return ResponseEntity.ok(Map.of(
                    "isLiked", isLiked,
                    "likesCount", likesCount
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{postId}/likes/count")
    public ResponseEntity<?> getLikesCount(@PathVariable Long postId) {
        try {
            Long likesCount = likeService.getPostLikesCount(postId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{postId}/likes/status")
    public ResponseEntity<?> getLikeStatus(@PathVariable Long postId) {
        try {
            UserDto currentUser = userService.getAuthUser();
            boolean isLiked = likeService.isPostLikedByUser(postId, currentUser.getId());
            Long likesCount = likeService.getPostLikesCount(postId);

            return ResponseEntity.ok(Map.of(
                    "isLiked", isLiked,
                    "likesCount", likesCount
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}