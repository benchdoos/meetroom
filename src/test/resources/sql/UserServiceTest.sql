--Creating user:
insert into users (id, enabled, need_activation, first_name, last_name, username, avatar_id, email,
                   password)
values ('31d5536a-535b-4601-90f8-97ce6db878ab', true, false, 'TestUser', 'TestUser', 'testuser',
        null, 'correct-test-user-email@mail.com', 'it-does-not-matter');

--Adding user role:
insert into users_roles(user_id, roles_id)
values ('31d5536a-535b-4601-90f8-97ce6db878ab', 'f0d09ed9-e672-457c-ac3e-f5ccfc62f228');
