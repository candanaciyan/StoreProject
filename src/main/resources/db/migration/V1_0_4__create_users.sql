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
