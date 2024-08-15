CREATE TABLE reservations (
    id          SERIAL PRIMARY KEY,
    checkin     TIMESTAMP   NOT NULL,
    checkout    TIMESTAMP   NOT NULL,
    status      VARCHAR(10) NOT NULL,
    customer_id BIGINT      NOT NULL,
    room_id     BIGINT      NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (room_id) REFERENCES rooms (id)
);