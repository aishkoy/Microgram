--liquibase formatted sql
--changeset Abu:add_initial_data

INSERT INTO PUBLIC.USERS (EMAIL, GENDER, NAME, SURNAME, USERNAME, PASSWORD, AVATAR, ABOUT_ME)
VALUES ('john.doe@example.com', 'Male', 'John', 'Doe', 'johndoe',
        '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2',
        'cb607ede-a6a7-4da5-a598-78c0df8139cd_  — копия.jpg', 'About me for user1');
INSERT INTO PUBLIC.USERS (EMAIL, GENDER, NAME, SURNAME, USERNAME, PASSWORD, AVATAR, ABOUT_ME)
VALUES ('jane.smith@example.com', 'Female', 'Jane', 'Smith', 'janesmith',
        '$2a$10$zLoPtjUjaZAcOPjEuFunnOS13swef0FFxo06ujuyobopYxwD5F/s2', '370e5d82-62a0-47e0-a186-2edb2f6d2a27_yone.jpg',
        'About me for user2');
INSERT INTO PUBLIC.USERS (EMAIL, GENDER, NAME, SURNAME, USERNAME, PASSWORD, AVATAR, ABOUT_ME)
VALUES ('michael.johnson@example.com', 'Other', 'Alice', 'Johnson', 'alicej',
        '$2a$10$VGkwdmvXTqgn6yfmdZ6w7.NdMDeSYTs4JcDdySZ.yMdQ4qzGH3At.',
        'ba590dfa-2a23-491d-ba51-ec7a707a9fcd_2_YOdQ.png.jpeg', 'ddoo');
INSERT INTO PUBLIC.USERS (EMAIL, GENDER, NAME, SURNAME, USERNAME, PASSWORD, AVATAR, ABOUT_ME)
VALUES ('brown@example.com', 'Male', 'Bob', 'Brown', 'bobbrown',
        '$2a$10$UYXM0mgh1OLkf6r7Iq0kCe2KV/fZT/GB1SkbdddpjzXEHK8NOQomi',
        '12f4136d-8034-40ff-9758-0987d9b5b318_Telegram_ @near2die_mood.jpg', 'About me for user4');
INSERT INTO PUBLIC.USERS (EMAIL, GENDER, NAME, SURNAME, USERNAME, PASSWORD, AVATAR, ABOUT_ME)
VALUES ('taylor@example.com', 'Female', 'Eve', 'Taylor', 'evet',
        '$2a$10$UYXM0mgh1OLkf6r7Iq0kCe2KV/fZT/GB1SkbdddpjzXEHK8NOQomi', null, 'About me for user5');
/* qwe */
/* password456 */
/* password123 */
/* qwerty */
/* qwerty */

--follows
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('john.doe@example.com', 'jane.smith@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('jane.smith@example.com', 'michael.johnson@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('michael.johnson@example.com', 'brown@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('brown@example.com', 'taylor@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('taylor@example.com', 'john.doe@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('john.doe@example.com', 'taylor@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('jane.smith@example.com', 'john.doe@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('jane.smith@example.com', 'brown@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('michael.johnson@example.com', 'jane.smith@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('michael.johnson@example.com', 'taylor@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('michael.johnson@example.com', 'john.doe@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('brown@example.com', 'john.doe@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('taylor@example.com', 'michael.johnson@example.com');
INSERT INTO PUBLIC.FOLLOWS (FOLLOWER, ACTUAL_USER)
VALUES ('taylor@example.com', 'jane.smith@example.com');

INSERT INTO authorities (role)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO user_authority (user_email, authority_id)
VALUES ('john.doe@example.com', 1),
       ('jane.smith@example.com', 1),
       ('michael.johnson@example.com', 1),
       ('brown@example.com', 1),
       ('taylor@example.com', 1),
       ('john.doe@example.com', 2);

--posts
INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
VALUES ('new manga is reaaady', '44a6194e-68a9-45b9-9d62-018198733f6a_001_waifu2x_2x_1n_ggAs.jpg',
        'john.doe@example.com', '2024-04-30 18:09:35.405745');
INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
VALUES ('DAANDAANNDAAAN', 'd1e7ae30-4b0b-4990-a80d-456f4fd0de9a_001_9VQY.jpg', 'john.doe@example.com',
        '2024-04-30 18:32:17.248037');
INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
VALUES ('tododo', '554c2ef2-7a3e-4fab-b771-ff604c8ed907_4_pMRq.png.jpeg', 'jane.smith@example.com',
        '2024-04-30 18:35:53.799012');
INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
VALUES ('moortty', 'd2ac860d-37ff-46b8-b0aa-975c4a06bb7e_rick-and-morty-season-6-morty-shocked.jpg',
        'brown@example.com', '2024-04-30 18:48:02.200330');
INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
VALUES ('hell paradisee loooovveveee', '31fb8175-6f1f-420c-a34d-632e763cca2b_01.jpeg', 'taylor@example.com',
        '2024-04-30 18:50:19.280442');
