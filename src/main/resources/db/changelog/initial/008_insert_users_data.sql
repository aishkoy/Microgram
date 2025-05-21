-- changeset Maksat: 008 insert user table

-- у всех пароли "qwerty"
INSERT INTO users (username, name, password, email, ROLE_ID)
SELECT 'Maksat',
       'Maksat',
       '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
       'zer0icemax@gmail.com',
       (SELECT id FROM ROLES WHERE Roles.NAME = 'USER')
UNION ALL
SELECT 'Aisha',
       'Aisha',
       '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
       'a.orozbekovaa@gmail.com',
       (SELECT id FROM ROLES WHERE Roles.NAME = 'USER')
UNION ALL
SELECT 'Aigerim',
       'Aigerim',
       '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
       'aigerim@example.com',
       (SELECT id FROM ROLES WHERE Roles.NAME = 'USER')
UNION ALL
SELECT 'Timur',
       'Timur',
       '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
       'timur@example.com',
       (SELECT id FROM ROLES WHERE Roles.NAME = 'USER')
UNION ALL
SELECT 'Diana',
       'Diana',
       '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
       'diana@example.com',
       (SELECT id FROM ROLES WHERE Roles.NAME = 'USER')
UNION ALL
SELECT 'Askar',
       'Askar',
       '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
       'askar@example.com',
       (SELECT id FROM ROLES WHERE Roles.NAME = 'USER');