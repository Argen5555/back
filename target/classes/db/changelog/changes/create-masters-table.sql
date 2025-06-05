--liquibase formatted sql
--changeset <postgres>:<create-masters-table>

create table masters
(
    id   bigserial
        primary key,
    name varchar(255)
);

--rollback DROP TABLE masters;
