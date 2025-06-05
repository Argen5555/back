--liquibase formatted sql
--changeset <postgres>:<create-goods-table>

create table goods
(
    id    bigserial
        primary key,
    name  varchar(255),
    price numeric(19, 2)
);

--rollback DROP TABLE goods;
