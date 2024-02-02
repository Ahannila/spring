CREATE TABLE IF NOT EXISTS t_owner (
    id BIGSERIAL,
    firstname TEXT,
    lastname text,
    CONSTRAINT t_owner_pk PRIMARY KEY (id)
);

SELECT setval(pg_get_serial_sequence('t_owner', 'id'), 1);


ALTER TABLE t_car ADD COLUMN owner bigint
CONSTRAINT owner_fk_id REFERENCES t_owner(id) ON DELETE SET NULL;