CREATE TABLE usuarios (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  login varchar(120) NOT NULL,
  email varchar(255) DEFAULT NULL,
  senha varchar(255) DEFAULT NULL,  
  perfil varchar(25) DEFAULT NULL,
  uuid varchar(36) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT un_email UNIQUE(email),
  CONSTRAINT un_login UNIQUE(login),
  INDEX idx_email_login (email, login)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO usuarios VALUES(1, 'adm', 'adm@the_email.com','$2a$10$83XXq4VqwnESayWg9QVrZOjPv2jUhRBh64ZbOGgElVbFER8ax76Z.
','ROLE_USUARIO','beeb0e90-e4dd-4976-9cc5-9c8331c5c053');

ALTER TABLE orcamentos
ADD COLUMN usuario_id bigint(20) NOT NULL DEFAULT 1;

ALTER TABLE orcamentos
ADD CONSTRAINT fk_orcamentos_usuario
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);