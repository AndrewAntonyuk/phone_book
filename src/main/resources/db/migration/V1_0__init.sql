CREATE TABLE IF NOT EXISTS phone_book
(
    id           INT(11)        AUTO_INCREMENT,
    first_name   VARCHAR(255)   NOT NULL,
    middle_name  VARCHAR(255),
    last_name    VARCHAR(255),
    description  VARCHAR(255),
    phone        VARCHAR(20)    NOT NULL,

    PRIMARY KEY (id)
);