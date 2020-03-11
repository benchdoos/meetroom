alter table if exists users
    add column need_activation boolean default false not null;