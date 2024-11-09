-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id SERIAL PRIMARY KEY, -- Automatically handles the sequence
    email character varying(250) COLLATE pg_catalog."default",
    password character varying(250) COLLATE pg_catalog."default",
    role character varying(50) COLLATE pg_catalog."default",
    firstname character varying(250) COLLATE pg_catalog."default",
    lastname character varying(250) COLLATE pg_catalog."default",
    adress character varying(250) COLLATE pg_catalog."default",
    plz numeric(10,0),
    city character varying(100) COLLATE pg_catalog."default",
    saluation character varying(150) COLLATE pg_catalog."default",
    username character varying(250) COLLATE pg_catalog."default"
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;
