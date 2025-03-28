--liquibase formatted sql
--changeset kemerovqq:3
insert into client(login, age) values
    ('test_user', 19);

insert into credentials(login, password) values
    ('test_user', 'asdasdqwresdvsdf');