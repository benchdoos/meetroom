--Creating table
create table user_email_update_requests
(
    id                          uuid      not null,
    active                      boolean   not null,
    expires                     timestamp not null,
    new_email_address           varchar(320),
    new_email_address_activated boolean   not null,
    new_email_confirmation      uuid,
    old_email_address_activated boolean   not null,
    old_email_confirmation      uuid,
    requested                   timestamp not null,
    requested_for_id            uuid      not null,
    primary key (id)
);

--Adding foreign key
alter table if exists user_email_update_requests
    add constraint FKhl1oervmqgd92cvnfd083smj6 foreign key (requested_for_id) references users;

--Adding unique constraint for new email
alter table if exists user_email_update_requests
    add constraint UK_nti60rmf7e4hunq38ig2hxd4b unique (new_email_confirmation);
alter table if exists user_email_update_requests
    drop constraint if exists UK_trhgo552tdnm74k113t6yya7a;

--Adding unique constraint for old email
alter table if exists user_email_update_requests
    add constraint UK_trhgo552tdnm74k113t6yya7a unique (old_email_confirmation);
alter table if exists user_email_update_requests
    add constraint FKhl1oervmqgd92cvnfd083smj6 foreign key (requested_for_id) references users;