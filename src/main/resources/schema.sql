

CREATE TABLE IF NOT EXISTS public.auth
(
    id SERIAL NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(255) COLLATE pg_catalog."default" DEFAULT 'USER'::character varying,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT auth_pkey PRIMARY KEY (id),
    CONSTRAINT auth_email_key UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.tags
(
    tag_id SERIAL NOT NULL,
    tag_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT tags_pkey PRIMARY KEY (tag_id),
    CONSTRAINT tags_tag_name_key UNIQUE (tag_name)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id SERIAL NOT NULL,
    auth_id integer NOT NULL,
    user_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    profile_text character varying(255) COLLATE pg_catalog."default",
    icon_url character varying(255) COLLATE pg_catalog."default",
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    icon_public_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT fk_users_auth FOREIGN KEY (auth_id)
        REFERENCES public.auth (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.reports
(
    id SERIAL NOT NULL,
    user_id integer NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    content character varying(255) COLLATE pg_catalog."default" NOT NULL,
    learning_date date NOT NULL,
    learning_hours double precision NOT NULL,
    tag_id integer,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT reports_pkey PRIMARY KEY (id),
    CONSTRAINT fk_reports_tag_id FOREIGN KEY (tag_id)
        REFERENCES public.tags (tag_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_reports_user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);
