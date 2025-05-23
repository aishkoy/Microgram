package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.entity.Post;
import kg.attractor.instagram.exception.nsee.ImageNotfoundException;
import kg.attractor.instagram.exception.nsee.PostNotFoundException;
import kg.attractor.instagram.mapper.PostMapper;
import kg.attractor.instagram.repository.LikeRepository;
import kg.attractor.instagram.repository.PostRepository;
import kg.attractor.instagram.service.*;
import kg.attractor.instagram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final PostCleanupService postCleanupService;

    @Override
    public List<PostDto> getAllPosts() {
        List<PostDto> posts = postRepository.findAll()
                .stream().map(postMapper::toDto)
                .toList();
        if (posts.isEmpty()) {
            throw new PostNotFoundException("Нет постов");
        }
        log.info("Получено постов {} ", posts.size());
        return posts;
    }

    @Override
    public Boolean hasAccessToPost(Long postId, Long userId) {
        return postRepository.existsByIdAndUser_Id(postId, userId);
    }

    @Override
    public List<PostDto> getUserPosts(Long userId) {
        List<PostDto> posts = postRepository.findByUserId(userId)
                .stream().map(postMapper::toDto)
                .toList();
        if (posts.isEmpty()) {
            throw new PostNotFoundException("У пользователя нет постов");
        }
        log.info("Получено {} постов пользователя по id {} ", posts.size(), userId);
        return posts;
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Пост не найден"));
        log.info("Получен пост с id {}", post.getId());
        return postMapper.toDto(post);
    }

    @Transactional
    @Override
    public PostDto createPost(String description, MultipartFile image, Long userId) {
        String filename = FileUtil.saveUploadFile(image, FileUtil.IMAGES_SUBDIR);

        PostDto postDto = PostDto.builder()
                .user(userService.getUserById(userId))
                .description(description)
                .image(filename)
                .build();

        Post entity = postMapper.toEntity(postDto);
        postRepository.save(entity);

        log.info("Создан пост с id {} для пользователя с id {}", entity.getId(), userId);

        return postDto;
    }

    @Transactional
    @Override
    public void deletePost(Long postId, Long userId) {
        PostDto post = getPostById(postId);

        Boolean hasAccess = hasAccessToPost(postId, userId);
        if (Boolean.TRUE.equals(hasAccess)) {
            try {
                Files.deleteIfExists(Paths.get(post.getImage()));
            } catch (IOException e) {
                log.error("Не удалось удалить файл изображения: {}", e.getMessage());
            }

            postCleanupService.cleanupPostDependencies(postId);

            postRepository.deleteById(postId);
            log.info("Удален пост с id {}", postId);
        } else {
            throw new AccessDeniedException("У вас нет права удалять этот пост! (" + postId + ")");
        }
    }

    @Override
    public ResponseEntity<?> getPostImageById(Long postId) {
        String imageFilename = postRepository.findById(postId)
                .map(Post::getImage)
                .orElseThrow(() -> new ImageNotfoundException("Фотография поста не была найдена!"));

        return FileUtil.getOutputFile(imageFilename, MediaType.IMAGE_JPEG);
    }
}
