package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.entity.Post;
import kg.attractor.instagram.entity.User;
import kg.attractor.instagram.mapper.PostMapper;
import kg.attractor.instagram.repository.PostRepository;
import kg.attractor.instagram.repository.UserRepository;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDto> getUserPosts(Long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto getPostById(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Пост не найден"));
    }

    @Transactional
    @Override
    public PostDto createPost(String description, MultipartFile image, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        String filename = FileUtil.saveUploadFile(image, FileUtil.IMAGES_SUBDIR);

        Post post = Post.builder()
                .user(user)
                .description(description)
                .image(filename)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost);
    }

    @Transactional
    @Override
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Пост не найден"));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("У вас нет прав для удаления этого поста");
        }

        try {
            Files.deleteIfExists(Paths.get(post.getImage()));
        } catch (IOException e) {
            System.err.println("Не удалось удалить файл изображения: " + e.getMessage());
        }

        postRepository.delete(post);
    }

    @Override
    public ResponseEntity<?> getPostImageById(Long postId) {
        String imageFilename = postRepository.findById(postId)
                .map(Post::getImage)
                .orElseThrow(() -> new IllegalArgumentException("Пост не найден"));

        return FileUtil.getOutputFile(imageFilename, MediaType.IMAGE_JPEG);
    }

}
