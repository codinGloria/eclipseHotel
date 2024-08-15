CREATE TABLE rooms (
    id          SERIAL PRIMARY KEY,
    room_number VARCHAR(55)    NOT NULL,
    type        VARCHAR(55)    NOT NULL,
    price       NUMERIC(10, 2) NOT NULL
);