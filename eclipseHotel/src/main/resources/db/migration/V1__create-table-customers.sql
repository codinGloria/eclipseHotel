CREATE TABLE customers (
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    phone     VARCHAR(20)  NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);