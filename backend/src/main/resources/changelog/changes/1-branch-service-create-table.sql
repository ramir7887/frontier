CREATE TABLE public.branch_science
(
    id   bigint NOT NULL PRIMARY KEY,
    name character varying
);


ALTER TABLE public.branch_science
    OWNER TO fortir;


CREATE SEQUENCE public.branch_science_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.branch_science_seq
    OWNER TO fortir;

ALTER TABLE ONLY public.branch_science
    ALTER
        COLUMN id SET DEFAULT nextval('public.branch_science_seq'::regclass);
