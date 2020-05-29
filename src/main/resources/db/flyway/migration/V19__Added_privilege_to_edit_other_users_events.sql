INSERT INTO public.privileges (id, name, description)
VALUES ('785a0bd7-e6b8-4582-89ed-2d6fa6308880', 'EVENT:EDIT:OTHER', 'Edit events of other users');

INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', '785a0bd7-e6b8-4582-89ed-2d6fa6308880');