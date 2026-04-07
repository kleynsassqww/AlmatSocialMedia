INSERT OR IGNORE INTO users (name, email, bio, password, created_at, is_verified, is_admin) VALUES
('Алмат', 'almat@example.com', 'Создатель этой платформы', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', datetime('now'), 1, 1);

INSERT OR IGNORE INTO users (name, email, bio, password, created_at, is_verified, is_admin) VALUES
('Айгерим', 'aigrim@example.com', 'Люблю читать и путешествовать', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', datetime('now'), 1, 0);

INSERT OR IGNORE INTO users (name, email, bio, password, created_at, is_verified, is_admin) VALUES
('Даниял', 'daniyal@example.com', 'Разработчик из Алматы', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', datetime('now'), 1, 0);

INSERT OR IGNORE INTO posts (title, content, user_id, likes, created_at) VALUES
('Добро пожаловать в AlmatSocial!', 'Это первый пост на нашей платформе. Рад всех приветствовать!', 1, 0, datetime('now'));

INSERT OR IGNORE INTO posts (title, content, user_id, likes, created_at) VALUES
('Весна в Алматы', 'Алматы весной — это что-то особенное. Цветут яблони, горы покрыты снегом...', 2, 0, datetime('now'));

INSERT OR IGNORE INTO posts (title, content, user_id, likes, created_at) VALUES
('Советы по программированию', 'Spring Boot — отличный фреймворк для создания REST API. Начните с малого и двигайтесь вперёд!', 1, 0, datetime('now'));

INSERT OR IGNORE INTO social_media (platform, profile_url, user_id) VALUES
('Instagram', 'https://instagram.com/almat', 1),
('GitHub', 'https://github.com/almat', 1),
('TikTok', 'https://tiktok.com/@aigrim', 2),
('LinkedIn', 'https://linkedin.com/in/daniyal', 3);

