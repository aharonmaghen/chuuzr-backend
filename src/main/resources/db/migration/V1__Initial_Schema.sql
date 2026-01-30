BEGIN;

CREATE TYPE public.vote_type AS ENUM ('UP', 'DOWN', 'NONE');

CREATE TABLE IF NOT EXISTS public.option_types
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    uuid uuid NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp(6) without time zone,
    CONSTRAINT option_types_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.options
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    uuid uuid NOT NULL,
    option_type_id bigint NOT NULL,
    api_provider character varying(20) COLLATE pg_catalog."default",
    external_id character varying(36) COLLATE pg_catalog."default",
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    image_url text COLLATE pg_catalog."default",
    metadata jsonb,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT options_pkey PRIMARY KEY (id),
    CONSTRAINT unique_movie_uuid UNIQUE (uuid)
);

CREATE TABLE IF NOT EXISTS public.room_options
(
    room_id bigint NOT NULL,
    option_id bigint NOT NULL,
    score integer NOT NULL DEFAULT 0,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT room_options_pkey PRIMARY KEY (room_id, option_id)
);

CREATE TABLE IF NOT EXISTS public.room_users
(
    room_id bigint NOT NULL,
    user_id bigint NOT NULL,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT room_users_pkey PRIMARY KEY (room_id, user_id)
);

CREATE TABLE IF NOT EXISTS public.rooms
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    uuid uuid NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT rooms_pkey PRIMARY KEY (id),
    CONSTRAINT unique_room_uuid UNIQUE (uuid)
);

CREATE TABLE IF NOT EXISTS public.user_votes
(
    user_id bigint NOT NULL,
    room_id bigint NOT NULL,
    option_id bigint NOT NULL,
    vote_type vote_type NOT NULL,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT user_votes_pkey PRIMARY KEY (user_id, room_id, option_id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    uuid uuid NOT NULL,
    name text COLLATE pg_catalog."default",
    nickname character varying(50) COLLATE pg_catalog."default",
    country_code character varying(2) COLLATE pg_catalog."default",
    phone_number character varying(20) COLLATE pg_catalog."default",
    profile_picture text COLLATE pg_catalog."default",
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    otp_code character varying(6) COLLATE pg_catalog."default",
    otp_expiration_time timestamp(6) without time zone,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT unique_user_uuid UNIQUE (uuid)
);

ALTER TABLE IF EXISTS public.options
    ADD CONSTRAINT option_type_id_fkey FOREIGN KEY (option_type_id)
    REFERENCES public.option_types (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.room_options
    ADD CONSTRAINT option_id_fkey FOREIGN KEY (option_id)
    REFERENCES public.options (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.room_options
    ADD CONSTRAINT room_id_fkey FOREIGN KEY (room_id)
    REFERENCES public.rooms (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.room_users
    ADD CONSTRAINT room_id_fkey FOREIGN KEY (room_id)
    REFERENCES public.rooms (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.room_users
    ADD CONSTRAINT user_id_fkey FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.user_votes
    ADD CONSTRAINT room_option_fkey FOREIGN KEY (room_id, option_id)
    REFERENCES public.room_options (room_id, option_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.user_votes
    ADD CONSTRAINT room_user_fkey FOREIGN KEY (room_id, user_id)
    REFERENCES public.room_users (room_id, user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

END;