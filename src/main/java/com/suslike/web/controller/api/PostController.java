package com.suslike.web.controller.api;

import com.suslike.web.AuthAdapter;
import com.suslike.web.dto.post.PostDto;
import com.suslike.web.service.PostService;
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
@RequestMapping("api/posts")
public class PostController {
    private final PostService service;
    private final AuthAdapter adapter;

    @GetMapping("/image/{postImage}")
    public ResponseEntity<InputStreamResource> getPostImage(@PathVariable String postImage){
       return service.getPostImage(postImage);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable Long id){
        return ResponseEntity.ok(service.getPostById(id, adapter.getAuthId()));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
