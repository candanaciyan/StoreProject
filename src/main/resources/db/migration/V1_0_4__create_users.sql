CREATE TABLE users
(
    id        BINARY(16)   NOT NULL,
    name     VARCHAR(50) NOT NULL,
    surname     VARCHAR(50) NOT NULL,
    email     VARCHAR(50) NOT NULL,
    password  VARCHAR(50) NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP  NULL,
    update_desc	VARCHAR(100)  NULL,
    role_id   INT  NOT NULL,
    
    PRIMARY KEY (id),
    CONSTRAINT fk_role_id
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
);
