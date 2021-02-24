CREATE TABLE IF NOT EXISTS workouts
(
    workout_id BIGSERIAL primary key,
    workout_date DATE not null,
    user_id BIGINT not null references users
);

CREATE TABLE IF NOT EXISTS workout_sets (
    workout_set_id BIGSERIAL primary key,
    workout_id BIGINT not null references workouts,
    exercise_id BIGINT not null references exercises,
    measure_unit varchar(100) not null,
    set_value INTEGER,
    reps INTEGER
);

INSERT INTO workouts (workout_id, workout_date, user_id)
values (1, NOW(), 1), (2, NOW(), 1), (3, NOW(), 2);

INSERT INTO workout_sets(workout_set_id, workout_id, exercise_id, measure_unit, set_value, reps)
values (1, 1, 1, 'KG', 100, 10),
       (2, 1, 1, 'KG', 110, 8),
       (3, 1, 2, 'OWN_WEIGHT', null, 15),
       (4, 2, 1, 'KG', 50, 10),
       (5, 3, 1, 'KG', 35, 15);