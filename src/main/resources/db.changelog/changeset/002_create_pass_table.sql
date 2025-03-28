--liquibase formatted sql
--changeset kemerovqq:2
create table if not exists credentials(
    login text primary key references client(login)
            on delete cascade
            on update cascade,
    password text not null
);
