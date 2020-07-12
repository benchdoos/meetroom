truncate users cascade;
truncate users_roles;

--Creating user:
insert into users (id, enabled, need_activation, first_name, last_name, username, avatar_id, email,
                   password)
values ('31d5536a-535b-4601-90f8-97ce6db878ab', true, false, 'TestUser1', 'TestUser1', 'testuser1',
        null, 'testuser1@mail.com', 'it-does-not-matter1');

insert into users (id, enabled, need_activation, first_name, last_name, username, avatar_id, email,
                   password)
values ('7952146d-36bb-4d89-89bf-7dd050c28120', true, false, 'TestUser2', 'TestUser2', 'testuser2',
        null, 'testuser2@mail.com', 'it-does-not-matter2');

--Adding user roles:
insert into users_roles(user_id, roles_id)
values ('31d5536a-535b-4601-90f8-97ce6db878ab', 'f0d09ed9-e672-457c-ac3e-f5ccfc62f228');

insert into users_roles(user_id, roles_id)
values ('7952146d-36bb-4d89-89bf-7dd050c28120', 'f0d09ed9-e672-457c-ac3e-f5ccfc62f228');
