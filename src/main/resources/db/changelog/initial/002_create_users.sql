-- changeset Aisha: 002 create user table
create table users
(
    id       long auto_increment primary key not null,
    name     varchar(55)                     not null,
    surname  varchar(55),
    username varchar(100)                    not null unique,
    email    varchar(255)                    not null unique,
    password varchar(255)                    not null,
    bio      varchar(255),
    avatar   varchar(255),
    role_id  long                            not null,
    enabled  boolean                         not null default true,

    constraint fk_role_id
        foreign key (role_id)
            references roles (id)
            on delete cascade
            on update cascade
)