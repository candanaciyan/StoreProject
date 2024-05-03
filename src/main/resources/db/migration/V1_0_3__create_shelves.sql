CREATE TABLE shelves
(
    id              INT UNSIGNED AUTO_INCREMENT NOT NULL,
    shelf_count     INT NULL,
    shelf_capacity  INT NOT NULL DEFAULT 5,
    product_id      INT UNSIGNED NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_product_id
    FOREIGN KEY (product_id)
    REFERENCES products (id)
);
