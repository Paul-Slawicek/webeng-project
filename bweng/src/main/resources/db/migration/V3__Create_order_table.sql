CREATE TABLE IF NOT EXISTS public.orders
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    product_id INTEGER,
    quantity INTEGER,
    total_price DOUBLE PRECISION
)

    TABLESPACE pg_default;
