package kg.attractor.microgram.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follow {
    private Long id;
    private String followerEmail;
    private String actualUser;
}
