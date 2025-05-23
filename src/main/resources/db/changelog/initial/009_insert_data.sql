-- changeset Maksat:009 insert posts with likes and comments
INSERT INTO posts (image, description, user_id, created_at)
-- Maksat's posts
SELECT 'post1.jpeg', 'Beautiful sunset views!',
       (SELECT id FROM users WHERE username = 'Maksat'), '2023-01-15 18:30:00'
UNION ALL
SELECT 'post2.jpg', 'My new project is coming soon!',
       (SELECT id FROM users WHERE username = 'Maksat'), '2023-02-20 12:15:00'

-- Aisha's posts
UNION ALL
SELECT 'post3.jpg', 'Enjoying my weekend getaway',
       (SELECT id FROM users WHERE username = 'Aisha'), '2023-01-18 14:20:00'
UNION ALL
SELECT 'post4.jpg', 'Delicious homemade dinner',
       (SELECT id FROM users WHERE username = 'Aisha'), '2023-03-05 19:45:00'

-- Aigerim's posts
UNION ALL
SELECT 'post5.jpg', 'My cute little puppy',
       (SELECT id FROM users WHERE username = 'Aigerim'), '2023-02-10 10:00:00'
UNION ALL
SELECT 'post6.jpg', 'Visiting new art exhibition',
       (SELECT id FROM users WHERE username = 'Aigerim'), '2023-03-15 16:30:00'

-- Timur's posts
UNION ALL
SELECT 'post7.jpg', 'Working on exciting new features',
       (SELECT id FROM users WHERE username = 'Timur'), '2023-01-25 09:15:00'
UNION ALL
SELECT 'post8.jpg', 'Team building event was amazing!',
       (SELECT id FROM users WHERE username = 'Timur'), '2023-02-28 20:00:00'

-- Diana's posts
UNION ALL
SELECT 'post9.jpeg', 'My morning coffee ritual',
       (SELECT id FROM users WHERE username = 'Diana'), '2023-02-05 08:30:00'
UNION ALL
SELECT 'post10.jpeg', 'Finished reading this amazing book',
       (SELECT id FROM users WHERE username = 'Diana'), '2023-03-10 22:15:00';

-- Insert likes
INSERT INTO likes (post_id, user_id)
-- Likes for Maksat's posts
SELECT (SELECT id FROM posts WHERE image = 'post1.jpeg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Aisha')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post1.jpeg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Aigerim')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post2.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Timur')

-- Likes for Aisha's posts
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post3.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Maksat')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post3.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Diana')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post4.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Aigerim')

-- Likes for Aigerim's posts
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post5.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Maksat')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post5.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Aisha')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post5.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Diana')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post6.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Timur')

-- Likes for Timur's posts
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post7.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Maksat')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post8.jpg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Aisha')

-- Likes for Diana's posts
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post9.jpeg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Aigerim')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post10.jpeg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Timur')
UNION ALL
SELECT (SELECT id FROM posts WHERE image = 'post10.jpeg' LIMIT 1),
       (SELECT id FROM users WHERE username = 'Maksat');

-- Insert comments
INSERT INTO comments (content, user_id, post_id, created_at)
-- Comments on Maksat's posts
SELECT 'Wow, amazing view!',
       (SELECT id FROM users WHERE username = 'Aisha'),
       (SELECT id FROM posts WHERE image = 'post1.jpeg' LIMIT 1),
       '2023-01-15 19:05:00'
UNION ALL
SELECT 'Where was this taken?',
       (SELECT id FROM users WHERE username = 'Aigerim'),
       (SELECT id FROM posts WHERE image = 'post1.jpeg' LIMIT 1),
       '2023-01-15 20:30:00'
UNION ALL
SELECT 'Looking forward to it!',
       (SELECT id FROM users WHERE username = 'Timur'),
       (SELECT id FROM posts WHERE image = 'post2.jpg' LIMIT 1),
       '2023-02-20 13:45:00'

-- Comments on Aisha's posts
UNION ALL
SELECT 'You deserve this break!',
       (SELECT id FROM users WHERE username = 'Maksat'),
       (SELECT id FROM posts WHERE image = 'post3.jpg' LIMIT 1),
       '2023-01-18 15:10:00'
UNION ALL
SELECT 'Recipe please!',
       (SELECT id FROM users WHERE username = 'Diana'),
       (SELECT id FROM posts WHERE image = 'post4.jpg' LIMIT 1),
       '2023-03-05 20:30:00'

-- Comments on Aigerim's posts
UNION ALL
SELECT 'So adorable! What breed?',
       (SELECT id FROM users WHERE username = 'Maksat'),
       (SELECT id FROM posts WHERE image = 'post5.jpg' LIMIT 1),
       '2023-02-10 11:20:00'
UNION ALL
SELECT 'I was there too! Amazing artworks',
       (SELECT id FROM users WHERE username = 'Timur'),
       (SELECT id FROM posts WHERE image = 'post6.jpg' LIMIT 1),
       '2023-03-15 17:45:00'

-- Comments on Timur's posts
UNION ALL
SELECT 'Can you share some details?',
       (SELECT id FROM users WHERE username = 'Aisha'),
       (SELECT id FROM posts WHERE image = 'post7.jpg' LIMIT 1),
       '2023-01-25 10:30:00'
UNION ALL
SELECT 'Teamwork makes the dream work!',
       (SELECT id FROM users WHERE username = 'Diana'),
       (SELECT id FROM posts WHERE image = 'post8.jpg' LIMIT 1),
       '2023-02-28 21:15:00'

-- Comments on Diana's posts
UNION ALL
SELECT 'Coffee is life!',
       (SELECT id FROM users WHERE username = 'Aigerim'),
       (SELECT id FROM posts WHERE image = 'post9.jpeg' LIMIT 1),
       '2023-02-05 09:00:00'
UNION ALL
SELECT 'I loved that book too!',
       (SELECT id FROM users WHERE username = 'Timur'),
       (SELECT id FROM posts WHERE image = 'post10.jpeg' LIMIT 1),
       '2023-03-10 23:00:00'
UNION ALL
SELECT 'Adding this to my reading list',
       (SELECT id FROM users WHERE username = 'Maksat'),
       (SELECT id FROM posts WHERE image = 'post10.jpeg' LIMIT 1),
       '2023-03-11 08:45:00';