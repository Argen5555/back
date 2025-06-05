--liquibase formatted sql
--changeset <postgres>:<create-services-table>

create table public.services
(
    id         bigserial
        primary key,
    diagnostic boolean,
    price      numeric(19, 2),
    status     varchar(255),
    master_id  bigint
        constraint fk565hd47u11qajksyfi4gqrfu0
            references public.masters,
    order_id   bigint
        constraint fknmykpsxcf4bgaecn9g3vdbc1s
            references public.orders
);

--rollback DROP TABLE services;
