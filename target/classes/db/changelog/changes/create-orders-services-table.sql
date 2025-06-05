--liquibase formatted sql
--changeset <postgres>:<create-orders-services-table>

create table orders_services
(
    order_id   bigint not null
        constraint fkq863ndc65lt9lgj0jg1h8ravg
            references orders,
    service_id bigint not null
        constraint uk_6stku4m0dy2cj3phyilue8998
            unique
        constraint fkmlxtixo7scj7qi4p35vltpsg2
            references services
);

--rollback DROP TABLE orders_services;
