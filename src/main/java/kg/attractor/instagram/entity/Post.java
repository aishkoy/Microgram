package kg.attractor.instagram.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    Long id;

    @Column(name = "image",
            nullable = false)
    String image;

    @Column(name = "description")
    String description;

    @Column(name = "created_at",
            nullable = false)
    Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            nullable = false)
    User user;

    @OneToMany(mappedBy = "post")
    List<Like> likes;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;
}
