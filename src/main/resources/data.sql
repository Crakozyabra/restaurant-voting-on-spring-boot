INSERT INTO restaurant (name)
VALUES ('Italian restaurant'),
       ('France restaurant'),
       ('Russian restaurant');

INSERT INTO users (name, email, password)
VALUES ('Anton', 'admin@gmail.com', '{noop}admin'),
       ('Elena', 'user@gmail.com', '{noop}user'),
       ('Boris', 'guest@gmail.com', '{noop}guest');

INSERT INTO role(user_id, role)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');

INSERT INTO dish (restaurant_id, name, price, created_date)
VALUES (1, 'pizza', 106, current_date),
       (1, 'risotto', 201, current_date - interval '1' day),
       (1, 'lasagne', 302, current_date - interval '1' day),
       (1, 'ravioli', 403, current_date),
       (1, 'bryschetta', 503, current_date),

       (2, 'Creme brulee', 212, current_date - interval '1' day),
       (2, 'Onion soup', 162, current_date),
       (2, 'Souffle', 812, current_date),
       (2, 'Croissant', 312, current_date),
       (2, 'Duck confit', 112, current_date),

       (3, 'Cabbage soup', 212, current_date),
       (3, 'Pancakes', 162, current_date),
       (3, 'Potatoes with chicken', 812, current_date),
       (3, 'Cabbage rolls', 312, current_date),
       (3, 'Aspic', 112, current_date);

INSERT INTO vote (user_id, restaurant_id, vote_date, vote_time)
VALUES (1, 1, current_date, current_time);