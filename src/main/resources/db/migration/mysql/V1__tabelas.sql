CREATE TABLE orcamentos (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  cliente varchar(120) DEFAULT NULL,
  criado_em datetime DEFAULT NULL,
  nome varchar(120) DEFAULT NULL,
  responsavel varchar(120) DEFAULT NULL,
  valido_ate datetime DEFAULT NULL,
  uuid varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE unidades_de_software (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  titulo varchar(200) NOT NULL,
  orcamento bigint(20) DEFAULT NULL,
  uuid varchar(36) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX fk_unidades_orcamento (orcamento),
  CONSTRAINT fk_unidades_orcamento FOREIGN KEY (orcamento) REFERENCES orcamentos (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE artefatos (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  custo decimal(8,2) DEFAULT NULL,
  nome varchar(150) NOT NULL,
  unidade bigint(20) DEFAULT NULL,
  uuid varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX fk_artefatos_unidade (unidade),
  CONSTRAINT fk_artefatos_unidade FOREIGN KEY (unidade) REFERENCES unidades_de_software (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE pagamentos (
  desconto_a_vista double NOT NULL,
  entrada double NOT NULL,
  juros_mensais double NOT NULL,
  vezes int(11) NOT NULL,
  orcamento_id bigint(20) NOT NULL,
  uuid varchar(32) DEFAULT NULL,
  PRIMARY KEY (orcamento_id),
  CONSTRAINT fk_pagamentos_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamentos (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
