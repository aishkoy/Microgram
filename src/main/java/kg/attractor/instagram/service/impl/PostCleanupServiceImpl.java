package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.repository.CommentRepository;
import kg.attractor.instagram.repository.LikeRepository;
import kg.attractor.instagram.service.PostCleanupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostCleanupServiceImpl implements PostCleanupService {
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void cleanupPostDependencies(Long postId) {
        int deletedLikes = likeRepository.deleteByPostId(postId);
        log.debug("Удалено {} лайков поста {}", deletedLikes, postId);

        int deletedComments = commentRepository.deleteByPostId(postId);
        log.debug("Удалено {} комментариев поста {}", deletedComments, postId);
    }
}