CREATE TABLE sch_sistema.usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(150) NOT NULL
);

CREATE TABLE sch_sistema.permissao (
    id BIGINT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);

CREATE TABLE sch_sistema.usuario_permissao (
    usuario_id BIGINT NOT NULL,
    permissao_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, permissao_id),
    FOREIGN KEY (usuario_id) REFERENCES sch_sistema.usuario(id),
    FOREIGN KEY (permissao_id) REFERENCES sch_sistema.permissao(id)
);

INSERT INTO sch_sistema.usuario (id, nome, email, senha) values (1, 'Administrador', 'admin@admin.com', '$2a$10$6Kgey7cW9Si.mU4DcvMWhOyX5/Ee6i2Kl9DPAYO3DMqCQIBT.XwAG');--admin
INSERT INTO sch_sistema.usuario (id, nome, email, senha) values (2, 'tester', 'teste@teste.com', '$2a$10$4bmZ55/.MsJB8YSixHd6ZOKHbBnGmrUIfxPoC013csfYf.x89KzXi');--tester

INSERT INTO sch_sistema.permissao (id, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO sch_sistema.permissao (id, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');
INSERT INTO sch_sistema.permissao (id, descricao) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO sch_sistema.permissao (id, descricao) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO sch_sistema.permissao (id, descricao) values (5, 'ROLE_PESQUISAR_PESSOA');
INSERT INTO sch_sistema.permissao (id, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO sch_sistema.permissao (id, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO sch_sistema.permissao (id, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 1);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 2);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 3);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 4);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 5);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 6);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 7);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (1, 8);

-- maria
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (2, 2);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (2, 5);
INSERT INTO sch_sistema.usuario_permissao (usuario_id, permissao_id) values (2, 8);
