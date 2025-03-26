package com.suslike.web.service;

import com.suslike.web.dao.LikeDao;
import com.suslike.web.dto.LikeDto;
import com.suslike.web.models.Like;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;

    public List<LikeDto> getAllLikesByPostId(Long id) {
        return likeDao.getAllLikesByPostID(id).stream().map(this::getDto).toList();
    }

    public void create(LikeDto dto) {
        Like like = likeDao.getLikeByIdAndPost(dto.getLiker(), dto.getPostId()).orElse(null);
        if (like != null) {
            if (Objects.equals(like.getPostId(), dto.getPostId()) && Objects.equals(like.getLiker(), dto.getLiker())) {
                likeDao.delete(like.getId());
            }
        } else {
            likeDao.create(Like.builder()
                    .liker(dto.getLiker())
                    .postId(dto.getPostId())
                    .build());
        }
    }

    private LikeDto getDto(Like l) {
        return LikeDto.builder()
                .id(l.getId())
                .liker(l.getLiker())
                .postId(l.getPostId())
                .build();
    }

}
