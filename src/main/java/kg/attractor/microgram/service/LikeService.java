package kg.attractor.microgram.service;

import kg.attractor.microgram.dao.LikeDao;
import kg.attractor.microgram.dto.LikeDto;
import kg.attractor.microgram.models.Likes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;

    public List<LikeDto> getAllLikesByPostId(Long id) {
        return likeDao.getAllLikesByPostID(id).stream().map(this::getDto).collect(Collectors.toList());
    }

    public void create(LikeDto dto) {
        Likes like = likeDao.getLikeBYEmailAndPost(dto.getLikerEmail(), dto.getPostId()).orElse(null);
        if (like != null) {
            if (Objects.equals(like.getPostId(), dto.getPostId()) && Objects.equals(like.getLiker(), dto.getLikerEmail())) {
                likeDao.delete(like.getId());
            }
        } else {
            likeDao.create(Likes.builder()
                    .liker(dto.getLikerEmail())
                    .postId(dto.getPostId())
                    .build());
        }
    }


    private LikeDto getDto(Likes l) {
        return LikeDto.builder()
                .id(l.getId())
                .likerEmail(l.getLiker())
                .postId(l.getPostId())
                .build();
    }

}
