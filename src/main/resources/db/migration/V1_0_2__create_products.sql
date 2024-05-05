CREATE TABLE product
(
    id            INT UNSIGNED AUTO_INCREMENT NOT NULL,
    product_name  VARCHAR(50)      NOT NULL,
    min_stock     DECIMAL(10,0)    NOT NULL,
    product_desc  VARCHAR(200)     NULL,
    image    VARCHAR(45)     NULL,
     PRIMARY KEY (id)
     );
