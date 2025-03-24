package kg.attractor.microgram.dto.post;

import kg.attractor.microgram.dto.user.UserDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String image;
    private String content;
    private UserDto owner;
    private String postedTime;
    private Integer likesNum;
    private Integer commentsNum;
}
