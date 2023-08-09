INSERT INTO employee (id, name, surname, email, age) VALUES (1, 'Fco. Javier', 'Delgado Vallano', 'franvallano@gmail.com', 31);
-- To avoid duplicate key error due to id sequence set in 1 instead of 2
ALTER SEQUENCE employee_seq RESTART WITH 2;
