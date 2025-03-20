package kg.attractor.microgram.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private Long id;
    private Long postId;
    private String commenter;
    private String content;
    private LocalDateTime commentedTime;
}
