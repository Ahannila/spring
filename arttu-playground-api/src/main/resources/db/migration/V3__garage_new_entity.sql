CREATE TABLE IF NOT EXISTS t_garage (
    id BIGSERIAL,
    name TEXT,
    location text,
    CONSTRAINT t_garage_pk PRIMARY KEY (id)
);

SELECT setval(pg_get_serial_sequence('t_garage', 'id'), 1);
