package com.suslike.web.dto.post;

import com.suslike.web.dto.user.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String image;
    private String content;
    private UserDto owner;
    private LocalDateTime postedTime;
    private Integer likesNum;
    private Integer commentsNum;
    private Boolean isLiked;
}
