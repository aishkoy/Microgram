package kg.attractor.microgram.controller.api;

import kg.attractor.microgram.AuthAdapter;
import kg.attractor.microgram.dto.comments.CommentCreateDto;
import kg.attractor.microgram.dto.comments.CommentDto;
import kg.attractor.microgram.dto.user.UserDto;
import kg.attractor.microgram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("restComment")
@RequiredArgsConstructor
@RequestMapping("api/comment")
public class CommentController {
    private final CommentService service;
    private final AuthAdapter adapter;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsBYPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(service.getCommentsByPostId(postId));
    }

    @GetMapping("/last/{commentId}")
    public ResponseEntity<CommentDto> getNewAddedComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(service.getCommentsLastCommentsByPost(commentId));
    }

    @PostMapping()
    public ResponseEntity<Long> addComment(@RequestBody CommentCreateDto dto) {
        UserDto user = adapter.getAuthUser();
        Long id = service.addComment(dto, user.getEmail());
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> addComment(@PathVariable Long id) {
        UserDto user = adapter.getAuthUser();
        service.deleteComment(id,user.getEmail());
        return ResponseEntity.ok(id);
    }
}
