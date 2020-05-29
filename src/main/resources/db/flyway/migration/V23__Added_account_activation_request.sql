create table account_activation_requests
(
    id               uuid      not null,
    active           boolean   not null,
    expires          timestamp not null,
    requested        timestamp not null,
    requested_for_id uuid      not null,
    primary key (id)
);
alter table if exists account_activation_requests
    add constraint FKcl385op5iqiaelcprxgjm0nbr foreign key (requested_for_id) references users;