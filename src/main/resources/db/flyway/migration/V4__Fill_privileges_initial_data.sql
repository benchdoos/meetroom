--Add privileges
INSERT INTO public.privileges (id, name)
VALUES ('080d4e4e-420f-42ce-bfb7-7ecc9de5de5a', 'MEETING_ROOM:USE');
INSERT INTO public.privileges (id, name)
VALUES ('c4bd8148-6a8e-4f79-88b5-60cff9c22ff0', 'LIST_ROOMS:USE');
INSERT INTO public.privileges (id, name)
VALUES ('7f34cdf1-0abd-40c9-b370-23f2b178acf6', 'EVENT:USE');
INSERT INTO public.privileges (id, name)
VALUES ('82379bba-018b-43d8-9d34-e0b27858ea93', 'EVENT:CREATE');
INSERT INTO public.privileges (id, name)
VALUES ('bd762159-a7f0-4cb2-bc6c-80a65231cade', 'EVENT:EDIT');
INSERT INTO public.privileges (id, name)
VALUES ('b39082d5-97ce-4217-a74e-a5f7a3a3ef2b', 'EVENT:DELETE');
INSERT INTO public.privileges (id, name)
VALUES ('330ecd33-7d36-40ec-936c-59efdde6ae2c', 'MEETING_ROOM:CREATE');
INSERT INTO public.privileges (id, name)
VALUES ('bea1ba03-b1de-498a-a25c-923f73de24a5', 'MEETING_ROOM:UPDATE');

--connect roles with privileges
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('f0d09ed9-e672-457c-ac3e-f5ccfc62f228', '080d4e4e-420f-42ce-bfb7-7ecc9de5de5a');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', '080d4e4e-420f-42ce-bfb7-7ecc9de5de5a');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', 'c4bd8148-6a8e-4f79-88b5-60cff9c22ff0');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', '7f34cdf1-0abd-40c9-b370-23f2b178acf6');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', '82379bba-018b-43d8-9d34-e0b27858ea93');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', 'bd762159-a7f0-4cb2-bc6c-80a65231cade');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('2936d396-9fe0-48a2-b07c-24c0b5f031df', 'b39082d5-97ce-4217-a74e-a5f7a3a3ef2b');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('f0d09ed9-e672-457c-ac3e-f5ccfc62f228', '7f34cdf1-0abd-40c9-b370-23f2b178acf6');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('f0d09ed9-e672-457c-ac3e-f5ccfc62f228', '82379bba-018b-43d8-9d34-e0b27858ea93');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('f0d09ed9-e672-457c-ac3e-f5ccfc62f228', 'bd762159-a7f0-4cb2-bc6c-80a65231cade');
INSERT INTO public.roles_privileges (roles_id, privilege_id)
VALUES ('f0d09ed9-e672-457c-ac3e-f5ccfc62f228', 'b39082d5-97ce-4217-a74e-a5f7a3a3ef2b');