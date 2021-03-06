create table if not exists meeting_rooms
(
    id       uuid not null
        constraint meeting_rooms_pkey
            primary key,
    location varchar(255)
        constraint uk_aqs0hc33i3102g2tyerwdh11
            unique,
    name     varchar(255)
        constraint uk_4dh7tu7n2yb9upgkaydm1p8er
            unique,
    enabled  boolean
);

create table if not exists roles
(
    id   uuid         not null
        constraint roles_pkey
            primary key,
    role varchar(255) not null,
    name varchar(255)
        constraint uk_ofx66keruapi6vyqpv6f2or37
            unique
);

create table if not exists users
(
    id         uuid         not null
        constraint users_pkey
            primary key,
    enabled    boolean      not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    username   varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

create table if not exists events
(
    id              uuid      not null
        constraint meeting_events_pkey
            primary key,
    from_date       timestamp not null,
    to_date         timestamp not null,
    meeting_room_id uuid      not null
        constraint fkiv87smnxp849nlrqgiluck9oc
            references meeting_rooms,
    user_id         uuid      not null
        constraint fkp5sgdxtbdmb1r2de6bblh7cvx
            references users,
    deleted         boolean,
    description     varchar(3000),
    title           varchar(255)
);

create table if not exists users_roles
(
    user_id  uuid not null
        constraint fk2o0jvgh89lemvvo17cbqvdxaa
            references users,
    roles_id uuid not null
        constraint uk_60loxav507l5mreo05v0im1lq
            unique
        constraint fka62j07k5mhgifpp955h37ponj
            references roles,
    constraint users_roles_pkey
        primary key (user_id, roles_id)
);

