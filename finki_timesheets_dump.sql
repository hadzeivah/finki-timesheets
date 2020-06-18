--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

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
-- Name: finkitimesheets; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE finkitimesheets WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE finkitimesheets OWNER TO postgres;

\connect finkitimesheets

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
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: holidays; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.holidays (
    id bigint NOT NULL,
    date timestamp without time zone,
    name character varying(255)
);


ALTER TABLE public.holidays OWNER TO postgres;

--
-- Name: holidays_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.holidays_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.holidays_id_seq OWNER TO postgres;

--
-- Name: holidays_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.holidays_id_seq OWNED BY public.holidays.id;


--
-- Name: items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.items (
    id bigint NOT NULL,
    end_date timestamp without time zone,
    hours integer,
    start_date timestamp without time zone,
    timesheet_id bigint,
    output_id bigint,
    task_id bigint
);


ALTER TABLE public.items OWNER TO postgres;

--
-- Name: items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.items_id_seq OWNER TO postgres;

--
-- Name: items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.items_id_seq OWNED BY public.items.id;


--
-- Name: members; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.members (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    embg character varying(255),
    first_name character varying(255),
    is_deleted boolean,
    last_name character varying(255),
    transaction_account character varying(255)
);


ALTER TABLE public.members OWNER TO postgres;

--
-- Name: members_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.members_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.members_id_seq OWNER TO postgres;

--
-- Name: members_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.members_id_seq OWNED BY public.members.id;


--
-- Name: outputs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.outputs (
    id bigint NOT NULL,
    description character varying(255),
    work_package_id bigint
);


ALTER TABLE public.outputs OWNER TO postgres;

--
-- Name: outputs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.outputs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.outputs_id_seq OWNER TO postgres;

--
-- Name: outputs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.outputs_id_seq OWNED BY public.outputs.id;


--
-- Name: positions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.positions (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.positions OWNER TO postgres;

--
-- Name: positions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.positions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.positions_id_seq OWNER TO postgres;

--
-- Name: positions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.positions_id_seq OWNED BY public.positions.id;


--
-- Name: project_position; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project_position (
    id bigint NOT NULL,
    salary integer NOT NULL,
    position_id bigint,
    project_id bigint
);


ALTER TABLE public.project_position OWNER TO postgres;

--
-- Name: project_position_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.project_position_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.project_position_id_seq OWNER TO postgres;

--
-- Name: project_position_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.project_position_id_seq OWNED BY public.project_position.id;


--
-- Name: projects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.projects (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    end_date timestamp without time zone,
    estimated_budget bigint,
    is_deleted boolean,
    name character varying(255),
    project_number character varying(255),
    start_date timestamp without time zone,
    project_manager_id bigint,
    university_id bigint,
    is_approved boolean,
    work_package_id bigint
);


ALTER TABLE public.projects OWNER TO postgres;

--
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.projects_id_seq OWNER TO postgres;

--
-- Name: projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.projects_id_seq OWNED BY public.projects.id;


--
-- Name: timesheets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.timesheets (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    is_deleted boolean,
    member_id bigint,
    project_id bigint,
    project_position bigint
);


ALTER TABLE public.timesheets OWNER TO postgres;

--
-- Name: report_total_by_intellectual_output; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.report_total_by_intellectual_output AS
 SELECT row_number() OVER () AS id,
    t.project_id,
    p.name AS project_name,
    o.description AS intellectual_output,
    (sum((items.hours * pp.salary)) / 8) AS total
   FROM ((((public.items
     JOIN public.timesheets t ON ((items.timesheet_id = t.id)))
     JOIN public.projects p ON ((t.project_id = p.id)))
     JOIN public.project_position pp ON ((pp.id = t.project_position)))
     JOIN public.outputs o ON ((o.id = items.output_id)))
  GROUP BY t.project_id, p.name, o.description, pp.salary;


ALTER TABLE public.report_total_by_intellectual_output OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: tasks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tasks (
    id bigint NOT NULL,
    description character varying(255),
    work_package_id bigint
);


ALTER TABLE public.tasks OWNER TO postgres;

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tasks_id_seq OWNER TO postgres;

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tasks_id_seq OWNED BY public.tasks.id;


--
-- Name: timesheets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.timesheets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.timesheets_id_seq OWNER TO postgres;

--
-- Name: timesheets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.timesheets_id_seq OWNED BY public.timesheets.id;


--
-- Name: university; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.university (
    id bigint NOT NULL,
    dean character varying(255),
    name character varying(255)
);


ALTER TABLE public.university OWNER TO postgres;

--
-- Name: university_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.university_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.university_id_seq OWNER TO postgres;

--
-- Name: university_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.university_id_seq OWNED BY public.university.id;


--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: work_packages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.work_packages (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.work_packages OWNER TO postgres;

--
-- Name: work_packages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.work_packages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.work_packages_id_seq OWNER TO postgres;

--
-- Name: work_packages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.work_packages_id_seq OWNED BY public.work_packages.id;


--
-- Name: holidays id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holidays ALTER COLUMN id SET DEFAULT nextval('public.holidays_id_seq'::regclass);


--
-- Name: items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items ALTER COLUMN id SET DEFAULT nextval('public.items_id_seq'::regclass);


--
-- Name: members id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.members ALTER COLUMN id SET DEFAULT nextval('public.members_id_seq'::regclass);


--
-- Name: outputs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outputs ALTER COLUMN id SET DEFAULT nextval('public.outputs_id_seq'::regclass);


--
-- Name: positions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positions ALTER COLUMN id SET DEFAULT nextval('public.positions_id_seq'::regclass);


--
-- Name: project_position id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_position ALTER COLUMN id SET DEFAULT nextval('public.project_position_id_seq'::regclass);


--
-- Name: projects id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects ALTER COLUMN id SET DEFAULT nextval('public.projects_id_seq'::regclass);


--
-- Name: tasks id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks ALTER COLUMN id SET DEFAULT nextval('public.tasks_id_seq'::regclass);


--
-- Name: timesheets id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.timesheets ALTER COLUMN id SET DEFAULT nextval('public.timesheets_id_seq'::regclass);


--
-- Name: university id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.university ALTER COLUMN id SET DEFAULT nextval('public.university_id_seq'::regclass);


--
-- Name: work_packages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.work_packages ALTER COLUMN id SET DEFAULT nextval('public.work_packages_id_seq'::regclass);


--
-- Data for Name: holidays; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.holidays (id, date, name) FROM stdin;
1	2019-01-01 18:30:00	\N
2	2019-01-07 18:30:00	\N
3	2019-01-06 18:30:00	\N
4	2019-01-19 18:30:00	\N
5	2019-04-26 18:30:00	\N
6	2019-04-29 18:30:00	\N
7	2019-05-01 18:30:00	\N
8	2019-05-24 18:30:00	\N
9	2019-06-04 18:30:00	\N
10	2019-06-14 18:30:00	\N
11	2019-07-02 18:30:00	\N
12	2019-07-28 18:30:00	\N
13	2019-09-08 18:30:00	\N
14	2019-10-11 18:30:00	\N
15	2019-11-23 18:30:00	\N
16	2019-12-08 18:30:00	\N
\.


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.items (id, end_date, hours, start_date, timesheet_id, output_id, task_id) FROM stdin;
81	2020-06-09 00:00:00	8	2020-05-31 00:00:00	7	2	6
82	2020-06-16 00:00:00	8	2020-05-31 00:00:00	6	3	7
84	2020-06-17 00:00:00	8	2020-05-31 00:00:00	6	3	7
\.


--
-- Data for Name: members; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.members (id, created_at, updated_at, embg, first_name, is_deleted, last_name, transaction_account) FROM stdin;
3	2019-10-20 20:32:35.603172	2019-10-20 20:32:35.603172	0106996435036	Hristina	f	Hadjieva	1000000478987
4	2019-10-20 20:33:01.014477	2019-10-20 21:58:04.986434	0578945898465	Maja	t	Hadjieva	1084529865294
5	2019-10-20 22:01:30.832193	2019-10-20 22:01:30.832193	4534865846845	Maja	f	Hadjieva	1000000000798
6	2019-11-14 02:37:51.786826	2019-11-14 02:37:51.786826	9865986139465	Jasmina	f	Hadjieva	465384615846153
7	2019-11-14 02:38:21.115895	2019-11-14 02:38:21.115895	984132946137	Ivana	f	Ivanovska	986134653486
\.


--
-- Data for Name: outputs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.outputs (id, description, work_package_id) FROM stdin;
1	1.1 Action plan on developing Interdisciplinary program.	1
2	1.2 Analysed international master programs.	1
3	1.3 Competence building in syntactic, morphological and semantic knowledge of English language	1
4	1.4 Equipment for administrative and teaching issues.	1
\.


--
-- Data for Name: positions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.positions (id, description, name) FROM stdin;
1	MANAGER	MANAGER
2	TEACHER	TEACHER
3	TRAINER	TRAINER
4	RESEARCHER	RESEARCHER
5	TECHNICIAN	TECHNICIAN
6	ADMINISTRATIVE	ADMINISTRATIVE
\.


--
-- Data for Name: project_position; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.project_position (id, salary, position_id, project_id) FROM stdin;
133	80	1	17
134	80	2	17
135	80	3	17
136	80	4	17
137	80	5	17
138	80	6	17
139	80	1	18
140	80	2	18
141	80	3	18
142	80	4	18
143	80	5	18
144	80	6	18
122	50	2	15
123	50	3	15
124	50	4	15
125	50	5	15
126	50	6	15
121	70	1	15
127	75	1	16
128	75	2	16
129	75	3	16
130	75	4	16
131	75	5	16
132	75	6	16
\.


--
-- Data for Name: projects; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.projects (id, created_at, updated_at, end_date, estimated_budget, is_deleted, name, project_number, start_date, project_manager_id, university_id, is_approved, work_package_id) FROM stdin;
15	2019-10-20 21:35:16.682291	2019-10-31 01:58:05.615269	2019-12-30 23:00:00	5000	f	Gameplay for Inspiring Digital Adoption - GIRDA	2016-1-UK01-KA204-024509	2019-09-30 22:00:00	2	1	t	1
16	2019-10-20 21:36:49.663438	2019-10-31 02:32:46.265572	2019-12-30 23:00:00	5000	f	Test project	2016-1-UK01-KA204-080000	2019-10-19 22:00:00	2	1	t	1
17	2019-10-31 02:43:12.857637	2019-10-31 02:50:45.196447	2019-12-30 23:00:00	5000	f	TEMPUS	2016-1-UK01-KA204-024508	2019-09-30 22:00:00	1	1	t	1
18	2019-10-31 03:01:27.066714	2019-10-31 03:01:27.066714	2019-10-29 23:00:00	5000	f	TEST	84574654	2019-09-30 22:00:00	1	1	f	1
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	ADMIN
2	USER
\.


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tasks (id, description, work_package_id) FROM stdin;
7	1.3 Analysing the content of international educational master programs in Computer Linguistics	1
10	1.1 Mobility to EU university for the kick off meeting	1
6	1.2 Developing an action plan	1
\.


--
-- Data for Name: timesheets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.timesheets (id, created_at, updated_at, is_deleted, member_id, project_id, project_position) FROM stdin;
6	2019-10-20 21:36:05.848969	2019-10-20 21:36:05.848969	f	3	15	121
7	2019-10-20 22:28:54.316917	2019-10-20 22:28:54.316917	f	5	15	123
8	2019-10-25 01:15:53.891963	2019-10-25 01:15:53.891963	f	3	16	128
\.


--
-- Data for Name: university; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.university (id, dean, name) FROM stdin;
1	Вон Проф. д-р Иван Чорбев	Факултет за информатички науки и компјутерско инженерство - ФИНКИ
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, role_id) FROM stdin;
1	2
2	1
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, password, username) FROM stdin;
1	Hristina	Hadjieva	$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2	hadjieva.hristina
2	Ivan	Chorbev	$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2	chorbev.ivan
\.


--
-- Data for Name: work_packages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.work_packages (id, name) FROM stdin;
1	WP1
2	WP2
3	WP3
5	WP5
6	WP6
4	WP4
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Name: holidays_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.holidays_id_seq', 16, true);


--
-- Name: items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.items_id_seq', 92, true);


--
-- Name: members_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.members_id_seq', 7, true);


--
-- Name: outputs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.outputs_id_seq', 36, true);


--
-- Name: positions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.positions_id_seq', 6, true);


--
-- Name: project_position_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.project_position_id_seq', 144, true);


--
-- Name: projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.projects_id_seq', 18, true);


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tasks_id_seq', 47, true);


--
-- Name: timesheets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.timesheets_id_seq', 8, true);


--
-- Name: university_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.university_id_seq', 1, false);


--
-- Name: work_packages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.work_packages_id_seq', 7, true);


--
-- Name: holidays holidays_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holidays
    ADD CONSTRAINT holidays_pkey PRIMARY KEY (id);


--
-- Name: items items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id);


--
-- Name: members members_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.members
    ADD CONSTRAINT members_pkey PRIMARY KEY (id);


--
-- Name: outputs outputs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outputs
    ADD CONSTRAINT outputs_pkey PRIMARY KEY (id);


--
-- Name: positions positions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positions
    ADD CONSTRAINT positions_pkey PRIMARY KEY (id);


--
-- Name: project_position project_position_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_position
    ADD CONSTRAINT project_position_pkey PRIMARY KEY (id);


--
-- Name: projects projects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- Name: roles role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- Name: timesheets timesheets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.timesheets
    ADD CONSTRAINT timesheets_pkey PRIMARY KEY (id);


--
-- Name: members uk_alqtiilqewoy03xfosa3iblxl; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.members
    ADD CONSTRAINT uk_alqtiilqewoy03xfosa3iblxl UNIQUE (embg);


--
-- Name: university university_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.university
    ADD CONSTRAINT university_pkey PRIMARY KEY (id);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: work_packages work_packages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.work_packages
    ADD CONSTRAINT work_packages_pkey PRIMARY KEY (id);


--
-- Name: timesheets fk2x6dv7lkigvbeehtv0u9b2dxi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.timesheets
    ADD CONSTRAINT fk2x6dv7lkigvbeehtv0u9b2dxi FOREIGN KEY (member_id) REFERENCES public.members(id);


--
-- Name: timesheets fk5v0u4owb9l6pp1c47fm1y77vb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.timesheets
    ADD CONSTRAINT fk5v0u4owb9l6pp1c47fm1y77vb FOREIGN KEY (project_id) REFERENCES public.projects(id);


--
-- Name: items fk6epil4k1e60nm8ndxmau4me7s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT fk6epil4k1e60nm8ndxmau4me7s FOREIGN KEY (task_id) REFERENCES public.tasks(id);


--
-- Name: project_position fk8m3uf1ac1rdnsalxu8ynn2c8d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_position
    ADD CONSTRAINT fk8m3uf1ac1rdnsalxu8ynn2c8d FOREIGN KEY (project_id) REFERENCES public.projects(id);


--
-- Name: items fkdcdoxa1twe5ko8k1xdyj6wfhi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT fkdcdoxa1twe5ko8k1xdyj6wfhi FOREIGN KEY (timesheet_id) REFERENCES public.timesheets(id);


--
-- Name: timesheets fkfdfcucma3uym56mm02n7hc6wq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.timesheets
    ADD CONSTRAINT fkfdfcucma3uym56mm02n7hc6wq FOREIGN KEY (project_position) REFERENCES public.project_position(id);


--
-- Name: projects fkgdyu73mg454kk9iys9567qft7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT fkgdyu73mg454kk9iys9567qft7 FOREIGN KEY (project_manager_id) REFERENCES public.users(id);


--
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: tasks fkhoi7phwxdsi7tty3ef6qhsoae; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT fkhoi7phwxdsi7tty3ef6qhsoae FOREIGN KEY (work_package_id) REFERENCES public.work_packages(id);


--
-- Name: project_position fklb1cqn87hx3vay5qswvxlalrg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_position
    ADD CONSTRAINT fklb1cqn87hx3vay5qswvxlalrg FOREIGN KEY (position_id) REFERENCES public.positions(id);


--
-- Name: outputs fklq4jw8cme3we5q3lflou76khu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outputs
    ADD CONSTRAINT fklq4jw8cme3we5q3lflou76khu FOREIGN KEY (work_package_id) REFERENCES public.work_packages(id);


--
-- Name: items fkpcjrx2wo6vr907w5que6y6y7q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT fkpcjrx2wo6vr907w5que6y6y7q FOREIGN KEY (output_id) REFERENCES public.outputs(id);


--
-- Name: projects fkr9ik6s1cos7s6094usa9p3y3s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT fkr9ik6s1cos7s6094usa9p3y3s FOREIGN KEY (work_package_id) REFERENCES public.work_packages(id);


--
-- Name: projects fktcmcdc8mry51nbqdq538k9cbq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT fktcmcdc8mry51nbqdq538k9cbq FOREIGN KEY (university_id) REFERENCES public.university(id);


--
-- PostgreSQL database dump complete
--

