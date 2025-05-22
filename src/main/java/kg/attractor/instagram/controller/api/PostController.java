package kg.attractor.instagram.controller.api;

import kg.attractor.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("restPost")
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("{postId}/image")
    public ResponseEntity<?> getPostImage(@PathVariable Long postId) {
        return postService.getPostImageById(postId);
    }

}
