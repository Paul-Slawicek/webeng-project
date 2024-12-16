-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id         SERIAL PRIMARY KEY,
    username   character varying(250) COLLATE pg_catalog."default",
    password   character varying(250) COLLATE pg_catalog."default",
    email      character varying(250) COLLATE pg_catalog."default",
    role       character varying(50) COLLATE pg_catalog."default"          DEFAULT 'user',
    firstname  character varying(250) COLLATE pg_catalog."default",
    lastname   character varying(250) COLLATE pg_catalog."default",
    address    character varying(250) COLLATE pg_catalog."default",
    plz        numeric(10, 0),
    city       character varying(100) COLLATE pg_catalog."default",
    salutation character varying(150) COLLATE pg_catalog."default",
    status     character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT 'active',
    country    character varying(150) COLLATE pg_catalog."default"
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;