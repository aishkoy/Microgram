-- changeset Aisha: 006 create follow table

create table follows
(
    follower_id  long not null,
    following_id long not null,
    primary key (follower_id, following_id),

    constraint fk_follows_follower
        foreign key (follower_id)
            references users (id)
            on delete cascade
            on update cascade,
    constraint fk_follows_following
        foreign key (following_id)
            references users (id)
            on delete cascade
            on update cascade
);