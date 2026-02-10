BEGIN;

CREATE TABLE IF NOT EXISTS public.search_providers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    option_type_id bigint NOT NULL,
    provider_key character varying(100) NOT NULL,
    enabled boolean NOT NULL,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT search_providers_pkey PRIMARY KEY (id),
    CONSTRAINT unique_option_type_id_provider_key UNIQUE (option_type_id, provider_key),
    CONSTRAINT option_type_id_fkey FOREIGN KEY (option_type_id)
    REFERENCES public.option_types (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

INSERT INTO public.search_providers (option_type_id, provider_key, enabled)
VALUES (1, 'TMDB', TRUE);

END;
