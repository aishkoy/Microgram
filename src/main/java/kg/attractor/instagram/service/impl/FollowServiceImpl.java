package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.repository.FollowRepository;
import kg.attractor.instagram.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
}
