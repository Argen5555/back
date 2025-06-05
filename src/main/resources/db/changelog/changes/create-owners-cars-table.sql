--liquibase formatted sql
--changeset <postgres>:<create-owners-cars-table>

create table owners_cars
(
    owner_id bigint not null
        constraint fkl3bgvt7natjt1rydg5avnmhcd
            references owners,
    car_id   bigint not null
        constraint uk_4s5l3sbsvheqehaat4rg8qn2g
            unique
        constraint fkobosrw1pt1tmgeqeftq01ldae
            references cars
);

--rollback DROP TABLE owners_cars;
