CREATE TABLE public.source
(
    id   bigint NOT NULL PRIMARY KEY,
    name character varying
);


ALTER TABLE public.source
    OWNER TO fortir;


CREATE SEQUENCE public.source_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.source_id_seq
    OWNER TO fortir;

ALTER TABLE ONLY public.source
ALTER
COLUMN id SET DEFAULT nextval('public.source_id_seq'::regclass);

INSERT INTO source(id, name) VALUES (1, 'McKinsey');
INSERT INTO source(id, name) VALUES (2, 'Gartner');
