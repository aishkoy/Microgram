package kg.attractor.instagram.entity;


import jakarta.persistence.*;
import kg.attractor.instagram.converter.RoleTypeConverter;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    Long id;

    @Column(name = "name",
            nullable = false)
    String name;

    @Column(name = "surname",
            nullable = false)
    String surname;

    @Column(name = "username",
            nullable = false,
            unique = true)
    String username;

    @Column(name = "email",
            nullable = false,
            unique = true)
    String email;

    @Column(name = "password",
            nullable = false)
    String password;

    @Column(name = "bio")
    String bio;

    @Column(name = "avatar")
    String avatar;

    @Convert(converter = RoleTypeConverter.class)
    @Column(name = "role_id", nullable = false)
    @Builder.Default
    RoleType role = RoleType.USER;

    @Column(name = "enabled",
            nullable = false)
    @Builder.Default
    Boolean enabled = true;
}
