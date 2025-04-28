--liquibase formatted sql
--changeset kemerovqq:2
create table if not exists credentials(
    id int primary key references "client" (client_id)
                                      on delete cascade
                                      on update cascade,
    login text references client(login)
            on delete cascade
            on update cascade,
    password text not null
);
