create table password_reset_requests
(
    id               uuid      not null,
    active           boolean   not null,
    expires          timestamp not null,
    requested        timestamp not null,
    requested_by_id  uuid      not null,
    requested_for_id uuid      not null,
    primary key (id)
);
alter table if exists password_reset_requests
    add constraint FKacgv5j0pk7synngmfymxrxdyv foreign key (requested_by_id) references users;
alter table if exists password_reset_requests
    add constraint FKfgwv6b6yfh8d7k72mvk0whvp4 foreign key (requested_for_id) references users;