package kg.attractor.instagram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)


@Embeddable
@EqualsAndHashCode
public class LikeId implements Serializable {
    @Column(name = "post_id")
    Long postId;

    @Column(name = "user_id")
    Long userId;
}