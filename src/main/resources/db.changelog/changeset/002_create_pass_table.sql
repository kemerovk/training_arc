--liquibase formatted sql
--changeset kemerovqq:2
create table if not exists credentials(
    id int primary key references "client" (id)
        on delete cascade
        on update cascade,
    login text references "client" (login)
            on delete cascade
            on update cascade,
    email text references "client" (email)
            on delete cascade
            on update cascade,
    password text not null
);
