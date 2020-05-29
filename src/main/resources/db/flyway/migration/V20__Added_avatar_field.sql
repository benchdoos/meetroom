create table users_avatars (id uuid not null, data varchar(98304), type varchar(36), primary key (id));
alter table if exists users add column avatar_id uuid;
alter table if exists users add constraint FKnj187q2ohu76frb6fvmcji8xy foreign key (avatar_id) references users_avatars;