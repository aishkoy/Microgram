package com.suslike.web.controller.api;

import com.suslike.web.AuthAdapter;
import com.suslike.web.dto.comments.CommentCreateDto;
import com.suslike.web.dto.comments.CommentDto;
import com.suslike.web.dto.user.UserDto;
import com.suslike.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("restComment")
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService service;
    private final AuthAdapter adapter;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(service.getCommentsByPostId(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getNewComment(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLastCommentByPost(id));
    }

    @PostMapping()
    public ResponseEntity<Long> addComment(@RequestBody CommentCreateDto dto) {
        UserDto user = adapter.getAuthUser();
        Long id = service.addComment(dto, user.getId());
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> addComment(@PathVariable Long id) {
        UserDto user = adapter.getAuthUser();
        service.deleteComment(id,user.getId());
        return ResponseEntity.ok(id);
    }
}
