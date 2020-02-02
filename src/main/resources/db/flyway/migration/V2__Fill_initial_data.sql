--Creating basic users: admin, user. Creating roles: ROLE_ADMIN, ROLE_USER. Attaching roles.

--Creating users:
INSERT INTO public.users (id, enabled, first_name, last_name, password, username)
VALUES ('a1b43961-b306-400b-82cc-b7d933c9828d', true, 'admin', 'admin',
        '$2a$10$xcCjZxd2ceEHxndbJtIIi.AoJcTWjFr3li8QfylHe07XM1fg1CGtm', 'admin'); --password: admin
INSERT INTO public.users (id, enabled, first_name, last_name, password, username)
VALUES ('fda7f710-7429-456a-a0d6-d1205c7e94cd', true, 'user', 'user',
        '$2a$10$xPaTrPygZdf63vw9jwbRt.AFmCV6faZ/FuhQ8o2RaRqBr7941qLWG', 'user'); --password: user


--Creating roles
INSERT INTO public.roles (id, role)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', 'ROLE_ADMIN');
INSERT INTO public.roles (id, role)
VALUES ('f0d09ed9-e672-457c-ac3e-f5ccfc62f228', 'ROLE_USER');


--Attaching roles to users
INSERT INTO public.users_roles (user_id, roles_id)
VALUES ('a1b43961-b306-400b-82cc-b7d933c9828d', '2936d396-9fe0-48a2-b07c-24c0b5f031df');
INSERT INTO public.users_roles (user_id, roles_id)
VALUES ('fda7f710-7429-456a-a0d6-d1205c7e94cd', 'f0d09ed9-e672-457c-ac3e-f5ccfc62f228');