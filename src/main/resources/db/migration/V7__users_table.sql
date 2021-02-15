CREATE TABLE if not exists users
(
    user_id     bigserial primary key,
    username    varchar(250) UNIQUE not null,
    password    varchar(250)        not null,
    last_name   varchar(250)        not null,
    first_name  varchar(250)        not null,
    middle_name varchar(250)        not null
);

INSERT INTO users(username, password, last_name, first_name)
VALUES ('admin', 'pass', 'Adminov', 'Admin'),
       ('user', 'pass', 'Userov', 'Userchik');