package kg.attractor.microgram.controller.api;

import kg.attractor.microgram.dto.post.PostDto;
import kg.attractor.microgram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("restPost")
@RequiredArgsConstructor
@RequestMapping("api/post")
public class PostController {
    private final PostService service;


    @GetMapping("/{postImage}")
    public ResponseEntity<InputStreamResource> getPostImage(@PathVariable String postImage){
       return service.getPostImage(postImage);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable Long id){
        return ResponseEntity.ok(service.getPostById(id));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
