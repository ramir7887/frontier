CREATE TABLE public.hello
(
    id   bigint NOT NULL PRIMARY KEY,
    name character varying
);


ALTER TABLE public.hello
    OWNER TO fortir;


CREATE SEQUENCE public.hello_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.hello_id_seq
    OWNER TO fortir;

ALTER TABLE ONLY public.hello
ALTER
COLUMN id SET DEFAULT nextval('public.hello_id_seq'::regclass);
