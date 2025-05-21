package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.entity.Like;
import kg.attractor.instagram.entity.LikeId;
import kg.attractor.instagram.entity.Post;
import kg.attractor.instagram.entity.User;
import kg.attractor.instagram.mapper.LikeMapper;
import kg.attractor.instagram.repository.LikeRepository;
import kg.attractor.instagram.repository.PostRepository;
import kg.attractor.instagram.repository.UserRepository;
import kg.attractor.instagram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeMapper likeMapper;

    @Transactional(readOnly = true)
    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        LikeId likeId = LikeId.builder()
                .postId(postId)
                .userId(userId)
                .build();
        return likeRepository.existsById(likeId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getPostLikesCount(Long postId) {
        return likeRepository.countByIdPostId(postId);
    }

    @Transactional
    @Override
    public void toggleLike(Long postId, Long userId) {
        LikeId likeId = LikeId.builder()
                .postId(postId)
                .userId(userId)
                .build();

        Optional<Like> existingLike = likeRepository.findById(likeId);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("Пост не найден"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

            Like like = Like.builder()
                    .id(likeId)
                    .post(post)
                    .user(user)
                    .build();

            likeRepository.save(like);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getPostLikers(Long postId) {
        return likeRepository.findByIdPostId(postId).stream()
                .map(Like::getUser)
                .toList();
    }
}