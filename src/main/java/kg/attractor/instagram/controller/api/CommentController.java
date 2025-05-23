package kg.attractor.instagram.controller.api;

import kg.attractor.instagram.dto.CommentDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.CommentService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsForPost(@PathVariable Long postId) {
        try {
            List<CommentDto> comments = commentService.getPostComments(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            log.error("Ошибка при получении комментариев для поста {}: {}", postId, e.getMessage());
            return ResponseEntity.ok(List.of());
        }
    }

    @PostMapping("/posts/{postId}")
    public ResponseEntity<?> addComment(
            @PathVariable Long postId,
            @RequestBody Map<String, String> request
    ) {
        try {
            UserDto currentUser = userService.getAuthUser();
            String content = request.get("content");

            if (content == null || content.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Содержимое комментария не может быть пустым"));
            }

            if (content.trim().length() > 500) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Комментарий не может быть длиннее 500 символов"));
            }

            CommentDto comment = commentService.addComment(postId, currentUser.getId(), content.trim());
            log.info("Пользователь {} добавил комментарий к посту {}", currentUser.getId(), postId);

            return ResponseEntity.ok(comment);
        } catch (IllegalArgumentException e) {
            log.warn("Некорректные данные при добавлении комментария: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Ошибка при добавлении комментария к посту {}: {}", postId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Произошла ошибка при добавлении комментария"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            UserDto currentUser = userService.getAuthUser();
            commentService.deleteComment(id, currentUser.getId());
            log.info("Пользователь {} удалил комментарий {}", currentUser.getId(), id);

            return ResponseEntity.ok(Map.of("success", true, "message", "Комментарий успешно удален"));
        } catch (AccessDeniedException e) {
            log.warn("Пользователь {} пытался удалить чужой комментарий {}",
                    userService.getAuthUser().getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "У вас нет права удалять этот комментарий"));
        } catch (Exception e) {
            log.error("Ошибка при удалении комментария {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Произошла ошибка при удалении комментария"));
        }
    }
}