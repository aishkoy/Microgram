package kg.attractor.microgram.service;

import kg.attractor.microgram.dao.PostDao;
import kg.attractor.microgram.dto.post.PostDto;
import kg.attractor.microgram.models.Post;
import kg.attractor.microgram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final UserService userService;
    private final LikeService likeService;
    private final PostDao postDao;
    private final FileUtil fileUtil;

    public List<PostDto> getAllPostsByOwner(String email){
        return postDao.getAllPostByEmail(email).stream()
                .map(this::getPostDto)
                .collect(Collectors.toList());
    }

    public void addPost(String content, MultipartFile mFile, String email) {
        String imgPath = fileUtil.saveUploadedFile(mFile, "img");

        postDao.addPost(
                Post.builder()
                        .image(imgPath)
                        .owner(email)
                        .content(content)
                        .postedTime(LocalDateTime.now())
                        .build()
        );
    }

    public List<PostDto> getAllPostByFollowedUser(String email) {
        return postDao.getAllPostByFollowedUser(email).stream()
                .map(this::getPostDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getPostsFromFeed (String followerEmail) {
        return postDao.getPostsFromFeed(followerEmail).stream().map(this::getPostDto).collect(Collectors.toList());
    }


    public void updatePost(Long postId, String newContent) {
        Post post = postDao.getPostById(postId);
        if (post != null) {
            post.setContent(newContent);
            postDao.updatePost(post);
            log.info("Updated post with ID {}", postId);
        } else {
            log.error("Post with ID {} not found", postId);
        }
    }

    public void deletePost(Long postId) {
        postDao.deletePost(postId);
        log.info("Deleted post with ID {}", postId);
    }

    private PostDto getPostDto(Post p){
        return PostDto.builder()
                .id(p.getId())
                .image(p.getImage())
                .content(p.getContent())
                .postedTime(p.getPostedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .owner(userService.getUserByEmail(p.getOwner()))
                .likesNum(likeService.getAllLikesByPostId(p.getId()).size())
                .build();
    }

    public ResponseEntity<InputStreamResource> getPostImage(String postImage) {
        return fileUtil.getOutputFile(postImage, "/img");
    }

    public PostDto getPostById(Long id) {
        return getPostDto(postDao.getPostById(id));
    }
}
