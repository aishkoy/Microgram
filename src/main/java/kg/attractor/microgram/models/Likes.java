package kg.attractor.microgram.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Likes {
    private Long id;
    private String liker;
    private Long postId;
}
