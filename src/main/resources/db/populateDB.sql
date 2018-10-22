DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;



INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

DELETE FROM meals;
ALTER SEQUENCE meals_id restart ;

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2018.01.03', 'dinner', 1200, 100001),
  ('2018.01.02', 'supper', 200, 100000),
  ('2018.01.01', 'breakfast', 500, 100000);
