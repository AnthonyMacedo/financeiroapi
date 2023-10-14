CREATE TABLE IF NOT EXISTS lancamento (
	id BIGSERIAL NOT NULL,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(120),
	tipo_lancamento VARCHAR(20) NOT NULL,
	categoria_id BIGINT NOT NULL,
	pessoa_id BIGINT NOT NULL,
    CONSTRAINT lancamento_pkey PRIMARY KEY (id),
	FOREIGN KEY (categoria_id) REFERENCES categoria(id),
	FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);