CREATE TABLE public.subject_science
(
    id   bigint NOT NULL PRIMARY KEY,
    name character varying,
    branch_id bigint
);


ALTER TABLE public.subject_science
    OWNER TO fortir;


CREATE SEQUENCE public.subject_science_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.subject_science_id_seq
    OWNER TO fortir;

ALTER TABLE ONLY public.subject_science
    ALTER
        COLUMN id SET DEFAULT nextval('public.subject_science_id_seq'::regclass);
