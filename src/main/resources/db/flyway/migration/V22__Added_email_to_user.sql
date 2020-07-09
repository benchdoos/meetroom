alter table if exists users add column email varchar(320);
alter table if exists users drop constraint if exists UK_6dotkott2kjsp8vw4d0m25fb7;
alter table if exists users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);