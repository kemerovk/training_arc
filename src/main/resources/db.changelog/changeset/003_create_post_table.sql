--liquibase formatted sql
--changeset kemerovqq:3
create table if not exists post(
    id int primary key generated always as identity,
    title text not null unique,
    content text not null,
    path_to_pic text,
    author int not null references client (id)
                               on update cascade on delete cascade ,
    date timestamp not null default now()
);
