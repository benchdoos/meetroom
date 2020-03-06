create table privileges
(
    id   uuid not null,
    name varchar(255),
    primary key (id)
);

create table roles_privileges
(
    roles_id     uuid not null,
    privilege_id uuid not null
);

alter table if exists roles_privileges
    add constraint FK5duhoc7rwt8h06avv41o41cfy foreign key (privilege_id) references privileges;

alter table if exists roles_privileges
    add constraint FK6mum48rg6jh9yb6aqmfv946ka foreign key (roles_id) references roles;