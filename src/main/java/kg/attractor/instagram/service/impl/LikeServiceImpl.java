package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.LikeDto;
import kg.attractor.instagram.entity.Like;
import kg.attractor.instagram.entity.LikeId;
import kg.attractor.instagram.mapper.LikeMapper;
import kg.attractor.instagram.repository.LikeRepository;
import kg.attractor.instagram.service.LikeService;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    private final PostService postService;
    private final UserService userService;

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        return likeRepository.existsByPost_IdAndUser_Id(postId, userId);
    }

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
            likeRepository.deleteById(likeId);
        } else {
            LikeDto like = LikeDto.builder()
                    .post(postService.getPostById(postId))
                    .user(userService.getUserById(userId))
                    .build();

            likeRepository.save(likeMapper.toEntity(like));
        }

        log.info("Пользователь {} {} лайк на пост {}", userId, existingLike.isPresent() ? "отменил" : "поставил", postId);
    }
}