INSERT INTO public.users (id, login, "password", "role", professor_id)
SELECT '6381b735-a469-4db2-974b-3851f810edd9', 'admin', '$2a$10$2KCTpnoThkGnE.WNeqpGC.t8ORQOB692ic.ugM.Wx4SVD39cz4.Z2', '0', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM public.users WHERE login = 'admin'
);