--liquibase formatted sql
--changeset Aisha:add_initial_data

-- Insert users
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
/* Password hints:
john.doe@example.com: qwe
jane.smith@example.com: password456
michael.johnson@example.com: password123
brown@example.com: qwerty
taylor@example.com: qwerty */

-- Insert authorities
INSERT INTO authorities (role)
VALUES ('USER'),
       ('ADMIN');

-- Insert user_authority relationships
-- We'll use subqueries to get the IDs since we now have numeric foreign keys
INSERT INTO user_authority (user_id, authority_id)
SELECT u.id, a.id FROM users u, authorities a
WHERE u.email = 'john.doe@example.com' AND a.role = 'USER';

INSERT INTO user_authority (user_id, authority_id)
SELECT u.id, a.id FROM users u, authorities a
WHERE u.email = 'jane.smith@example.com' AND a.role = 'USER';

INSERT INTO user_authority (user_id, authority_id)
SELECT u.id, a.id FROM users u, authorities a
WHERE u.email = 'michael.johnson@example.com' AND a.role = 'USER';

INSERT INTO user_authority (user_id, authority_id)
SELECT u.id, a.id FROM users u, authorities a
WHERE u.email = 'brown@example.com' AND a.role = 'USER';

INSERT INTO user_authority (user_id, authority_id)
SELECT u.id, a.id FROM users u, authorities a
WHERE u.email = 'taylor@example.com' AND a.role = 'USER';

INSERT INTO user_authority (user_id, authority_id)
SELECT u.id, a.id FROM users u, authorities a
WHERE u.email = 'john.doe@example.com' AND a.role = 'ADMIN';

-- Insert follows relationships
INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'john.doe@example.com' AND a.email = 'jane.smith@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'jane.smith@example.com' AND a.email = 'michael.johnson@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'michael.johnson@example.com' AND a.email = 'brown@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'brown@example.com' AND a.email = 'taylor@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'taylor@example.com' AND a.email = 'john.doe@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'john.doe@example.com' AND a.email = 'taylor@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'jane.smith@example.com' AND a.email = 'john.doe@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'jane.smith@example.com' AND a.email = 'brown@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'michael.johnson@example.com' AND a.email = 'jane.smith@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'michael.johnson@example.com' AND a.email = 'taylor@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'michael.johnson@example.com' AND a.email = 'john.doe@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'brown@example.com' AND a.email = 'john.doe@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'taylor@example.com' AND a.email = 'michael.johnson@example.com';

INSERT INTO PUBLIC.FOLLOWS (follower, actual_user)
SELECT f.id, a.id FROM users f, users a
WHERE f.email = 'taylor@example.com' AND a.email = 'jane.smith@example.com';

-- Insert posts
INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
SELECT 'new manga is reaaady', '44a6194e-68a9-45b9-9d62-018198733f6a_001_waifu2x_2x_1n_ggAs.jpg',
       u.id, '2024-04-30 18:09:35.405745'
FROM users u WHERE u.email = 'john.doe@example.com';

INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
SELECT 'DAANDAANNDAAAN', 'd1e7ae30-4b0b-4990-a80d-456f4fd0de9a_001_9VQY.jpg',
       u.id, '2024-04-30 18:32:17.248037'
FROM users u WHERE u.email = 'john.doe@example.com';

INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
SELECT 'tododo', '554c2ef2-7a3e-4fab-b771-ff604c8ed907_4_pMRq.png.jpeg',
       u.id, '2024-04-30 18:35:53.799012'
FROM users u WHERE u.email = 'jane.smith@example.com';

INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
SELECT 'moortty', 'd2ac860d-37ff-46b8-b0aa-975c4a06bb7e_rick-and-morty-season-6-morty-shocked.jpg',
       u.id, '2024-04-30 18:48:02.200330'
FROM users u WHERE u.email = 'brown@example.com';

INSERT INTO PUBLIC.POSTS (CONTENT, IMAGE, OWNER, POSTED_TIME)
SELECT 'hell paradisee loooovveveee', '31fb8175-6f1f-420c-a34d-632e763cca2b_01.jpeg',
       u.id, '2024-04-30 18:50:19.280442'
FROM users u WHERE u.email = 'taylor@example.com';

-- Add sample likes
INSERT INTO PUBLIC.LIKES (LIKER, POST_ID)
SELECT u.id, p.id
FROM users u, posts p
WHERE u.email = 'john.doe@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'jane.smith@example.com') LIMIT 1);

INSERT INTO PUBLIC.LIKES (LIKER, POST_ID)
SELECT u.id, p.id
FROM users u, posts p
WHERE u.email = 'jane.smith@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'john.doe@example.com') LIMIT 1);

INSERT INTO PUBLIC.LIKES (LIKER, POST_ID)
SELECT u.id, p.id
FROM users u, posts p
WHERE u.email = 'taylor@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'brown@example.com') LIMIT 1);

INSERT INTO PUBLIC.LIKES (LIKER, POST_ID)
SELECT u.id, p.id
FROM users u, posts p
WHERE u.email = 'michael.johnson@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'taylor@example.com') LIMIT 1);

-- Add sample comments
INSERT INTO PUBLIC.COMMENTS (CONTENT, POST, COMMENTER, COMMENTED_TIME)
SELECT 'Great post!', p.id, u.id, '2024-04-30 19:15:00.000000'
FROM users u, posts p
WHERE u.email = 'jane.smith@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'john.doe@example.com') LIMIT 1);

INSERT INTO PUBLIC.COMMENTS (CONTENT, POST, COMMENTER, COMMENTED_TIME)
SELECT 'Love this!', p.id, u.id, '2024-04-30 19:20:00.000000'
FROM users u, posts p
WHERE u.email = 'michael.johnson@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'taylor@example.com') LIMIT 1);

INSERT INTO PUBLIC.COMMENTS (CONTENT, POST, COMMENTER, COMMENTED_TIME)
SELECT 'Awesome content', p.id, u.id, '2024-04-30 19:25:00.000000'
FROM users u, posts p
WHERE u.email = 'brown@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'john.doe@example.com') LIMIT 1);

INSERT INTO PUBLIC.COMMENTS (CONTENT, POST, COMMENTER, COMMENTED_TIME)
SELECT 'Nice one!', p.id, u.id, '2024-04-30 19:30:00.000000'
FROM users u, posts p
WHERE u.email = 'taylor@example.com'
  AND p.id = (SELECT id FROM posts WHERE owner = (SELECT id FROM users WHERE email = 'jane.smith@example.com') LIMIT 1);