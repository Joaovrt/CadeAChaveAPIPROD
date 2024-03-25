INSERT INTO public.users (id, login, "password", "role", professor_id)
SELECT '66bdc056-364a-4b39-8578-ffc9d1c7426d', 'hardware', '$2a$10$1TksZGS7fA6Q68JvWtkC6.D2nrtAwplpyCAzlRAUEyAWPP9EersHC', '2', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM public.users WHERE login = 'hardware'
);