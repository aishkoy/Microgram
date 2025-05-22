package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    Boolean hasAccessToPost(Long postId, Long userId);

    List<PostDto> getUserPosts(Long userId);

    PostDto getPostById(Long postId);

    @Transactional
    PostDto createPost(String description, MultipartFile image, Long userId);

    @Transactional
    void deletePost(Long postId, Long userId);

    ResponseEntity<?> getPostImageById(Long postId);
}
