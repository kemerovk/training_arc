--liquibase formatted sql
--changeset TestUsers_sql:1
create table client(
    id integer primary key generated by default as identity ,
    name text not null,
    age int check (age > 0)
);