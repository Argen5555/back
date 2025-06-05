--liquibase formatted sql
--changeset <postgres>:<create-cars-table>

create table cars
(
    id       bigserial
        primary key,
    brand    varchar(255),
    model    varchar(255),
    number   varchar(255),
    year     integer not null,
    owner_id bigint
        constraint fkhcsx2hgskre1qwetp67r7qfr
            references owners
);

--rollback DROP TABLE cars;
