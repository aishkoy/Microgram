package com.suslike.web.service;

import com.suslike.web.dao.PostDao;
import com.suslike.web.dto.LikeDto;
import com.suslike.web.dto.post.PostDto;
import com.suslike.web.models.Post;
import com.suslike.web.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final PostDao postDao;
    private final FileUtil fileUtil;

    public List<PostDto> getAllPostsByOwner(Long ownerId, Long currentUserId) {
        return postDao.getAllPostById(ownerId).stream()
                .map(post -> getPostDto(post, currentUserId))
                .toList();
    }

    public void addPost(String content, MultipartFile mFile, Long id) {
        String imgPath = fileUtil.saveUploadedFile(mFile, "img");

        postDao.addPost(
                Post.builder()
                        .image(imgPath)
                        .owner(id)
                        .content(content)
                        .postedTime(LocalDateTime.now())
                        .build()
        );
    }

    public List<PostDto> getFavoritesPostsByOwner(Long userId) {
        return postDao.getFavoritesPost(userId).stream()
                .map(post -> getPostDto(post, userId))
                .toList();
    }

    public List<PostDto> getPostsFromFeed(Long userId) {
        return postDao.getPostsFromFeed(userId).stream()
                .map(post -> getPostDto(post, userId))
                .toList();
    }

    public PostDto getPostById(Long postId, Long currentUserId) {
        return getPostDto(postDao.getPostById(postId), currentUserId);
    }

    public void deletePost(Long postId) {
        postDao.deletePost(postId);
        log.info("Deleted post with ID {}", postId);
    }

    private PostDto getPostDto(Post p, Long currentUserId) {
        List<LikeDto> likes = likeService.getAllLikesByPostId(p.getId());
        boolean isLiked = likes.stream()
                .anyMatch(like -> like.getLiker().equals(currentUserId));

        return PostDto.builder()
                .id(p.getId())
                .image(p.getImage())
                .content(p.getContent())
                .postedTime(p.getPostedTime())
                .owner(userService.getUserById(p.getOwner()))
                .likesNum(likes.size())
                .commentsNum(commentService.getAllCommentsByPostId(p.getId()).size())
                .isLiked(isLiked)
                .build();
    }

    public ResponseEntity<InputStreamResource> getPostImage(String postImage) {
        return fileUtil.getOutputFile(postImage, "/img");
    }
}
