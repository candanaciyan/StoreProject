CREATE TABLE products
(
    id            INT UNSIGNED AUTO_INCREMENT NOT NULL,
    product_name  VARCHAR(50)      NOT NULL,
    stock_quantity DECIMAL(10,0)   NOT NULL,
    min_stock     DECIMAL(10,0)    NOT NULL,
    product_desc  VARCHAR(200)     NULL,
     PRIMARY KEY (id)
     );
