-- changeset Aisha: 003 create post table

create table posts
(
    id          long auto_increment primary key not null,
    image       varchar(255)                    not null,
    description text                            not null,
    user_id     long                            not null,
    created_at  timestamp                       not null default now(),

    constraint fk_posts_user_id
        foreign key (user_id)
            references users (id)
            on delete cascade
            on update cascade
)