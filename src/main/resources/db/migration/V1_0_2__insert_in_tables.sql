INSERT INTO role (role_name) VALUES ('admin');
INSERT INTO role (role_name) VALUES ('supervisor');
INSERT INTO role (role_name) VALUES ('reporter');


INSERT INTO product (product_name,min_stock, product_desc) VALUES ('Baldo Pirinc',10, 'baldo pirinc');
INSERT INTO product (product_name,min_stock, product_desc) VALUES('Osmancik Pirinc',15, 'osmancik pirinc');
INSERT INTO product (product_name,min_stock, product_desc) VALUES ('Kirmizi Mercimek',18, 'kirmizi mercimek');
INSERT INTO product (product_name,min_stock, product_desc) VALUES  ('Yesil Mercimek',20, 'yesil mercimek');
INSERT INTO product (product_name,min_stock, product_desc) VALUES  ('Borulce Fasulye',15, 'borulce fasulye');
INSERT INTO product (product_name,min_stock, product_desc) VALUES  ('Kuru Fasulye',12, 'kuru fasulye');
INSERT INTO product (product_name,min_stock, product_desc) VALUES ('Nohut',13, 'nohut');
INSERT INTO product (product_name,min_stock, product_desc) VALUES  ('Beyaz Nohut',14, 'beyaz nohut');


INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count,product_id) VALUES (1,1);
INSERT INTO shelf (shelf_count,product_id) VALUES (1,2);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,3);
INSERT INTO shelf (shelf_count,product_id) VALUES (3,4);
INSERT INTO shelf (shelf_count,product_id) VALUES (3,5);
INSERT INTO shelf (shelf_count,product_id) VALUES (3,6);
INSERT INTO shelf (shelf_count,product_id) VALUES (3,7);
INSERT INTO shelf (shelf_count,product_id) VALUES (3,8);
INSERT INTO shelf (shelf_count,product_id) VALUES (3,8);



INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Ali', 'Veli', 'admin@gmail.com', 'admin', CURRENT_TIMESTAMP, 1);

INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Mehmet', 'Arslan', 'super@gmail.com', 'super', CURRENT_TIMESTAMP, 2);

INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Tahsin', 'Mizrak', 'r@gmail.com', 'r', CURRENT_TIMESTAMP, 3);


