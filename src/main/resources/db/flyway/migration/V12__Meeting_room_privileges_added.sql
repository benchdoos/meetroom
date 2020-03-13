INSERT INTO public.privileges (id, name)
VALUES ('8104520b-f0a5-4e42-935e-f5fb178f5152', 'MEETING_ROOM:CREATE');
INSERT INTO public.privileges (id, name)
VALUES ('c8af07ba-e138-4533-b507-5d6c98eb3aa5', 'MEETING_ROOM:UPDATE');

INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', '8104520b-f0a5-4e42-935e-f5fb178f5152');

INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', 'c8af07ba-e138-4533-b507-5d6c98eb3aa5');