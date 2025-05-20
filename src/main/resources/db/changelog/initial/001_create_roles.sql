-- changeset Aisha: 001 create role table
create table roles
(
    id   long auto_increment primary key not null,
    name varchar(20)                     not null unique
)