package kg.attractor.instagram.entity;

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
@Table(name = "follows")
public class Follow {
    @EmbeddedId
    FollowId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id",
            nullable = false)
    User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id",
            nullable = false)
    User following;
}