CREATE TABLE transports (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    speed INT NOT NULL
);

CREATE TABLE couriers (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location_x INT NOT NULL,
    location_y INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    transport_id UUID NOT NULL,
    FOREIGN KEY (transport_id) REFERENCES transports(id)
);

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    location_x INT NOT NULL,
    location_y INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    courier_id UUID
);