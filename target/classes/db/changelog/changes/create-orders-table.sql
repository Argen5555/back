--liquibase formatted sql
--changeset <postgres>:<create-orders-table>

create table orders
(
    id              bigserial
        primary key,
    completion_time timestamp,
    description     varchar(255),
    order_time      timestamp,
    price           numeric(19, 2),
    status          varchar(255),
    car_id          bigint
        constraint fkd2p23ixwrrt395glgi9nnbj23
            references cars
);

--rollback DROP TABLE orders;
