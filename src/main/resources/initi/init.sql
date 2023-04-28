DROP TABLE public.users;

CREATE TABLE public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
)

INSERT INTO public.users(id, email, name, password, phone_number)
VALUES (1, "admin@test.com", "Admin", "$2a$10$6D/LYYl58.Qp6AMCaCRCyOVFTtMQbMuK1nULGp7RWC2jeR2eyj2SW", "15654586");  --FG1FGEr@123dfg
