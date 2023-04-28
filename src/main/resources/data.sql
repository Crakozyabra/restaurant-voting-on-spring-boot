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

INSERT INTO menu (restaurant_id, name, price, enabled)
VALUES (1, 'pizza', 106.5, 'false'),
       (1, 'risotto', 201.4, 'true'),
       (1, 'lasagne', 302.3, 'true'),
       (1, 'ravioli', 403.2, 'true'),
       (1, 'bryschetta', 503.1, 'true'),

       (2, 'Creme brulee', 212.1, 'false'),
       (2, 'Onion soup', 162.1, 'false'),
       (2, 'Souffle', 812.1, 'true'),
       (2, 'Croissant', 312.1, 'true'),
       (2, 'Duck confit', 112.1, 'true'),

       (3, 'Cabbage soup', 212.1, 'true'),
       (3, 'Pancakes', 162.1, 'true'),
       (3, 'Potatoes with chicken', 812.1, 'true'),
       (3, 'Cabbage rolls', 312.1, 'true'),
       (3, 'Aspic', 112.1, 'true');

INSERT INTO vote (user_id, restaurant_id, voting_date, voting_time)
VALUES (1, 1, current_date, current_time);