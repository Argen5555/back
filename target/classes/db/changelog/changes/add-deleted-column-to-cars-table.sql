--liquibase formatted sql
--changeset <postgres>:<add-deleted-column-to-cars-table>

alter table cars add deleted boolean;

--rollback ALTER TABLE cars DROP COLUMN deleted;
