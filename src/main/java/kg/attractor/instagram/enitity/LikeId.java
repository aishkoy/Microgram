package kg.attractor.instagram.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

@Embeddable
public class LikeId implements Serializable {
    @Column(name = "post_id")
    Long postId;

    @Column(name = "user_id")
    Long userId;
}