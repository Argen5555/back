--liquibase formatted sql
--changeset <postgres>:<create-owners-orders-table>

create table owners_orders
(
    owner_id bigint not null
        constraint fk72iccnam7p59oel627kryqyv2
            references owners,
    order_id bigint not null
        constraint uk_3nyffnq542wils22wjbf779a7
            unique
        constraint fkhdu8v0nt2g2wtp40r58tvfy4b
            references orders
);

--rollback DROP TABLE owners_orders;
