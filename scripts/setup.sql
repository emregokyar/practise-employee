DROP TABLE IF EXISTS employee;

CREATE TABLE employee(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(45)
);

INSERT INTO employee(first_name, last_name, email) VALUES
    ('Lessie', 'Adams' , 'la@gmail.com'),
    ('Emre', 'Gokyar' , 'eg@gmail.com'),
    ('Bruce', 'Lee' , 'bl@gmail.com'),
    ('Ana', 'Armes' , 'aa@gmail.com'),
    ('Geek', 'Geek' , 'gg@gmail.com');