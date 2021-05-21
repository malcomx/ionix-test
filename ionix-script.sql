CREATE DATABASE ionix_db;

CREATE TABLE ionix_user (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(40) NOT NULL,
    phone VARCHAR(15),
    PRIMARY KEY (id)
);
