CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)  NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    age INTEGER
);

CREATE SEQUENCE employee_seq;
