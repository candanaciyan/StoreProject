INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'a', 'a', 'a@gmail.com', 'a', CURRENT_TIMESTAMP, 1);

INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'b', 'b', 'b@gmail.com', 'b', CURRENT_TIMESTAMP, 2);

INSERT INTO user (id,name, surname, email, password, registration_date, role_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'c', 'c', 'c@gmail.com', 'c', CURRENT_TIMESTAMP, 3);


