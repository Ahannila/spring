ALTER TABLE t_garage ADD COLUMN car bigint
CONSTRAINT car_fk_id REFERENCES t_car(id) ON DELETE SET NULL;