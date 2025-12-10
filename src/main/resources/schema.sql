CREATE TABLE Product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price NUMERIC,
    creation_datetime TIMESTAMP
);

CREATE TABLE product_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    product_id INT REFERENCES Product(id)
);
