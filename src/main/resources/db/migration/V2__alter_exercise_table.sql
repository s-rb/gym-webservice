ALTER TABLE if exists exercises
    RENAME COLUMN id TO exercise_id;
ALTER TABLE if exists exercises
    RENAME COLUMN name TO exercise_name;
ALTER TABLE if exists exercises
    ADD COLUMN exercise_description varchar(500);

CREATE TABLE tags
(
    tag_id   bigserial primary key,
    tag_name varchar(100)
);

INSERT INTO tags(tag_name)
values ('Жим'),
       ('Штанга'),
       ('Спина');

CREATE TABLE exercises_tags
(
    exercises_tags_id bigserial primary key,
    exercise_id       bigint not null references exercises,
    tag_id            bigint not null references tags
);