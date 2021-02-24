CREATE TABLE exercises
(
    id   bigserial primary key,
    name varchar(200)
);

INSERT INTO exercises (name)
values ('Жим лёжа широким хватом на горизонтальной скамье'),
       ('Подтягивания на перекладине прямым широким хватом')