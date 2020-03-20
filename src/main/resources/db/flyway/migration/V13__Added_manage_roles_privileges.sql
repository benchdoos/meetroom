INSERT INTO public.privileges (id, name)
VALUES ('f4241e77-e473-4d1a-9176-5d8c22eb259f', 'MANAGE_ROLES:USE');
INSERT INTO public.privileges (id, name)
VALUES ('548df413-3ebb-4a8e-ab23-6bcb148ac550', 'MANAGE_ROLES:CREATE');
INSERT INTO public.privileges (id, name)
VALUES ('dc998e44-b1b0-4d8d-a0d5-6077d66650dd', 'MANAGE_ROLES:UPDATE');
INSERT INTO public.privileges (id, name)
VALUES ('049ecc46-c617-43f0-beae-9a1fbf6c1afd', 'MANAGE_ROLES:UPDATE');

INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', 'f4241e77-e473-4d1a-9176-5d8c22eb259f');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', '548df413-3ebb-4a8e-ab23-6bcb148ac550');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', 'dc998e44-b1b0-4d8d-a0d5-6077d66650dd');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', '049ecc46-c617-43f0-beae-9a1fbf6c1afd');
