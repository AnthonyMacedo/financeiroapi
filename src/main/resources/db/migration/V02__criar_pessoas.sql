CREATE TABLE IF NOT EXISTS sch_sistema.pessoa (
    id bigserial NOT NULL,
    nome VARCHAR(120) NOT NULL,
    ativo boolean NOT NULL,
    logradouro VARCHAR(60),
    numero VARCHAR(9),
    complemento VARCHAR(120),
    bairro VARCHAR(60),
    cep VARCHAR(9),
    cidade VARCHAR(60),
    estado VARCHAR(60),
    CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);


