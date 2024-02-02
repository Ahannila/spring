CREATE TABLE IF NOT EXISTS t_common (
                                     id BIGSERIAL,
                                     generic_value TEXT,
                                     CONSTRAINT t_common_pk PRIMARY KEY (id)
    );
SELECT setval(pg_get_serial_sequence('t_common', 'id'), 1);
