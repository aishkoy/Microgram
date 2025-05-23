-- changeset Aisha: 010 insert follows data

INSERT INTO follows (follower_id, following_id)
-- Maksat подписан на Aisha и Aigerim
SELECT (SELECT id FROM users WHERE username = 'Maksat'),
       (SELECT id FROM users WHERE username = 'Aisha')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Maksat'),
       (SELECT id FROM users WHERE username = 'Aigerim')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Aisha'),
       (SELECT id FROM users WHERE username = 'Maksat')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Aisha'),
       (SELECT id FROM users WHERE username = 'Timur')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Aigerim'),
       (SELECT id FROM users WHERE username = 'Maksat')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Aigerim'),
       (SELECT id FROM users WHERE username = 'Diana')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Timur'),
       (SELECT id FROM users WHERE username = 'Aisha')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Timur'),
       (SELECT id FROM users WHERE username = 'Askar')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Diana'),
       (SELECT id FROM users WHERE username = 'Aigerim')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Askar'),
       (SELECT id FROM users WHERE username = 'Timur')
UNION ALL
SELECT (SELECT id FROM users WHERE username = 'Askar'),
       (SELECT id FROM users WHERE username = 'Maksat');