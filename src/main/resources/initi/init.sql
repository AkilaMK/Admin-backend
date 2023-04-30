-- Table: public.role table
DROP SEQUENCE seq_role_id;
CREATE SEQUENCE seq_role_id START 2;

DROP TABLE public.role;
CREATE TABLE public.role
(
    id bigint NOT NULL DEFAULT nextval('seq_role_id'),
    name character varying(255) NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT uq_role_name UNIQUE (name)
);

TABLESPACE pg_default;

ALTER TABLE public.role OWNER to "adminUser";

INSERT INTO public.role(id, name) VALUES (1, 'ADMIN');
INSERT INTO public.role(id, name) VALUES (nextval('seq_role_id'), 'USER');

-- Table: public.users table
DROP SEQUENCE seq_users_id;
CREATE SEQUENCE seq_users_id START 2;

DROP TABLE public.users;
CREATE TABLE public.users
(
    id bigint NOT NULL DEFAULT nextval('seq_users_id'),
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
)

TABLESPACE pg_default;

ALTER TABLE public.users OWNER to "adminUser";

INSERT INTO public.user(id, email, name, password, phone_number)
VALUES (1, "admin@test.com", "Admin", "$2a$10$wfvXzOccyESaiFl1KdiqqOtE5G4LwpKP8dkSco2fNSjkxZkg9l0.K", "15654586");  --FG1FGEr@123dfg

-- Table: public.users_roles table

DROP TABLE public.users_roles;
CREATE TABLE public.users_roles
(
    user_id bigint NOT NULL,
    roles_id bigint NOT NULL,
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, roles_id),
    CONSTRAINT fk_role_id FOREIGN KEY (roles_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_users_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.users_roles OWNER to "adminUser";
