CREATE TABLE IF NOT EXISTS public.products
(
    id          SERIAL PRIMARY KEY,
    title       character varying(250) COLLATE pg_catalog."default",
    price       INTEGER,
    description TEXT,
    picture     character varying(250) COLLATE pg_catalog."default"
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;