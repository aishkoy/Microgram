package kg.attractor.microgram.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {
    private Long id;
    private String image;
    private String content;
    private String owner;
    private LocalDateTime postedTime;
}
