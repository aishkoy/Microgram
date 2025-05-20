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
public class FollowId implements Serializable {
    @Column(name = "follower_id")
    Long followerId;

    @Column(name = "following_id")
    Long followingId;
}