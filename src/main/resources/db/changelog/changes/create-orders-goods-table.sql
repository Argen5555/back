--liquibase formatted sql
--changeset <postgres>:<create-orders-goods-table>

create table orders_goods
(
    order_id bigint not null
        constraint fkaoqjqu5li3448xo657dvp6teq
            references orders,
    goods_id bigint not null
        constraint fknsv6m7fvy9pmg1b122f7o62x1
            references goods
);

--rollback DROP TABLE orders_goods;
