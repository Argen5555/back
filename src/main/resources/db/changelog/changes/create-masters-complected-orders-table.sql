--liquibase formatted sql
--changeset <postgres>:<create-masters-complected-orders-table>

create table masters_complected_orders
(
    master_id           bigint not null
        constraint fk7y53xagi8p6rkms8jxhd7cqqv
            references masters,
    complected_order_id bigint not null
        constraint fkb4u6y58wco88n46ygt9e6j0pf
            references orders
);

--rollback DROP TABLE masters_complected_orders;
