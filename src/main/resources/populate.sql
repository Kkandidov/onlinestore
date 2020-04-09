-- populate categories table
INSERT INTO categories(name, active)
VALUES('Mobile', true);
INSERT INTO categories(name, active)
VALUES('Television', true);
INSERT INTO categories(name, active)
VALUES('Laptop', true);
INSERT INTO categories(name, active)
VALUES('Tablet', true);
INSERT INTO categories(name, active)
VALUES('Player', true);

-- populate brands table
INSERT INTO brands(name, description, active)
VALUES('Samsung','Samsung, South Korean company that is one of the world’s largest producers of electronic devices. It produces about a fifth of South Korea’s total exports', true);
INSERT INTO brands(name, description, active)
VALUES('Motorola','Motorola is an American multinational telecommunications company. The company was divided into two independent companies, Motorola Mobility (sold to Google and acquired by Lenovo) and Motorola Solutions (the direct successor to Motorola)', true);
INSERT INTO brands(name, description, active)
VALUES('Xiaomi','Xiaomi is a Chinese electronics company founded in 2010 by Lei Jun. The company creates a wide range of products including hardware, software and Internet services', true);
INSERT INTO brands(name, description, active)
VALUES('Apple','Apple is an American multinational technology company headquartered in Cupertino, California, that designs, develops, and sells consumer electronics, computer software, and online services', true);
INSERT INTO brands(name, description, active)
VALUES('Huawei','Huawei is a Chinese information and communications technology company that specializes in telecommunications equipment. The company offers services and consumer electronics including wearables, modems, smartphones, tablets and PCs', true);
INSERT INTO brands(name, description, active)
VALUES('Nokia','Nokia is a Finnish multinational telecommunications, information technology, and consumer electronics company, founded in 1865. Nokia\'s headquarters are in Espoo, in the greater Helsinki metropolitan area', true);
INSERT INTO brands(name, description, active)
VALUES('Asus','Asus is a Taiwan-based multinational computer and phone hardware and electronics company headquartered in Beitou District, Taipei, Taiwan. Its products include desktops, laptops, netbooks, mobile phones, networking equipment, monitors, tablet PCs', true);
INSERT INTO brands(name, description, active)
VALUES('Sony','Sony is a Japanese multinational conglomerate corporation headquartered in Kōnan, Minato, Tokyo. The company owns the largest music entertainment business in the world, the largest video game console business', true);
INSERT INTO brands(name, description, active)
VALUES('Lenovo','Lenovo is a Chinese multinational technology company with headquarters in Beijing. The company manufactures, and sells personal computers, tablet computers, smartphones, workstations, servers, IT management software, and smart televisions', true);
INSERT INTO brands(name, description, active)
VALUES('Honor','Honor is a smartphone brand owned by Huawei Technologies. As part of the Huawei Consumer Business Group\'s dual-brand strategy, Honor provides smartphone handsets targeting young consumers but has released tablet computers and wearable technology as well', true);
INSERT INTO brands(name, description, active)
VALUES('Acer','Acer is a Taiwanese multinational hardware and electronics corporation, headquartered in Xizhi, New Taipei City, Taiwan. Acer\'s products include desktop PCs, laptop PCs tablets, servers, virtual reality devices, displays, smartphones and peripherals', true);
INSERT INTO brands(name, description, active)
VALUES('iHiFi ','Xue Lin Audio is a division of the small Chinese company XueLin Electronics Co., Ltd., founded in Shenzhen in 2002. The company specializes in the development of modern microcontrollers and smart logic chips', true);
INSERT INTO brands(name, description, active)
VALUES('Ritmix','Ritmix is ​​a well-known Korean brand of portable electronics. Starting in 2001 Ritmix has expanded its product range in less than 10 years to a full range of portable electronics: from headphones and voice recorders to GPS navigators and e-books', true);

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

-- populate addresses table 
INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, billing, shipping)
VALUES(2, 'Platonov street', '', 'Minsk', 'Minsk', 'Belarus', '220034', true, false);

-- populate products table, descriptions table, views table  
INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('HONOR 20 international version', 'MAIN1581865663258', 10, 400, 10, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(1, '6.26" IPS (1080x2340)', 'ice white', 'HiSilicon Kirin 980', '32 MP', '48 MP', '6 GB RAM / 128 GB flash memory', 'Li-ion 3 750 mAh', 'IPS', '16 million', 'Bluetooth / Wifi / Nfc');
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

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung Galaxy S10 G973', 'MAIN1581866051337', 1, 710, 8, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(2, '6.1" AMOLED (1440x3040)', 'black', 'Exynos 9820', '10 MP', '12 MP', '8 GB RAM / 128 GB flash memory', 'Li-ion 3 400 mAh', 'AMOLED', '16 million', 'Bluetooth / Wifi / Nfc');
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

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi Mi 9T', 'MAIN1581866964489', 3, 343, 6, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(3, '6.39" AMOLED (1080x2340)', 'blue', 'Qualcomm Snapdragon 730', '20 MP', '48 MP', '6 GB RAM / 64 GB flash memory', 'Li-pol 4,000 mAh', 'AMOLED', '16 million', 'Bluetooth / Wifi / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581866965493', 3);
INSERT INTO views(code, product_id)
VALUES('PRD1581866966495', 3);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Huawei P30 Lite MAR-LX1M Dual', 'MAIN1581867469198', 5, 253, 12, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(4, '6.15" IPS (1080x2312)', 'rich turquoise', 'HiSilicon Kirin 710', '32 MP', '24 MP', '4 GB RAM / 128 GB flash memory', 'Li-pol 3 340 mAh', 'IPS  (LTPS)', '16 million', 'Bluetooth / Wifi / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581867470199', 4);
INSERT INTO views(code, product_id)
VALUES('PRD1581867471199', 4);
INSERT INTO views(code, product_id)
VALUES('PRD1581867472199', 4);
INSERT INTO views(code, product_id)
VALUES('PRD1581867473199', 4);
INSERT INTO views(code, product_id)
VALUES('PRD1581867474199', 4);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Nokia 5.1 Plus', 'MAIN1581865677875', 6, 144, 11, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(5, '5.8 " IPS (720x1520)', 'glossy indigo', 'Mediatek MT6771 Helio P60', '8 MP', '13 MP', '3 GB RAM / 32 GB flash memory', 'Li-ion 3 060 mAh', 'IPS', '16 million', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865678877', 5);
INSERT INTO views(code, product_id)
VALUES('PRD1581865679908', 5);
INSERT INTO views(code, product_id)
VALUES('PRD1581865680908', 5);
INSERT INTO views(code, product_id)
VALUES('PRD1581865681908', 5);
INSERT INTO views(code, product_id)
VALUES('PRD1581865682908', 5);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('ASUS ROG Phone II ZS660KL', 'MAIN1581866127769', 7, 661, 4, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(6, '6.59" AMOLED (1080x2340)', 'black', 'Qualcomm Snapdragon 855+', '24 MP', '48 MP', '8 GB RAM / 128 GB flash memory', 'Li-ion 6,000 mAh', 'AMOLED  (120 Hz)', '16 million', 'Bluetooth / Wifi / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581866128770', 6);
INSERT INTO views(code, product_id)
VALUES('PRD1581866129770', 6);
INSERT INTO views(code, product_id)
VALUES('PRD1581866130770', 6);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Sony Xperia 5 J9210', 'MAIN1581866558933', 8, 842, 9, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(7, '6.1" OLED (1080x2520)', 'black', 'Qualcomm Snapdragon 855', '8 MP', '12 MP', '6 GB RAM / 128 GB flash memory', 'Li-ion 3 140 mAh', 'OLED', '16 million', 'Bluetooth / Wifi / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581866559934', 7);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Lenovo A5 3GB / 32GB', 'MAIN1581866912161', 9, 101, 14, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(8, '5.45" IPS (720x1440)', 'black', 'Mediatek MT6739', '8 MP', '13 MP', '3 GB RAM / 32 GB flash memory', 'Li-pol 4,000 mAh', 'IPS', '16 million', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866913161', 8);
INSERT INTO views(code, product_id)
VALUES('PRD1581866914162', 8);
INSERT INTO views(code, product_id)
VALUES('PRD1581866915162', 8);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Acer Liquid Zest 4G', 'MAIN1581867419089', 11, 147, 6, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(9, '5" IPS (720x1280)', 'black', 'Mediatek MT6735P', '5 MP', '8 MP', '2 GB RAM / 16 GB flash memory', 'Li-ion 2,000 mAh', 'IPS', '16 million', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581867420090', 9);
INSERT INTO views(code, product_id)
VALUES('PRD1581867421090', 9);
INSERT INTO views(code, product_id)
VALUES('PRD1581867422090', 9);
INSERT INTO views(code, product_id)
VALUES('PRD1581867423090', 9);
INSERT INTO views(code, product_id)
VALUES('PRD1581867424091', 9);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Apple iPhone 11', 'MAIN1581867855232', 4, 954, 16, true, 1);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(10, '6.1" IPS (828x1792)', 'white', 'Apple A13 Bionic', '12 MP', '12 MP', '4 GB RAM / 128 GB flash memory', 'Li-ion 3 046 mAh', 'IPS', '16 million', 'Bluetooth / Wifi / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581867856233', 10);
INSERT INTO views(code, product_id)
VALUES('PRD1581867857233', 10);
INSERT INTO views(code, product_id)
VALUES('PRD1581867858233', 10);
INSERT INTO views(code, product_id)
VALUES('PRD1581867859233', 10);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('ASUS TUF Gaming FX505DY-BQ024', 'MAIN1581865268100', 7, 770, 10, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(11, '15.6" 1920 x 1080', 'black', 'AMD Ryzen 5', '1 MP', '', '8 GB / 512 GB SSD', '3 cells 2,526 mAh', 'IPS', 'AMD Radeon RX 560X 4 GB', 'Bluetooth / LAN / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865269100', 11);
INSERT INTO views(code, product_id)
VALUES('PRD1581865270102', 11);
INSERT INTO views(code, product_id)
VALUES('PRD1581865271102', 11);
INSERT INTO views(code, product_id)
VALUES('PRD1581865272106', 11);
INSERT INTO views(code, product_id)
VALUES('PRD1581865273106', 11);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Lenovo IdeaPad S340-15API 81NC00F0RE', 'MAIN1581865831165', 9, 521, 19, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(12, '15.6" 1920 x 1080', 'gray', 'AMD Ryzen 3', '1 MP', '', '8 GB / 256 GB SSD', '52.5 Wh 8 hours  (MobileMark 2014)', 'IPS', 'graphics adapter: built-in', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865832166', 12);
INSERT INTO views(code, product_id)
VALUES('PRD1581865833166', 12);
INSERT INTO views(code, product_id)
VALUES('PRD1581865834167', 12);
INSERT INTO views(code, product_id)
VALUES('PRD1581865835167', 12);
INSERT INTO views(code, product_id)
VALUES('PRD1581865836167', 12);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Acer Aspire 3 A315-55G-59YC NX.HEDEU.008', 'MAIN1581866391484', 11, 635, 4, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(13, '15.6" 1920 x 1080 TN + Film', 'black', 'Intel Core i5', '1 MP', '', '8 GB / SSD 256 GB', '3 cells 3 220 mAh', 'TN + Film', 'NVIDIA GeForce MX230 2 GB', 'Bluetooth / LAN / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866392485', 13);
INSERT INTO views(code, product_id)
VALUES('PRD1581866393485', 13);
INSERT INTO views(code, product_id)
VALUES('PRD1581866394485', 13);
INSERT INTO views(code, product_id)
VALUES('PRD1581866395485', 13);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi Mi Notebook Pro 15.6 JYU4036CN', 'MAIN1581866485662', 3, 945, 6, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(14, '15.6" 1920 x 1080', 'gray', 'Intel Core i5', '1 MP', '', '8 GB / 256 GB SSD', '60 Wh 9 hours', 'IPS', 'NVIDIA GeForce MX150 2 GB', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866486664', 14);
INSERT INTO views(code, product_id)
VALUES('PRD1581866487664', 14);
INSERT INTO views(code, product_id)
VALUES('PRD1581866488664', 14);
INSERT INTO views(code, product_id)
VALUES('PRD1581866489664', 14);
INSERT INTO views(code, product_id)
VALUES('PRD1581866490664', 14);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('MacBook Air 13 "(2017) Notebook [MQD32]', 'MAIN1581866590550', 4, 940, 8, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(15, '13.3" 1440 x 900 TN + Film', 'gray', 'Intel Core i5', '1 MP', '', '8 GB / SSD 128 GB', '54 Wh 12 hours', 'TN + Film', 'graphic adapter: built-in', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866591552', 15);
INSERT INTO views(code, product_id)
VALUES('PRD1581866592557', 15);
INSERT INTO views(code, product_id)
VALUES('PRD1581866593557', 15);
INSERT INTO views(code, product_id)
VALUES('PRD1581866594557', 15);
INSERT INTO views(code, product_id)
VALUES('PRD1581866595557', 15);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung Notebook 7 15.6 ”NP750XBE-K01US', 'MAIN1581866730892', 1, 1500, 7, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(16, '15.6" 1920 x 1080', 'gray', 'Intel Core i7', '1 MP', '', ' 8 GB / SSD 256 GB', '3 cells 55 Wh', 'IPS', 'graphics adapter: built-in', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866731893', 16);
INSERT INTO views(code, product_id)
VALUES('PRD1581866732893', 16);
INSERT INTO views(code, product_id)
VALUES('PRD1581866733893', 16);
INSERT INTO views(code, product_id)
VALUES('PRD1581866734893', 16);
INSERT INTO views(code, product_id)
VALUES('PRD1581866735893', 16);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Huawei MateBook X Pro MACH-W19', 'MAIN1581866870824', 5, 1790, 3, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(17, '13.9" 3000 x 2000', 'gray', 'Intel Core i5', '1 MP', '', '8 GB / 256 GB SSD', '57.4 Wh', 'IPS', 'NVIDIA GeForce MX150 2 GB', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866871824', 17);
INSERT INTO views(code, product_id)
VALUES('PRD1581866872824', 17);
INSERT INTO views(code, product_id)
VALUES('PRD1581866873824', 17);
INSERT INTO views(code, product_id)
VALUES('PRD1581866874824', 17);
INSERT INTO views(code, product_id)
VALUES('PRD1581866875825', 17);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Apple MacBook Pro 13 "Touch Bar 2019 MUHN2', 'MAIN1581866960513', 4, 1502, 2, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(18, '13.3" 2560 x 1600', 'gray', 'Intel Core i5', '1 MP', '', '8 GB / SSD 128 GB', '58.2 Wh 10 hours', 'IPS', 'graphic adapter: built-in', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866961514', 18);
INSERT INTO views(code, product_id)
VALUES('PRD1581866962514', 18);
INSERT INTO views(code, product_id)
VALUES('PRD1581866963515', 18);
INSERT INTO views(code, product_id)
VALUES('PRD1581866964515', 18);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Lenovo IdeaPad S340-15IWL 81N80110RE', 'MAIN1581867064760', 9, 493, 6, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(19, '15.6" 1920 x 1080 TN + Film', 'gray', 'Intel Core i3', '1 MP', '', '8 GB / 256 GB SSD', '52.5 Wh 10 hours  (MobileMark 2014)', 'TN + Film', 'NVIDIA GeForce MX110 2 GB', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581867065762', 19);
INSERT INTO views(code, product_id)
VALUES('PRD1581867066763', 19);
INSERT INTO views(code, product_id)
VALUES('PRD1581867067763', 19);
INSERT INTO views(code, product_id)
VALUES('PRD1581867068763', 19);
INSERT INTO views(code, product_id)
VALUES('PRD1581867069763', 19);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi Mi Notebook Pro 15.6 "2019 JYU4147CN', 'MAIN1581867214521', 3, 1250, 5, true, 3);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(20, '15.6" 1920 x 1080', 'gray', 'Intel Core i7', '1 MP', '', '16 GB / 512 GB SSD', '60 Wh 9 hours', 'IPS', 'NVIDIA GeForce MX250 2 GB', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581867215522', 20);
INSERT INTO views(code, product_id)
VALUES('PRD1581867216523', 20);
INSERT INTO views(code, product_id)
VALUES('PRD1581867217589', 20);
INSERT INTO views(code, product_id)
VALUES('PRD1581867218589', 20);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung UE55RU7470U TV', 'MAIN1581866807129', 1, 745, 8, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(21, '55" 3840x2160 (4K UHD)', 'silver', '4 core', '', '', '', '', 'LCD', ' HDR10, HDR10 +, HLG', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581866808130', 21);
INSERT INTO views(code, product_id)
VALUES('PRD1581866809130', 21);
INSERT INTO views(code, product_id)
VALUES('PRD1581866810133', 21);
INSERT INTO views(code, product_id)
VALUES('PRD1581866811134', 21);
INSERT INTO views(code, product_id)
VALUES('PRD1581866812134', 21);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Sony KD-55XG9505 TV', 'MAIN1581866953294', 8, 1415, 13, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(22, '54.6" 3840x2160(4K UHD)', 'black', 'Sony X1 Ultimate', '', '', '', '', 'LCD', 'HDR10, Dolby Vision, HLG', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581866954313', 22);
INSERT INTO views(code, product_id)
VALUES('PRD1581866955313', 22);
INSERT INTO views(code, product_id)
VALUES('PRD1581866956313', 22);
INSERT INTO views(code, product_id)
VALUES('PRD1581866957313', 22);
INSERT INTO views(code, product_id)
VALUES('PRD1581866958317', 22);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi MI TV 4S 43 "(international version)', 'MAIN1581867041106', 3, 402, 22, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(23, '43" 3840x2160 (4K UHD)', 'black, dark gray', '4-core  (Cortex A55 + Mali 470)', '', '', '', '', 'LCD', 'High Dynamic Range (HDR)', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867042107', 23);
INSERT INTO views(code, product_id)
VALUES('PRD1581867043107', 23);
INSERT INTO views(code, product_id)
VALUES('PRD1581867044107', 23);
INSERT INTO views(code, product_id)
VALUES('PRD1581867045107', 23);
INSERT INTO views(code, product_id)
VALUES('PRD1581867046107', 23);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung UE32N5300AU TV', 'MAIN1581867144771', 1, 300, 18, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(24, '32" 1920x1080 (Full HD)', 'black', '4 core', '', '', '', '', 'LCD', ' HDR10, HLG', 'Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867145772', 24);
INSERT INTO views(code, product_id)
VALUES('PRD1581867146781', 24);
INSERT INTO views(code, product_id)
VALUES('PRD1581867147783', 24);
INSERT INTO views(code, product_id)
VALUES('PRD1581867148786', 24);
INSERT INTO views(code, product_id)
VALUES('PRD1581867149787', 24);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi MI TV 4A 43 "TV (Chinese version)', 'MAIN1581867251215', 3, 274, 12, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(25, '43" 1920x1080 (Full HD)', 'black', '4-core  (Cortex A53 + Mali 450)', '', '', '', '', 'LCD', 'HDR10, HLG', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867252215', 25);
INSERT INTO views(code, product_id)
VALUES('PRD1581867253215', 25);
INSERT INTO views(code, product_id)
VALUES('PRD1581867254215', 25);
INSERT INTO views(code, product_id)
VALUES('PRD1581867255216', 25);
INSERT INTO views(code, product_id)
VALUES('PRD1581867256218', 25);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Sony KD-55XF9005 TV', 'MAIN1581867351843', 8, 1130, 3, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(26, '54.6" 3840x2160 (4K UHD)', 'black', '4 core', '', '', '', '', 'LCD', 'HDR10, Dolby Vision, HLG', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867352847', 26);
INSERT INTO views(code, product_id)
VALUES('PRD1581867353849', 26);
INSERT INTO views(code, product_id)
VALUES('PRD1581867354849', 26);
INSERT INTO views(code, product_id)
VALUES('PRD1581867355849', 26);
INSERT INTO views(code, product_id)
VALUES('PRD1581867356849', 26);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung UE24N4500AU TV', 'MAIN1581867435139', 1, 210, 7, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(27, '24" 1366x768 (HD)', 'black', '4 core', '', '', '', '', 'LCD', '', 'Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867436140', 27);
INSERT INTO views(code, product_id)
VALUES('PRD1581867437140', 27);
INSERT INTO views(code, product_id)
VALUES('PRD1581867438140', 27);
INSERT INTO views(code, product_id)
VALUES('PRD1581867439141', 27);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Sony KD-98ZG9 TV', 'MAIN1581867542678', 8, 86000, 2, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(28, '97.5" 7680x4320 (8K UHD)', 'black', 'ARM Cortex-A73', '', '', '', '', 'LCD', 'HDR10, Dolby Vision, HLG', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867543682', 28);
INSERT INTO views(code, product_id)
VALUES('PRD1581867544682', 28);
INSERT INTO views(code, product_id)
VALUES('PRD1581867545682', 28);
INSERT INTO views(code, product_id)
VALUES('PRD1581867546682', 28);
INSERT INTO views(code, product_id)
VALUES('PRD1581867547682', 28);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi Mi TV 4S 55 "TV (Chinese version)', 'MAIN1581867636396', 3, 210, 4, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(29, '55" 3840x2160 (4K UHD)', 'black', '4-core  (ARM Advanced Multi-Core up to 1.5 GHz)', '', '', '', '', 'LCD', '', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867637399', 29);
INSERT INTO views(code, product_id)
VALUES('PRD1581867638402', 29);
INSERT INTO views(code, product_id)
VALUES('PRD1581867639402', 29);
INSERT INTO views(code, product_id)
VALUES('PRD1581867640403', 29);
INSERT INTO views(code, product_id)
VALUES('PRD1581867641403', 29);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung UE55RU8000U TV', 'MAIN1581867736486', 1, 895, 9, true, 2);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(30, '55" 3840x2160 (4K UHD)', 'black', '4-core', '', '', '', '', 'LCD', 'HDR10, HLG', 'Bluetooth / Wifi / Wi-Fi Direct');
INSERT INTO views(code, product_id)
VALUES('PRD1581867737488', 30);
INSERT INTO views(code, product_id)
VALUES('PRD1581867738488', 30);
INSERT INTO views(code, product_id)
VALUES('PRD1581867739488', 30);
INSERT INTO views(code, product_id)
VALUES('PRD1581867740488', 30);
INSERT INTO views(code, product_id)
VALUES('PRD1581867741488', 30);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Apple iPad 10.2 "32GB MW742 Tablet', 'MAIN1581868388555', 4, 430, 8, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(31, '10.2" IPS (2160x1620)', 'Space Gray', 'Apple A10  (M10 coprocessor)', '1.2 MP', '8 MP', '3 GB RAM / 32 GB flash memory', '32.4 Wh 10 hours  (Wi-Fi)', 'IPS', 'PowerVR GT7600', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581868389556', 31);
INSERT INTO views(code, product_id)
VALUES('PRD1581868390556', 31);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Apple iPad 2018 32GB MR7F2 Tablet', 'MAIN1581868455677', 4, 390, 7, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(32, '9.7" IPS (2048x1536)', 'Space Gray', 'Apple A10  (M10 coprocessor)', '1.2 MP', '8 MP', '2 GB RAM / 32 GB flash memory', '32.4 Wh 10 hours  (Wi-Fi)', 'IPS', ' PowerVR GT7600', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581868456678', 32);
INSERT INTO views(code, product_id)
VALUES('PRD1581868457678', 32);
INSERT INTO views(code, product_id)
VALUES('PRD1581868458678', 32);
INSERT INTO views(code, product_id)
VALUES('PRD1581868459678', 32);
INSERT INTO views(code, product_id)
VALUES('PRD1581868460678', 32);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung Galaxy Tab A10.1 (2019) 2GB / 32GB Tablet', 'MAIN1581868551438', 1, 250, 6, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(33, '10.1" IPS (1920x1200)', 'black', 'Exynos 7904', '5 MP', '8 MP', '2 GB RAM / 32 GB flash memory', '6 150 mAh', 'IPS', 'ARM Mali-G71 MP2', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581868552440', 33);
INSERT INTO views(code, product_id)
VALUES('PRD1581868553442', 33);
INSERT INTO views(code, product_id)
VALUES('PRD1581868554442', 33);
INSERT INTO views(code, product_id)
VALUES('PRD1581868555442', 33);
INSERT INTO views(code, product_id)
VALUES('PRD1581868556442', 33);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Samsung Galaxy Tab S5e LTE 64GB Tablet', 'MAIN1581868660714', 1, 560, 5, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(34, '10.5" AMOLED (2560x1600)', 'black', 'Qualcomm SDM670', '8 MP', '13 MP', '4 GB RAM / 64 GB Flash', '7 040 mAh', 'AMOLED  (sAMOLED)', 'Qualcomm Adreno 615', 'Bluetooth / Wifi / LTE');
INSERT INTO views(code, product_id)
VALUES('PRD1581868661714', 34);
INSERT INTO views(code, product_id)
VALUES('PRD1581868662715', 34);
INSERT INTO views(code, product_id)
VALUES('PRD1581868663715', 34);
INSERT INTO views(code, product_id)
VALUES('PRD1581868664715', 34);
INSERT INTO views(code, product_id)
VALUES('PRD1581868665716', 34);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Lenovo Tab 4 10 TB-X304L 16GB LTE Tablet', 'MAIN1581868768714', 9, 191, 8, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(35, '10.1" IPS (1280x800)', 'black', 'Qualcomm MSM8917', '2 MP', '5 MP', '2 GB RAM / 16 GB flash memory', '7,000 mAh', 'IPS', 'Qualcomm Adreno 308', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581868769737', 35);
INSERT INTO views(code, product_id)
VALUES('PRD1581868770741', 35);
INSERT INTO views(code, product_id)
VALUES('PRD1581868771742', 35);
INSERT INTO views(code, product_id)
VALUES('PRD1581868772742', 35);
INSERT INTO views(code, product_id)
VALUES('PRD1581868773742', 35);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Lenovo Tab 4 8 TB-8504X 16GB LTE Tablet', 'MAIN1581865308600', 9, 194, 9, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(36, '8.0" IPS (1280x800)', 'black', '4-Core', '2 MP', '5 MP', '2 GB RAM / 16 GB flash memory', '4 850 mAh', 'IPS', ' Qualcomm Adreno 308', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865309600', 36);
INSERT INTO views(code, product_id)
VALUES('PRD1581865310601', 36);
INSERT INTO views(code, product_id)
VALUES('PRD1581865311602', 36);
INSERT INTO views(code, product_id)
VALUES('PRD1581865312602', 36);
INSERT INTO views(code, product_id)
VALUES('PRD1581865313625', 36);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Huawei MediaPad M6 10.8 SCM-W09 4GB / 64GB Tablet', 'MAIN1581865424203', 5, 443, 2, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(37, '10.8" IPS (2560x1600)', 'Titanium Gray', 'Hisilicon kirin 980', '8 MP', '13 MP', '4 GB RAM / 64 GB flash memory', '7 500 mAh 12 hours', 'IPS', 'ARM Mali-G76 MP10', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865425244', 37);
INSERT INTO views(code, product_id)
VALUES('PRD1581865426247', 37);
INSERT INTO views(code, product_id)
VALUES('PRD1581865427247', 37);
INSERT INTO views(code, product_id)
VALUES('PRD1581865428247', 37);
INSERT INTO views(code, product_id)
VALUES('PRD1581865429247', 37);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Huawei MediaPad M5 lite BAH2-W19 64GB Tablet', 'MAIN1581865525767', 5, 333, 6, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(38, '10.1" IPS (1920x1200)', 'gray', 'Huawei HiSilicon Kirin 659', '8 MP', '8 MP', '4 GB RAM / 64 GB flash memory', '7 500 mAh', 'IPS', 'ARM Mali T830 MP2', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865526768', 38);
INSERT INTO views(code, product_id)
VALUES('PRD1581865527768', 38);
INSERT INTO views(code, product_id)
VALUES('PRD1581865528769', 38);
INSERT INTO views(code, product_id)
VALUES('PRD1581865529769', 38);
INSERT INTO views(code, product_id)
VALUES('PRD1581865530769', 38);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi Mi Pad 4 LTE 64GB Tablet', 'MAIN1581865645808', 3, 270, 7, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(39, '8.0" IPS (1920x1200)', 'black', 'Qualcomm SDM660', '5 MP', '13 MP', '4 GB RAM / 64 GB flash memory', '6,000 mAh 8 hours - 15 hours', 'IPS', ' Qualcomm Adreno 512', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865646809', 39);
INSERT INTO views(code, product_id)
VALUES('PRD1581865647809', 39);
INSERT INTO views(code, product_id)
VALUES('PRD1581865648809', 39);
INSERT INTO views(code, product_id)
VALUES('PRD1581865649809', 39);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Xiaomi Mi Pad 4 64GB Tablet', 'MAIN1581865735105', 3, 251, 6, true, 4);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(40, '8.0" IPS (1920x1200)', 'black', 'Qualcomm SDM660', '5 MP', '13 MP', '4 GB RAM / 64 GB flash memory', '6,000 mAh 8 hours - 15 hours', 'IPS', 'Qualcomm Adreno 512', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581865736108', 40);
INSERT INTO views(code, product_id)
VALUES('PRD1581865737109', 40);
INSERT INTO views(code, product_id)
VALUES('PRD1581865738109', 40);
INSERT INTO views(code, product_id)
VALUES('PRD1581865739110', 40);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Hi-Fi Player Sony NW-A55 16GB', 'MAIN1581866713476', 8, 220, 3, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(41, '3.1" touch TFT 800 x 480', 'gray', '', '', '', '16 GB', 'Li-ion 20 hours - 1 day, 21 hours', 'Tft', '', 'Bluetooth / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581866714477', 41);
INSERT INTO views(code, product_id)
VALUES('PRD1581866715477', 41);
INSERT INTO views(code, product_id)
VALUES('PRD1581866716477', 41);
INSERT INTO views(code, product_id)
VALUES('PRD1581866717477', 41);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Hi-Fi Player Sony NW-ZX300 64GB', 'MAIN1581866795105', 8, 660, 5, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(42, '3.1" touch TFT 800 x 480', 'black', '', '', '', '64 GB', 'Li-ion 1 day, 2 hours - 1 day, 6 hours', 'Tft', '', 'Bluetooth / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581866796106', 42);
INSERT INTO views(code, product_id)
VALUES('PRD1581866797106', 42);
INSERT INTO views(code, product_id)
VALUES('PRD1581866798106', 42);
INSERT INTO views(code, product_id)
VALUES('PRD1581866799106', 42);
INSERT INTO views(code, product_id)
VALUES('PRD1581866800106', 42);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Apple iPod touch 32GB MP3 Player', 'MAIN1581866898329', 4, 342, 4, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(43, '4" touch screen IPS 640 x 1136', 'blue', '', '1.2 MP', '8 MP', '32 GB', 'Li-ion 8 hours - 1 day, 16 hours', 'IPS  (Retina)', '', 'Bluetooth / Wifi');
INSERT INTO views(code, product_id)
VALUES('PRD1581866899330', 43);
INSERT INTO views(code, product_id)
VALUES('PRD1581866900330', 43);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('MP3 Player Ritmix RF-4950 16GB', 'MAIN1581867053033', 13, 30, 9, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(44, '1.8" TFT 128 x 160', 'white', '', '', '', '16 GB', 'Li-ion 170 mAh 8 ocloc\'k', 'TFT', '', 'Bluetooth');
INSERT INTO views(code, product_id)
VALUES('PRD1581867054035', 44);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Ritmix RF-5100BT 8GB MP3 Player', 'MAIN1581867093536', 13, 37, 5, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(45, '1.8" TFT', 'black', '', '', '', '8 GB', 'Li-ion 1 day, 1 hour', 'TFT', '', 'Bluetooth');
INSERT INTO views(code, product_id)
VALUES('PRD1581867094537', 45);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Hi-Fi Player iHiFi 960 v.2 16GB', 'MAIN1581867149855', 12, 460, 3, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(46, '2"', 'black', '', '', '', '16 GB', 'Li-ion 1,200 mAh', '', '', '');
INSERT INTO views(code, product_id)
VALUES('PRD1581867150857', 46);
INSERT INTO views(code, product_id)
VALUES('PRD1581867151857', 46);
INSERT INTO views(code, product_id)
VALUES('PRD1581867152857', 46);
INSERT INTO views(code, product_id)
VALUES('PRD1581867153858', 46);
INSERT INTO views(code, product_id)
VALUES('PRD1581867154858', 46);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('MP3 Player iHiFi 770 8GB', 'MAIN1581867261951', 12, 300, 7, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(47, 'Tft Screen backlight', 'black', '', '', '', '8 GB', 'Li-ion 13 hours', 'TFT', '', '');
INSERT INTO views(code, product_id)
VALUES('PRD1581867262954', 47);
INSERT INTO views(code, product_id)
VALUES('PRD1581867263954', 47);
INSERT INTO views(code, product_id)
VALUES('PRD1581867264954', 47);
INSERT INTO views(code, product_id)
VALUES('PRD1581867265954', 47);
INSERT INTO views(code, product_id)
VALUES('PRD1581867266954', 47);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('IHiFi 800 8GB MP3 Player', 'MAIN1581867338170', 12, 310, 4, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(48, '', 'black', '', '', '', '8 GB', 'Li-ion 10 hours - 12 hours', '', '', '');
INSERT INTO views(code, product_id)
VALUES('PRD1581867339172', 48);
INSERT INTO views(code, product_id)
VALUES('PRD1581867340188', 48);
INSERT INTO views(code, product_id)
VALUES('PRD1581867341188', 48);
INSERT INTO views(code, product_id)
VALUES('PRD1581867342188', 48);
INSERT INTO views(code, product_id)
VALUES('PRD1581867343189', 48);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Hi-Fi Player Sony NW-A55 16GB', 'MAIN1581867418029', 8, 225, 8, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(49, '3.1" touch TFT 800 x 480', 'gray', '', '', '', '16 GB', 'Li-ion 20 hours - 1 day, 21 hours', 'TFT', '', 'Bluetooth / Nfc');
INSERT INTO views(code, product_id)
VALUES('PRD1581867419030', 49);
INSERT INTO views(code, product_id)
VALUES('PRD1581867420030', 49);
INSERT INTO views(code, product_id)
VALUES('PRD1581867421030', 49);
INSERT INTO views(code, product_id)
VALUES('PRD1581867422030', 49);

INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)
VALUES('Hi-Fi Player FiiO M3K', 'MAIN1581867482147', 12, 88, 3, true, 5);
INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, battery, display_technology, graphics, wireless_communication)
VALUES(50, '2" IPS 240 x 320', 'black', '', '', '', 'microSD, microSDHC, microSDXC', 'Li-ion 1,100 mAh 1 day, 2 hours', 'IPS', '', '');
INSERT INTO views(code, product_id)
VALUES('PRD1581867483148', 50);
INSERT INTO views(code, product_id)
VALUES('PRD1581867484148', 50);
INSERT INTO views(code, product_id)
VALUES('PRD1581867485148', 50);
INSERT INTO views(code, product_id)
VALUES('PRD1581867486148', 50);

-- populate carts table 
INSERT INTO carts(id, total, cart_items)
VALUES(2, 0, 0);