-- populate categories table
INSERT INTO categories(name, active)
VALUES('Mobile phone', true);
INSERT INTO categories(name, active)
VALUES('Television', true);
INSERT INTO categories(name, active)
VALUES('Laptop', true);
INSERT INTO categories(name, active)
VALUES('Tablet', true);
INSERT INTO categories(name, active)
VALUES('Player', true);

-- populate brands table
INSERT INTO brands(id, name, description, active)
VALUES(1, 'Samsung','Samsung, South Korean company that is one of the world’s largest producers of electronic devices. It produces about a fifth of South Korea’s total exports', true);
INSERT INTO brands(id, name, description, active)
VALUES(3, 'Xiaomi','Xiaomi is a Chinese electronics company founded in 2010 by Lei Jun. The company creates a wide range of products including hardware, software and Internet services', true);
INSERT INTO brands(id, name, description, active)
VALUES(10, 'Honor','Honor is a smartphone brand owned by Huawei Technologies. As part of the Huawei Consumer Business Group\'s dual-brand strategy, Honor provides smartphone handsets targeting young consumers but has released tablet computers and wearable technology as well', true);

-- populate roles table
INSERT INTO roles(name, active)
VALUES('ADMIN', true);
INSERT INTO roles(name, active)
VALUES('USER', true);

-- populate users table (password - BCrypt Hash Generator)
INSERT INTO users(first_name, last_name, email, password, contact_number, enabled, role_id)
-- password: n!k@sn1Kos
VALUES('Ivan', 'Ivanov', 'ivan@gmail.com', '$2y$12$i5iA/3OVxdeVLB4h5ttOSecMkd1E0Vj9ywhjL449OuemuD09buJvS', '+375296543218', true, 1);
INSERT INTO users(first_name, last_name, email, password, contact_number, enabled, role_id)
-- password: J@vaC0deG##ks
VALUES('Petr', 'Petrov', 'petr@gmail.com', '$2y$12$r4EhYmgRbDrbmfAMvE1usO/fY8yv1Z.Hp6D9OSIcYelIfjYxUj3e.', '375296543384', true, 2);
INSERT INTO users(first_name, last_name, email, password, contact_number, enabled, role_id)
-- password: n!k1abcD#!
VALUES('Sergey', 'Sergeev', 'sergey@gmail.com', '$2y$12$ZSE/h0gS.Mg.qZnuwzfCxuBd3D1qH3KeY4wL9qZEcAt0FQYNRrBIO', '+375-29-654-32-45', true, 2);

-- populate addresses table 
-- shipping addresses
INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing, shipping)
VALUES(2, 'Platonov street', '', 'Minsk', 'Minsk', 'Belarus', '220034', false, true);
INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing, shipping)
VALUES(3, 'Serdich street', '', 'Minsk', 'Minsk', 'Belarus', '220035', false, true);
-- billing addresses
INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing, shipping)
VALUES(2, 'Platonov street', '', 'Minsk', 'Minsk', 'Belarus', '220034', true, false);
INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing, shipping)
VALUES(3, 'Serdich street', '', 'Minsk', 'Minsk', 'Belarus', '220035', true, false);

-- populate products table
INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('HONOR 20 international version', 'MAIN1581865663258', 10, 400, 10, true, 1);
INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung Galaxy S10 G973', 'MAIN1581866051337', 1, 710, 8, true, 1);
INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi Mi 9T', 'MAIN1581866964489', 3, 343, 6, true, 1);

-- populate descriptions table
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(1, '6.26" IPS (1080x2340)', 'ice white', 'HiSilicon Kirin 980', '32 MP', '48 MP', '6 GB RAM / 128 GB flash memory', 'Li-ion 3 750 mAh', 'IPS', '16 million', 'Bluetooth / Wifi / Nfc');
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(2, '6.1" AMOLED (1440x3040)', 'black', 'Exynos 9820', '10 MP', '12 MP', '8 GB RAM / 128 GB flash memory', 'Li-ion 3 400 mAh', 'AMOLED', '16 million', 'Bluetooth / Wifi / Nfc');
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(3, '6.39" AMOLED (1080x2340)', 'blue', 'Qualcomm Snapdragon 730', '20 MP', '48 MP', '6 GB RAM / 64 GB flash memory', 'Li-pol 4,000 mAh', 'AMOLED', '16 million', 'Bluetooth / Wifi / Nfc');

-- populate views table  
INSERT INTO views(code, product_id)
VALUES('PRD1581865664262', 1);
INSERT INTO views(code, product_id)
VALUES('PRD1581865665263', 1);
INSERT INTO views(code, product_id)
VALUES('PRD1581865666263', 1);
INSERT INTO views(code, product_id)
VALUES('PRD1581865667263', 1);
INSERT INTO views(code, product_id)
VALUES('PRD1581865668263', 1);
INSERT INTO views(code, product_id)
VALUES('PRD1581866052337', 2);
INSERT INTO views(code, product_id)
VALUES('PRD1581866053337', 2);
INSERT INTO views(code, product_id)
VALUES('PRD1581866054337', 2);
INSERT INTO views(code, product_id)
VALUES('PRD1581866055338', 2);
INSERT INTO views(code, product_id)
VALUES('PRD1581866056338', 2);
INSERT INTO views(code, product_id)
VALUES('PRD1581866965493', 3);
INSERT INTO views(code, product_id)
VALUES('PRD1581866966495', 3);

-- populate carts table 
INSERT INTO carts(id, total, cart_items)
VALUES(2, 1510, 2);
-- populate carts table 
INSERT INTO carts(id, total, cart_items)
VALUES(3, 1029, 1);

-- populate cart_items table
INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)
VALUES(2, 800, 1, 2, 400, true);
INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)
VALUES(2, 710, 2, 1, 710, true);
INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)
VALUES(3, 1029, 3, 3, 343, true);

-- populate orders table 
INSERT INTO orders(user_id, total, count, shipping_id, billing_id, date)
VALUES(2, 1510, 3, 1, 3, '2020-03-03 10:37:22');
-- populate carts table 
INSERT INTO orders(user_id, total, count, shipping_id, billing_id, date)
VALUES(3, 1029, 3, 2, 4, '2020-03-05 13:35:21');

-- populate order_items table
INSERT INTO order_items(order_id, total, product_id, product_count, product_price)
VALUES(1, 800, 1, 2, 400);
INSERT INTO order_items(order_id, total, product_id, product_count, product_price)
VALUES(1, 710, 2, 1, 710);
INSERT INTO order_items(order_id, total, product_id, product_count, product_price)
VALUES(2, 1029, 3, 3, 343);
