package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    @Transactional(readOnly = true)
    List<PostDto> getAllPosts();

    @Transactional(readOnly = true)
    List<PostDto> getUserPosts(Long userId);

    @Transactional(readOnly = true)
    PostDto getPostById(Long postId);

    @Transactional
    PostDto createPost(String description, MultipartFile image, Long userId) throws IOException;

    @Transactional
    void deletePost(Long postId, Long userId);

    ResponseEntity<?> getPostImageById(Long postId);
}
