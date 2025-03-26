package com.suslike.web.dto.comments;

import com.suslike.web.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long post;
    private UserDto commenter;
    private String content;
    private LocalDateTime commentedTime;
}
