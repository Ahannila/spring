CREATE TABLE IF NOT EXISTS t_car (
    id BIGSERIAL,
    brand TEXT,
    year INT,
    CONSTRAINT t_car_pk PRIMARY KEY (id)
);
SELECT setval(pg_get_serial_sequence('t_car', 'id'), 1);
