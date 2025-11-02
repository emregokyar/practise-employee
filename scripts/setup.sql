-- Drop existing tables
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS employee;

-- Create users
CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL,
    PRIMARY KEY (username)
);

INSERT INTO users (username, password, enabled) VALUES
    ('ana', '{noop}as', TRUE),
    ('sydney', '{noop}as', TRUE),
    ('emre', '{noop}as',TRUE);

-- Create authorities table
-- Create authorities table (depends on users)
CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT authorities_unique UNIQUE (username, authority),
    CONSTRAINT fk_authorities_users
        FOREIGN KEY (username)
        REFERENCES users(username)
        ON DELETE CASCADE
);

INSERT INTO authorities (username, authority) VALUES
    ('ana', 'ROLE_EMPLOYEE'),
    ('sydney', 'ROLE_EMPLOYEE'),
    ('sydney', 'ROLE_MANAGER'),
    ('emre', 'ROLE_EMPLOYEE'),
    ('emre', 'ROLE_MANAGER'),
    ('emre', 'ROLE_ADMIN');

-- Create employee table
CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(100)
);

INSERT INTO employee (first_name, last_name, email) VALUES
    ('Lessie', 'Adams', 'la@gmail.com'),
    ('Emre', 'Gokyar', 'eg@gmail.com'),
    ('Bruce', 'Lee', 'bl@gmail.com'),
    ('Ana', 'Armes', 'aa@gmail.com'),
    ('Geek', 'Geek', 'gg@gmail.com');