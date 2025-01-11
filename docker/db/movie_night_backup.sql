--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE aharon WITH PASSWORD 'postgres';
ALTER ROLE aharon WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;

--
-- User Configurations
--






--
-- PostgreSQL database cluster dump complete
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.6 (Homebrew)
-- Dumped by pg_dump version 15.6 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS "movie_night_2.0";
--
-- Name: movie_night_2.0; Type: DATABASE; Schema: -; Owner: aharon
--

CREATE DATABASE "movie_night_2.0" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';


ALTER DATABASE "movie_night_2.0" OWNER TO aharon;

\connect "movie_night_2.0"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: vote_type; Type: TYPE; Schema: public; Owner: aharon
--

CREATE TYPE public.vote_type AS ENUM (
    'UP',
    'DOWN',
    'NONE'
);


ALTER TYPE public.vote_type OWNER TO aharon;

--
-- Name: set_created_at_date(); Type: FUNCTION; Schema: public; Owner: aharon
--

CREATE FUNCTION public.set_created_at_date() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
    NEW.created_at = to_char(COALESCE(NEW.created_at, CURRENT_TIMESTAMP), 'YYYY-MM-DD HH24:MI:SS');
    NEW.updated_at = to_char(CURRENT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS');
    RETURN NEW;
END;$$;


ALTER FUNCTION public.set_created_at_date() OWNER TO aharon;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: movies; Type: TABLE; Schema: public; Owner: aharon
--

CREATE TABLE public.movies (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    external_id character varying(30) NOT NULL,
    api_provider character varying(20) NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    genre character varying(255),
    title character varying(255),
    year integer
);


ALTER TABLE public.movies OWNER TO aharon;

--
-- Name: movies_id_seq; Type: SEQUENCE; Schema: public; Owner: aharon
--

CREATE SEQUENCE public.movies_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movies_id_seq OWNER TO aharon;

--
-- Name: movies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aharon
--

ALTER SEQUENCE public.movies_id_seq OWNED BY public.movies.id;


--
-- Name: room_movies; Type: TABLE; Schema: public; Owner: aharon
--

CREATE TABLE public.room_movies (
    room_id bigint NOT NULL,
    movie_id bigint NOT NULL,
    uuid uuid NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.room_movies OWNER TO aharon;

--
-- Name: room_users; Type: TABLE; Schema: public; Owner: aharon
--

CREATE TABLE public.room_users (
    room_id bigint NOT NULL,
    user_id bigint NOT NULL,
    uuid uuid NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.room_users OWNER TO aharon;

--
-- Name: rooms; Type: TABLE; Schema: public; Owner: aharon
--

CREATE TABLE public.rooms (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    name character varying(100) NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.rooms OWNER TO aharon;

--
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: public; Owner: aharon
--

CREATE SEQUENCE public.rooms_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rooms_id_seq OWNER TO aharon;

--
-- Name: rooms_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aharon
--

ALTER SEQUENCE public.rooms_id_seq OWNED BY public.rooms.id;


--
-- Name: user_votes; Type: TABLE; Schema: public; Owner: aharon
--

CREATE TABLE public.user_votes (
    user_id bigint NOT NULL,
    room_id bigint NOT NULL,
    movie_id bigint NOT NULL,
    uuid uuid NOT NULL,
    vote_type public.vote_type NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.user_votes OWNER TO aharon;

--
-- Name: users; Type: TABLE; Schema: public; Owner: aharon
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    name text,
    nickname character varying(50),
    country_code character varying(2),
    phone_number character varying(20),
    profile_picture text,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO aharon;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: aharon
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO aharon;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aharon
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: movies id; Type: DEFAULT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.movies ALTER COLUMN id SET DEFAULT nextval('public.movies_id_seq'::regclass);


--
-- Name: rooms id; Type: DEFAULT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.rooms ALTER COLUMN id SET DEFAULT nextval('public.rooms_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: movies; Type: TABLE DATA; Schema: public; Owner: aharon
--

COPY public.movies (id, uuid, external_id, api_provider, updated_at, created_at, genre, title, year) FROM stdin;
\.


--
-- Data for Name: room_movies; Type: TABLE DATA; Schema: public; Owner: aharon
--

COPY public.room_movies (room_id, movie_id, uuid, updated_at, created_at) FROM stdin;
\.


--
-- Data for Name: room_users; Type: TABLE DATA; Schema: public; Owner: aharon
--

COPY public.room_users (room_id, user_id, uuid, updated_at, created_at) FROM stdin;
8	10	061dfe46-86c8-40b8-8087-0b7cdc222cd9	2025-01-06 00:23:47.630813	2025-01-06 00:23:47.630813
9	11	fd5baca6-c3d8-4ef6-8c02-f7270c4bf179	2025-01-06 00:23:58.620784	2025-01-06 00:23:58.620784
9	12	6e4841c5-79ac-4279-bad7-1b79c7fb2b8d	2025-01-06 00:24:57.388465	2025-01-06 00:24:57.388465
8	21	158484ba-8955-4e9d-a37d-a81cc04870e8	2025-01-06 20:56:09.835515	2025-01-06 20:56:09.835528
\.


--
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: aharon
--

COPY public.rooms (id, uuid, name, updated_at, created_at) FROM stdin;
1	1c7b80e3-08e2-414d-9991-6fc8b1e12401	Test Room 2	2025-01-05 22:41:07.711668	2025-01-05 22:41:07.711681
2	f8579afa-f3ba-49b4-8838-a5d2da064d2a	Aharon's First Room 3.0	2025-01-05 22:48:59.973504	2025-01-05 22:47:00.663445
3	3cf055b0-81ce-4fe2-9954-6a2072835c03	Test Room 2	2025-01-06 00:12:18.519513	2025-01-06 00:12:18.519525
4	e9ea3131-443f-4a13-86fd-5eb8c9790826	Test Room 2	2025-01-06 00:14:12.028255	2025-01-06 00:14:12.028265
5	e04d6714-be6d-4cd9-becc-2f651a3f70bc	Test Room 2	2025-01-06 00:18:16.902207	2025-01-06 00:18:16.902221
9	7db07b43-ce20-4c6a-bcad-0ef47b66e2bc	Room 2	2025-01-06 00:21:29.859969	2025-01-06 00:21:29.859969
10	eac6efb5-ab99-46bd-9a9d-ef28e21db71e	Test Room 2	2025-01-06 00:25:56.047526	2025-01-06 00:25:56.047538
11	e2cdde28-507f-45e1-a50a-c7cf698bbbe7	Test Room 2	2025-01-06 13:25:29.295741	2025-01-06 13:25:29.295753
12	2dc73868-76cc-42e4-994b-c3a1e2d62cba	Test Room 2	2025-01-06 13:28:12.96467	2025-01-06 13:28:12.96468
13	caf5c3d7-ace2-4739-a360-8023755cf1cb	Test Room 2	2025-01-06 13:49:56.656608	2025-01-06 13:49:56.656618
14	1102ca5a-8cc0-4fd4-a795-20a72c73a3ef	Test Room 2	2025-01-06 19:13:32.24489	2025-01-06 19:13:32.244906
8	dfc98e14-8bb9-4f76-af51-70afaaaaae4e	Aharon Test Room	2025-01-08 17:14:17.466852	2025-01-06 00:21:22.422263
15	1aa91262-a5a8-44f7-91bd-64e5b56fe0d3	Test Room 2	2025-01-08 17:14:17.493405	2025-01-08 17:14:17.493411
\.


--
-- Data for Name: user_votes; Type: TABLE DATA; Schema: public; Owner: aharon
--

COPY public.user_votes (user_id, room_id, movie_id, uuid, vote_type, updated_at, created_at) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: aharon
--

COPY public.users (id, uuid, name, nickname, country_code, phone_number, profile_picture, updated_at, created_at) FROM stdin;
2	86b664ca-f015-4522-a199-256c9a650615	Aharon Maghen	Magoo	IL	0505533685	\N	2025-01-05 22:46:13.241323	2025-01-05 22:42:56.873531
3	d3072341-02c2-41f0-af98-44346fa63a3c	John Doe	Doey	US	3471112233	\N	2025-01-06 00:12:19.445953	2025-01-06 00:12:19.445961
4	d94d27f6-84e3-41ed-8dbc-e2fca2f15797	John Doe	Doey	US	3471112233	\N	2025-01-06 00:14:12.998489	2025-01-06 00:14:12.998494
5	5206df38-f0c6-497e-9710-d25170037014	John Doe	Doey	US	3471112233	\N	2025-01-06 00:18:17.896473	2025-01-06 00:18:17.896478
11	3e649738-d999-4eda-9124-08a0ae2f6ec4	John Doe	Doey	US	3471231234	\N	2025-01-06 00:21:42.939906	2025-01-06 00:21:42.939906
12	9bef0d8f-cd75-4429-9642-d7a2a1140855	Jane Doe	Janey	IL	0501231234	\N	2025-01-06 00:21:42.939906	2025-01-06 00:21:42.939906
13	ec786f98-b8a4-4ed8-89fb-9e1e6d4dc25d	Super Man	SM	US	3475555555	\N	2025-01-06 00:21:42.939906	2025-01-06 00:21:42.939906
14	d8627c9b-589c-4f6f-8a77-66a04837617c	John Doe	Doey	US	3471112233	\N	2025-01-06 00:25:27.246964	2025-01-06 00:25:27.246973
17	0b075be4-6d5e-4ab5-80c3-6ff658410768	John Doe	Doey	US	3471112233	\N	\N	\N
18	c9fc475e-054e-4b9d-81ef-de35096fe32e	John Doe	Doey	US	3471112233	\N	2025-01-06 13:25:28.822867	2025-01-06 13:25:28.822873
19	fa72ef61-a787-4cb4-b32b-0ce07d6ba962	John Doe	Doey	US	3471112233	\N	2025-01-06 13:28:13.810467	2025-01-06 13:28:13.810473
20	c759fe59-d24c-4bb6-bdf8-52f4d08a8181	John Doe	Doey	US	3471112233	\N	2025-01-06 13:49:57.310881	2025-01-06 13:49:57.310889
21	c3d67b51-660b-4c86-967b-6c23ba30f06b	John Doe	Doey	US	3471112233	\N	2025-01-06 19:08:11.340769	2025-01-06 19:08:11.340881
22	53d911e4-dfdc-47f7-858e-58b243f6419d	John Doe	Doey	US	3471112233	\N	2025-01-06 19:09:52.064689	2025-01-06 19:09:52.0647
10	73066cc7-d23b-4330-aeae-db224e02bfa4	Aharon Maghen	Magoo	IL	0505533685	\N	2025-01-08 17:14:17.57309	2025-01-06 00:21:36.710974
23	eb0ada14-40d3-4845-be00-8ee23ef9c114	John Doe	Doey	US	3471112233	\N	2025-01-08 17:14:17.581157	2025-01-08 17:14:17.581165
\.


--
-- Name: movies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: aharon
--

SELECT pg_catalog.setval('public.movies_id_seq', 1, false);


--
-- Name: rooms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: aharon
--

SELECT pg_catalog.setval('public.rooms_id_seq', 15, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: aharon
--

SELECT pg_catalog.setval('public.users_id_seq', 23, true);


--
-- Name: movies movies_pkey; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.movies
    ADD CONSTRAINT movies_pkey PRIMARY KEY (id);


--
-- Name: room_movies room_movies_pkey; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_movies
    ADD CONSTRAINT room_movies_pkey PRIMARY KEY (room_id, movie_id);


--
-- Name: room_users room_users_pkey; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_users
    ADD CONSTRAINT room_users_pkey PRIMARY KEY (room_id, user_id);


--
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- Name: movies unique_movie_uuid; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.movies
    ADD CONSTRAINT unique_movie_uuid UNIQUE (uuid);


--
-- Name: room_movies unique_room_movie_uuid; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_movies
    ADD CONSTRAINT unique_room_movie_uuid UNIQUE (uuid);


--
-- Name: room_users unique_room_user_uuid; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_users
    ADD CONSTRAINT unique_room_user_uuid UNIQUE (uuid);


--
-- Name: rooms unique_room_uuid; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT unique_room_uuid UNIQUE (uuid);


--
-- Name: users unique_user_uuid; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_user_uuid UNIQUE (uuid);


--
-- Name: user_votes unique_user_vote_uuid; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.user_votes
    ADD CONSTRAINT unique_user_vote_uuid UNIQUE (uuid);


--
-- Name: user_votes user_votes_pkey; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.user_votes
    ADD CONSTRAINT user_votes_pkey PRIMARY KEY (user_id, room_id, movie_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: room_movies movie_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_movies
    ADD CONSTRAINT movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(id) NOT VALID;


--
-- Name: room_movies room_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_movies
    ADD CONSTRAINT room_id_fkey FOREIGN KEY (room_id) REFERENCES public.rooms(id) NOT VALID;


--
-- Name: room_users room_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_users
    ADD CONSTRAINT room_id_fkey FOREIGN KEY (room_id) REFERENCES public.rooms(id) NOT VALID;


--
-- Name: user_votes room_movie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.user_votes
    ADD CONSTRAINT room_movie_fkey FOREIGN KEY (room_id, movie_id) REFERENCES public.room_movies(room_id, movie_id) NOT VALID;


--
-- Name: user_votes room_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.user_votes
    ADD CONSTRAINT room_user_fkey FOREIGN KEY (user_id, room_id) REFERENCES public.room_users(user_id, room_id) NOT VALID;


--
-- Name: room_users user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aharon
--

ALTER TABLE ONLY public.room_users
    ADD CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- PostgreSQL database dump complete
--

