CREATE TABLE links (
   id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   codigo_curto  VARCHAR(20) NOT NULL UNIQUE,
   url_original  VARCHAR(2048) NOT NULL,
   criado_em     TIMESTAMPTZ NOT NULL DEFAULT now(),
   expira_em     TIMESTAMPTZ,              -- NULL = nunca expira
   cliques       BIGINT NOT NULL DEFAULT 0,
   ativo         BOOLEAN NOT NULL DEFAULT true
);