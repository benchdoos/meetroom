--Creating role:
insert into roles (color, internal_name, name, id)
values ('#0061ff', 'ROLE_GUEST', 'Guest', '99e16714-5bd0-4f26-9fcf-a383e270aabc');

insert into roles_privileges (roles_id, privilege_id)
values ('99e16714-5bd0-4f26-9fcf-a383e270aabc', '080d4e4e-420f-42ce-bfb7-7ecc9de5de5a');

insert into roles_privileges (roles_id, privilege_id)
values ('99e16714-5bd0-4f26-9fcf-a383e270aabc', 'c5ed871a-58dc-41f5-856c-98f653a49614');

insert into roles_privileges (roles_id, privilege_id)
values ('99e16714-5bd0-4f26-9fcf-a383e270aabc', '7f34cdf1-0abd-40c9-b370-23f2b178acf6');

--Creating user: (pwd - guest)
insert into users (id, enabled, need_activation, first_name, last_name, username, avatar_id, email, password)
values ('15459a57-f389-4f3a-81c3-294bee832708', true, false, 'Guest', 'Guest', 'guest', null, null,
        '$2a$10$ss5QH6M4puTat1PLG6d0CuA7feZ35DsKcVV7bxLvQ66z6Adt9sc3O');

--Adding user role:
insert into users_roles(user_id, roles_id)
values ('15459a57-f389-4f3a-81c3-294bee832708', '99e16714-5bd0-4f26-9fcf-a383e270aabc');

