-- orcamentos
ALTER TABLE orcamentos
ADD INDEX idx_nome (nome);

ALTER TABLE orcamentos
ADD INDEX idx_responsavel (responsavel);

ALTER TABLE orcamentos
ADD INDEX idx_cliente (cliente);

ALTER TABLE orcamentos
CHANGE uuid uuid VARCHAR(36) NOT NULL;

ALTER TABLE orcamentos
ADD CONSTRAINT un_uuid
UNIQUE (uuid);

-- unidades de software
ALTER TABLE unidades_de_software
CHANGE orcamento orcamento_id bigint(20);

ALTER TABLE unidades_de_software
ADD CONSTRAINT un_orcamento_id_titulo
UNIQUE (orcamento_id, titulo);

ALTER TABLE unidades_de_software
CHANGE uuid uuid VARCHAR(36) NOT NULL;

ALTER TABLE unidades_de_software
ADD CONSTRAINT un_uuid
UNIQUE (uuid);

-- artefatos
ALTER TABLE artefatos
CHANGE unidade unidade_id bigint(20);

ALTER TABLE artefatos
ADD CONSTRAINT un_unidade_id_nome
UNIQUE (unidade_id, nome);

ALTER TABLE artefatos
CHANGE uuid uuid VARCHAR(36) NOT NULL;

ALTER TABLE artefatos
ADD CONSTRAINT un_uuid
UNIQUE (uuid);

-- pagamentos
ALTER TABLE pagamentos
CHANGE uuid uuid VARCHAR(36) NOT NULL;

ALTER TABLE pagamentos
ADD CONSTRAINT un_uuid
UNIQUE (uuid);

