-- changeset Aisha: 005 create comment table

create table comments
(
    id         long auto_increment primary key not null,
    content    varchar(255)                    not null,
    user_id    long                            not null,
    post_id    long                            not null,
    created_at timestamp                       not null default now(),

    constraint fk_comments_user
        foreign key (user_id)
            references users (id)
            on delete cascade
            on update cascade,

    constraint fk_comments_post
        foreign key (post_id)
            references posts (id)
            on delete cascade
            on update cascade
)