CREATE TABLE public.marker
(
    id   bigint NOT NULL PRIMARY KEY,
    name character varying
);


ALTER TABLE public.marker
    OWNER TO fortir;


CREATE SEQUENCE public.marker_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.marker_id_seq
    OWNER TO fortir;

ALTER TABLE ONLY public.marker
ALTER
COLUMN id SET DEFAULT nextval('public.marker_id_seq'::regclass);
