CREATE TABLE IF NOT EXISTS sch_sistema.categoria (
    id bigserial NOT NULL,
    descricao character varying(60) NOT NULL,
    CONSTRAINT categoria_pkey PRIMARY KEY (id)
);


INSERT INTO sch_sistema.categoria(descricao) VALUES ('Farmácia');
INSERT INTO sch_sistema.categoria(descricao) VALUES ('Supermercado');
INSERT INTO sch_sistema.categoria(descricao) VALUES ('Lazer');
INSERT INTO sch_sistema.categoria(descricao) VALUES ('Alimentação');
INSERT INTO sch_sistema.categoria(descricao) VALUES ('Outros');