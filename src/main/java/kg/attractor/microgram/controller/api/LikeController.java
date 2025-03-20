package kg.attractor.microgram.controller.api;

import kg.attractor.microgram.dto.LikeDto;
import kg.attractor.microgram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("restLike")
@RequiredArgsConstructor
@RequestMapping("api/like")
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

    @GetMapping("isLiked")
    public HttpStatus isPostLiked (@RequestBody LikeDto likeDto) {
        List<LikeDto> likes = likeService.getAllLikesByPostId(likeDto.getPostId());
        for (var like : likes) {
            if (like.getLikerEmail().equals(likeDto.getLikerEmail())) {
                return HttpStatus.OK;
            }
        }
        return HttpStatus.NOT_FOUND;
    }
}
