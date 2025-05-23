package kg.attractor.instagram.service;

public interface PostCleanupService {
    void cleanupPostDependencies(Long postId);
}
