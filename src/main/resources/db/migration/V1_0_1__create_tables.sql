CREATE TABLE role
(
    id   INT  AUTO_INCREMENT NOT NULL PRIMARY KEY,
    role_name VARCHAR(50)     NOT NULL
);

CREATE TABLE product
(
    id            INT UNSIGNED AUTO_INCREMENT NOT NULL,
    product_name  VARCHAR(50)      NOT NULL,
    min_stock     DECIMAL(10,0)    NOT NULL,
    product_desc  VARCHAR(200)     NULL,
    image    VARCHAR(45)     NULL,
     PRIMARY KEY (id)
     );

  CREATE TABLE shelf
(
    id              INT UNSIGNED AUTO_INCREMENT NOT NULL,
    shelf_count     INT NULL,
    shelf_capacity  INT NOT NULL DEFAULT 5,
    product_id      INT UNSIGNED NULL,
    product_name    VARCHAR(50) NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_product_id
    FOREIGN KEY (product_id)
    REFERENCES product (id)
);


CREATE TABLE user
(
    id        BINARY(16)   NOT NULL,
    name     VARCHAR(50) NOT NULL,
    surname     VARCHAR(50) NOT NULL,
    email     VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(200) NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP  NULL,
    role_id   INT  NOT NULL,
    
    PRIMARY KEY (id),
    CONSTRAINT fk_role_id
        FOREIGN KEY (role_id)
            REFERENCES role (id)
);

     