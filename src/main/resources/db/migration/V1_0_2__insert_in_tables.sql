INSERT INTO role (role_name) VALUES ('admin');
INSERT INTO role (role_name) VALUES ('supervisor');
INSERT INTO role (role_name) VALUES ('reporter');


INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES ('Baldo Pirinc',10, 'baldo pirinc','/assets/img/baldo.jpg');
INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES('Osmancik Pirinc',11, 'osmancik pirinc','/assets/img/osmancik.jpg');
INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES ('Kirmizi Mercimek',12, 'kirmizi mercimek','/assets/img/kirmizi.jpg');
INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES  ('Yesil Mercimek',8, 'yesil mercimek','/assets/img/yesil.jpg');
INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES  ('Borulce Fasulye',14, 'borulce fasulye','/assets/img/borulce.jpg');
INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES  ('Kuru Fasulye',12, 'kuru fasulye','/assets/img/kuru.jpg');
INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES ('Nohut',13, 'nohut','/assets/img/nohut.jpg');
INSERT INTO product (product_name,min_stock, product_desc ,image) VALUES  ('Beyaz Nohut',10, 'beyaz nohut','/assets/img/beyaznohut.jpg');


INSERT INTO shelf (shelf_count,product_id) VALUES (5,1);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,1);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,1);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,1);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,1);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,2);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,2);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,2);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,2);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,2);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,1);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,2);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,3);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,4);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,5);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,6);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,7);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,8);
INSERT INTO shelf (shelf_count,product_id) VALUES (5,8);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);
INSERT INTO shelf (shelf_count) VALUES (0);



INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Ali', 'Veli', 'a@gmail.com', 'a', CURRENT_TIMESTAMP, 1);

INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Mehmet', 'Arslan', 's@gmail.com', 's', CURRENT_TIMESTAMP, 2);

INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Tahsin', 'Mizrak', 'r@gmail.com', 'r', CURRENT_TIMESTAMP, 3);


