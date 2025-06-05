--liquibase formatted sql
--changeset <postgres>:<create-owners-table>

create table owners
(
    id bigserial
        primary key
);

--rollback DROP TABLE owners;
