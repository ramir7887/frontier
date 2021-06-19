CREATE TABLE public.subject_market
(
    id   bigint NOT NULL PRIMARY KEY,
    year_stat int,
    market_size int,
    inverstors character varying,
    mckinsey int,
    frontier int
);

ALTER TABLE public.subject_market
    OWNER TO fortir;


CREATE SEQUENCE public.subject_market_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.subject_market_seq
    OWNER TO fortir;

ALTER TABLE ONLY public.subject_market
ALTER
COLUMN id SET DEFAULT nextval('public.subject_market_seq'::regclass);
