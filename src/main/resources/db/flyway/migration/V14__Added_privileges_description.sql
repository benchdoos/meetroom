--Fix of duplicate privileges
DELETE
FROM roles_privileges
WHERE roles_id = '2936d396-9fe0-48a2-b07c-24c0b5f031df'
  AND privilege_id = '8104520b-f0a5-4e42-935e-f5fb178f5152';

DELETE
FROM public.privileges
WHERE id = '8104520b-f0a5-4e42-935e-f5fb178f5152';

DELETE
FROM privileges
WHERE id = 'bea1ba03-b1de-498a-a25c-923f73de24a5';

-----------------------------

alter table if exists privileges
    add column description varchar(1000);

UPDATE privileges
SET description = 'Create events'
WHERE id = '82379bba-018b-43d8-9d34-e0b27858ea93';

UPDATE privileges
SET description = 'Delete events'
WHERE id = 'b39082d5-97ce-4217-a74e-a5f7a3a3ef2b';

UPDATE privileges
SET description = 'Edit events'
WHERE id = 'bd762159-a7f0-4cb2-bc6c-80a65231cade';

UPDATE privileges
SET description = 'Open manage page'
WHERE id = '5788eca6-cd20-40ba-a930-90924d137577';

UPDATE privileges
SET description = 'Create meeting rooms'
WHERE id = '8104520b-f0a5-4e42-935e-f5fb178f5152';

UPDATE privileges
SET description = 'Use meeting rooms'
WHERE id = '080d4e4e-420f-42ce-bfb7-7ecc9de5de5a';

UPDATE privileges
SET description = 'See all list rooms'
WHERE id = 'c4bd8148-6a8e-4f79-88b5-60cff9c22ff0';

UPDATE privileges
SET description = 'Update user roles'
WHERE id = 'dc998e44-b1b0-4d8d-a0d5-6077d66650dd';

UPDATE privileges
SET description = 'Update meeting rooms'
WHERE id = 'bea1ba03-b1de-498a-a25c-923f73de24a5';

UPDATE privileges
SET description = 'Manage users'
WHERE id = '85514763-0e62-4d0a-b15d-482b219e1524';

UPDATE privileges
SET description = 'Update meeting rooms'
WHERE id = 'c8af07ba-e138-4533-b507-5d6c98eb3aa5';

UPDATE privileges
SET description = 'Use events'
WHERE id = '7f34cdf1-0abd-40c9-b370-23f2b178acf6';

UPDATE privileges
SET description = 'Delete user roles'
WHERE id = '049ecc46-c617-43f0-beae-9a1fbf6c1afd';

UPDATE privileges
SET description = 'Create meeting rooms'
WHERE id = '330ecd33-7d36-40ec-936c-59efdde6ae2c';

UPDATE privileges
SET description = 'Create user roles'
WHERE id = '548df413-3ebb-4a8e-ab23-6bcb148ac550';

UPDATE privileges
SET description = 'Default user permissions'
WHERE id = 'c5ed871a-58dc-41f5-856c-98f653a49614';

UPDATE privileges
SET description = 'Manage roles'
WHERE id = 'f4241e77-e473-4d1a-9176-5d8c22eb259f';

UPDATE privileges
SET name = 'MANAGE_ROLES:DELETE'
WHERE id = '049ecc46-c617-43f0-beae-9a1fbf6c1afd';

--Add unique constraint
ALTER TABLE privileges
    ADD CONSTRAINT name_unique_yuyhjkbjvgfr5678 UNIQUE (name);
