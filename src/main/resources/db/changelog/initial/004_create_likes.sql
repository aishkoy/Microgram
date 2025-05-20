-- changeset Aisha:004 create like table

CREATE TABLE likes
(
    post_id long not null,
    user_id long not null,
    primary key (user_id, post_id),

    constraint fk_likes_post
        foreign key (post_id)
            references posts (id)
            on delete cascade
            on update cascade,

    constraint fk_likes_user
        foreign key (user_id)
            references users (id)
            on delete cascade
            on update cascade
);
