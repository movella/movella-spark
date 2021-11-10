CREATE TYPE acesso AS ENUM ('normal', 'verificado', 'admin');
CREATE TYPE tipodenuncia AS ENUM ('informacoes inconsistentes', 'anuncio indevido', 'outro');
CREATE TYPE tipopagamento AS ENUM ('cpf');

-- -----------------------------------------------------
-- Table tbl_usuario
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_usuario;

CREATE TABLE IF NOT EXISTS tbl_usuario (
  id SERIAL,
  celular VARCHAR(11) NULL DEFAULT NULL,
  email VARCHAR(255) NOT NULL,
  foto VARCHAR(255) NOT NULL DEFAULT 'default.png',
  senha TEXT NOT NULL,
  nome VARCHAR(20) NOT NULL,
  cpf CHAR(11),
  acesso acesso NOT NULL DEFAULT 'normal',
  cep CHAR(8) NULL DEFAULT NULL,
  logradouro VARCHAR(255) NULL DEFAULT NULL,
  complemento VARCHAR(255) NULL DEFAULT NULL,
  bairro VARCHAR(255) NULL DEFAULT NULL,
  cidade VARCHAR(255) NULL DEFAULT NULL,
  uf CHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT usuario_id_UNIQUE UNIQUE (id),
  CONSTRAINT usuario_email_UNIQUE UNIQUE (email),
  CONSTRAINT usuario_nome_UNIQUE UNIQUE (nome),
  CONSTRAINT usuario_cpf_UNIQUE UNIQUE (cpf));

CREATE INDEX tbl_usuario_id_index ON tbl_usuario (id);


-- -----------------------------------------------------
-- Table tbl_categoria
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_categoria;

CREATE TABLE IF NOT EXISTS tbl_categoria (
  id SERIAL,
  nome VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT categoria_id_UNIQUE UNIQUE (id));

CREATE INDEX tbl_categoria_id_index ON tbl_categoria (id);


-- -----------------------------------------------------
-- Table tbl_movel
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_movel;

CREATE TABLE IF NOT EXISTS tbl_movel (
  id SERIAL,
  categoriaId INT  NOT NULL,
  usuarioId INT  NOT NULL,
  descricao TEXT NULL DEFAULT NULL,
  imagem VARCHAR(255) NOT NULL DEFAULT 'default.png',
  nome VARCHAR(40) NOT NULL,
  valorMes real  NOT NULL,
  disponivel boolean NOT NULL,
  altura real  NOT NULL,
  largura real  NOT NULL,
  espessura real  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT movel_id_UNIQUE UNIQUE (id),
  CONSTRAINT fk_usuario
    FOREIGN KEY (usuarioId)
    REFERENCES tbl_usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_categoria
    FOREIGN KEY (categoriaId)
    REFERENCES tbl_categoria (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX tbl_movel_id_index ON tbl_movel (id);
CREATE INDEX tbl_movel_categoriaId_index ON tbl_movel (categoriaId);
CREATE INDEX tbl_movel_usuarioId_index ON tbl_movel (usuarioId);


-- -----------------------------------------------------
-- Table tbl_aluguel
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_aluguel;

CREATE TABLE IF NOT EXISTS tbl_aluguel (
  id SERIAL,
  movelId INT  NOT NULL,
  usuarioId INT  NOT NULL,
  dataInicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dataFim TIMESTAMP NOT NULL,
  valorFrete real  NOT NULL,
  descricao TEXT NULL DEFAULT NULL,
  imagem VARCHAR(255) NULL DEFAULT 'default.png',
  nome VARCHAR(40) NULL DEFAULT NULL,
  valorMes real  NULL DEFAULT NULL,
  chavePagamento VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT aluguel_id_UNIQUE UNIQUE (id),
  CONSTRAINT fk_aluguel_movel
    FOREIGN KEY (movelId)
    REFERENCES tbl_movel (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_aluguel_usuario
    FOREIGN KEY (usuarioId)
    REFERENCES tbl_usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX tbl_aluguel_id_index ON tbl_aluguel (id);
CREATE INDEX tbl_aluguel_usuarioId_index ON tbl_aluguel (usuarioId);
CREATE INDEX tbl_aluguel_movelId_index ON tbl_aluguel (movelId);

-- -----------------------------------------------------
-- Table tbl_avaliacao
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_avaliacao;
CREATE TABLE IF NOT EXISTS tbl_avaliacao (
  id SERIAL,
  avaliacao INT NOT NULL,
  avaliadorId INT  NOT NULL,
  avaliadoId INT  NOT NULL,
  data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT avaliacao_id_UNIQUE UNIQUE (id),
  CONSTRAINT fk_avaliacao_avaliador
    FOREIGN KEY (avaliadorId)
    REFERENCES tbl_usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_avaliacao_avaliado
    FOREIGN KEY (avaliadoId)
    REFERENCES tbl_usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX tbl_avaliacao_id_index ON tbl_avaliacao (id);
CREATE INDEX tbl_avaliacao_avaliadorId_index ON tbl_avaliacao (avaliadorId);
CREATE INDEX tbl_avaliacao_avaliadoId_index ON tbl_avaliacao (avaliadoId);


-- -----------------------------------------------------
-- Table tbl_contato
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_contato;

CREATE TABLE IF NOT EXISTS tbl_contato (
  id SERIAL,
  nome VARCHAR(64) NOT NULL,
  email VARCHAR(255) NOT NULL,
  assunto VARCHAR(64) NOT NULL,
  mensagem TEXT NOT NULL,
  data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT contato_id_UNIQUE UNIQUE (id));

CREATE INDEX tbl_contato_id_index ON tbl_contato (id);


-- -----------------------------------------------------
-- Table tbl_denuncia
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_denuncia;

CREATE TABLE IF NOT EXISTS tbl_denuncia (
  id SERIAL,
  tipo tipodenuncia NOT NULL,
  descricao TEXT NULL DEFAULT NULL,
  delatorId INT  NOT NULL,
  usuarioId INT  NULL DEFAULT NULL,
  movelId INT  NULL DEFAULT NULL,
  data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT denuncia_id_UNIQUE UNIQUE (id),
  CONSTRAINT fk_denuncia_delator
    FOREIGN KEY (delatorId)
    REFERENCES tbl_usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_denuncia_usuario
    FOREIGN KEY (usuarioId)
    REFERENCES tbl_usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_denuncia_movel
    FOREIGN KEY (movelId)
    REFERENCES tbl_movel (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX tbl_denuncia_id_index ON tbl_denuncia (id);
CREATE INDEX tbl_denuncia_delatorId_index ON tbl_denuncia (delatorId);
CREATE INDEX tbl_denuncia_usuarioId_index ON tbl_denuncia (usuarioId);
CREATE INDEX tbl_denuncia_movelId_index ON tbl_denuncia (movelId);


-- -----------------------------------------------------
-- Table tbl_foto
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_foto;

CREATE TABLE IF NOT EXISTS tbl_foto (
  id SERIAL,
  caminho VARCHAR(255) NOT NULL,
  movelId INT  NULL DEFAULT NULL,
  data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT foto_id_UNIQUE UNIQUE (id),
  CONSTRAINT fk_movel
    FOREIGN KEY (movelId)
    REFERENCES tbl_movel (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX tbl_foto_id_index ON tbl_foto (id);
CREATE INDEX tbl_foto_movelId_index ON tbl_foto (movelId);


-- -----------------------------------------------------
-- Table tbl_pagamento
-- -----------------------------------------------------
DROP TABLE IF EXISTS tbl_pagamento;

CREATE TABLE IF NOT EXISTS tbl_pagamento (
  id SERIAL,
  tipo tipopagamento NOT NULL,
  chave VARCHAR(255) NOT NULL,
  usuarioId INT  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT pagamento_id_UNIQUE UNIQUE (id),
  CONSTRAINT fk_pagamento_usuario
    FOREIGN KEY (usuarioId)
    REFERENCES tbl_usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX tbl_pagamento_id_index ON tbl_pagamento (id);
CREATE INDEX tbl_pagamento_usuarioId_index ON tbl_pagamento (usuarioId);

-- -----------------------------------------------------
-- Placeholder table for view view_aluguel
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS view_aluguel (id INT, locador INT, locatario INT, nome INT, categoria INT, valorMes INT, valorFrete INT, dataInicio INT, dataFim INT, descricao INT, imagem INT, email INT, celular INT);

-- -----------------------------------------------------
-- Placeholder table for view view_denuncia_movel
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS view_denuncia_movel (tipo INT, denunciaDescricao INT, data INT, delatorNome INT, delatorEmail INT, delatorAcesso INT, nome INT, descricao INT, valorMes INT, id INT);

-- -----------------------------------------------------
-- Placeholder table for view view_denuncia_usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS view_denuncia_usuario (tipo INT, denunciaDescricao INT, data INT, delatorNome INT, delatorEmail INT, delatorAcesso INT, email INT, nome INT, acesso INT);

-- -----------------------------------------------------
-- Placeholder table for view view_movel
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS view_movel (usuarioNome INT, categoria INT, nome INT, descricao INT, imagem INT, valorMes INT, disponivel INT, id INT, cidade INT);

-- -----------------------------------------------------
-- Placeholder table for view view_usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS view_usuario (nome INT, email INT, celular INT, foto INT, cep INT, logradouro INT, complemento INT, bairro INT, cidade INT, uf INT, acesso INT, avaliacao INT, avaliacoes INT);

-- -----------------------------------------------------
-- View view_aluguel
-- -----------------------------------------------------
DROP TABLE IF EXISTS view_aluguel;
DROP VIEW IF EXISTS view_aluguel ;
CREATE VIEW view_aluguel AS
    SELECT 
        a.id AS id,
        u2.nome AS locador,
        u.nome AS locatario,
        a.nome AS nome,
        c.nome AS categoria,
        a.valorMes AS valorMes,
        a.valorFrete AS valorFrete,
        a.dataInicio AS dataInicio,
        a.dataFim AS dataFim,
        a.descricao AS descricao,
        a.imagem AS imagem,
        u.email AS email,
        u.celular AS celular
    FROM
        ((((tbl_aluguel a
        JOIN tbl_movel m ON ((a.movelId = m.id)))
        JOIN tbl_categoria c ON ((m.categoriaId = c.id)))
        JOIN tbl_usuario u ON ((a.usuarioId = u.id)))
        JOIN tbl_usuario u2 ON ((m.usuarioId = u2.id)));

-- -----------------------------------------------------
-- View view_denuncia_movel
-- -----------------------------------------------------
DROP TABLE IF EXISTS view_denuncia_movel;
DROP VIEW IF EXISTS view_denuncia_movel ;
CREATE VIEW view_denuncia_movel AS
    SELECT 
        d.tipo AS tipo,
        d.descricao AS denunciaDescricao,
        d.data AS data,
        delator.nome AS delatorNome,
        delator.email AS delatorEmail,
        delator.acesso AS delatorAcesso,
        m.nome AS nome,
        m.descricao AS descricao,
        m.valorMes AS valorMes,
        m.id AS id
    FROM
        ((tbl_denuncia d
        JOIN tbl_usuario delator ON ((d.delatorId = delator.id)))
        JOIN tbl_movel m ON ((d.movelId = m.id)));

-- -----------------------------------------------------
-- View view_denuncia_usuario
-- -----------------------------------------------------
DROP TABLE IF EXISTS view_denuncia_usuario;
DROP VIEW IF EXISTS view_denuncia_usuario ;
CREATE VIEW view_denuncia_usuario AS
    SELECT 
        d.tipo AS tipo,
        d.descricao AS denunciaDescricao,
        d.data AS data,
        delator.nome AS delatorNome,
        delator.email AS delatorEmail,
        delator.acesso AS delatorAcesso,
        u.email AS email,
        u.nome AS nome,
        u.acesso AS acesso
    FROM
        ((tbl_denuncia d
        JOIN tbl_usuario delator ON ((d.delatorId = delator.id)))
        JOIN tbl_usuario u ON ((d.usuarioId = u.id)));

-- -----------------------------------------------------
-- View view_movel
-- -----------------------------------------------------
DROP TABLE IF EXISTS view_movel;
DROP VIEW IF EXISTS view_movel ;
CREATE VIEW view_movel AS
    SELECT 
        u.nome AS usuarioNome,
        c.nome AS categoria,
        m.nome AS nome,
        m.descricao AS descricao,
        m.imagem AS imagem,
        m.valorMes AS valorMes,
        m.disponivel AS disponivel,
        m.id AS id,
        u.cidade AS cidade,
        (FLOOR(((SUM(a.avaliacao) / COUNT(a.avaliacao)) * 10)) / 10) AS avaliacao,
        COUNT(a.avaliacao) AS avaliacoes
    FROM
        tbl_movel m
        JOIN tbl_categoria c ON m.categoriaId = c.id
        JOIN tbl_usuario u ON m.usuarioId = u.id
        LEFT JOIN tbl_avaliacao a on a.avaliadoId = u.id
    GROUP BY usuarioNome, categoria, m.nome, descricao, imagem, valorMes, disponivel, m.id, cidade;

-- -----------------------------------------------------
-- View view_usuario
-- -----------------------------------------------------
DROP TABLE IF EXISTS view_usuario;
DROP VIEW IF EXISTS view_usuario ;
CREATE VIEW view_usuario AS
    SELECT 
        u.nome AS nome,
        u.email AS email,
        u.celular AS celular,
        u.foto AS foto,
        u.cep AS cep,
        u.logradouro AS logradouro,
        u.complemento AS complemento,
        u.bairro AS bairro,
        u.cidade AS cidade,
        u.uf AS uf,
        u.acesso AS acesso,
        (FLOOR(((SUM(a.avaliacao) / COUNT(a.avaliacao)) * 10)) / 10) AS avaliacao,
        COUNT(a.avaliacao) AS avaliacoes
    FROM
        tbl_usuario u
        LEFT JOIN tbl_avaliacao a ON a.avaliadoId = u.id
    GROUP BY nome, email, celular, foto, cep, logradouro, complemento, bairro, cidade, uf, acesso;

CREATE OR REPLACE FUNCTION update_aluguel()
RETURNS TRIGGER
AS
$$
DECLARE
  descricao TEXT;
  imagem TEXT;
  nome TEXT;
  valorMes REAL;
BEGIN
	SELECT
    descricao,
    imagem,
    nome,
    valorMes
  INTO
    descricao,
    imagem,
    nome,
    valorMes
  FROM tbl_movel m
  WHERE m.id = new.movelId;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_aluguel_insert
BEFORE INSERT ON tbl_aluguel
FOR EACH ROW
EXECUTE PROCEDURE update_aluguel();
