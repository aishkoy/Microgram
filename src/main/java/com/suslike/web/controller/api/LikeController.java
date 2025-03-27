package com.suslike.web.controller.api;

import com.suslike.web.dto.LikeDto;
import com.suslike.web.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("restLike")
@RequiredArgsConstructor
@RequestMapping("api/likes")
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<LikeDto>> getAllLikesBYPostId(@PathVariable Long postId){
        return ResponseEntity.ok(likeService.getAllLikesByPostId(postId));
    }

    @PostMapping()
    public HttpStatus getAllLikesBYPostId(LikeDto dto){
        likeService.create(dto);
        return HttpStatus.OK;
    }

    @PostMapping("like")
    public HttpStatus likePost (@RequestBody LikeDto likeDto) {
        likeService.create(likeDto);
        return HttpStatus.OK;
    }
}
