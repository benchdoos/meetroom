create table user_settings
(
    id                        uuid                  not null,
    dark_mode_enabled         boolean default false not null,
    show_email_to_other_users boolean default true  not null,
    primary key (id)
);

alter table if exists users
    add column settings_id uuid;

alter table if exists users
    add constraint FKmn3gkgvehtqlalfpg4um0fvks foreign key (settings_id) references user_settings;