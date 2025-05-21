package kg.attractor.instagram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LikeId implements Serializable {
    @Column(name = "post_id")
    Long postId;

    @Column(name = "user_id")
    Long userId;
}