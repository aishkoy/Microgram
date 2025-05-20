package kg.attractor.instagram.enitity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "likes")
public class Like {
    @EmbeddedId
    LikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",
            nullable = false)
    Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            nullable = false)
    User user;
}