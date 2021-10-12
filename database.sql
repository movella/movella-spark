-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema movella
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema movella
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `movella` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `movella` ;

-- -----------------------------------------------------
-- Table `movella`.`tbl_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_usuario` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_usuario` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `celular` VARCHAR(11) NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `foto` VARCHAR(255) NOT NULL DEFAULT 'default.png',
  `senha` TEXT NOT NULL,
  `nome` VARCHAR(20) NOT NULL,
  `acesso` ENUM('normal', 'verificado', 'admin') NOT NULL DEFAULT 'normal',
  `cep` CHAR(8) NULL DEFAULT NULL,
  `logradouro` VARCHAR(255) NULL DEFAULT NULL,
  `complemento` VARCHAR(255) NULL DEFAULT NULL,
  `bairro` VARCHAR(255) NULL DEFAULT NULL,
  `cidade` VARCHAR(255) NULL DEFAULT NULL,
  `uf` CHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_categoria` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_categoria` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_movel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_movel` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_movel` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `categoriaId` INT UNSIGNED NOT NULL,
  `usuarioId` INT UNSIGNED NOT NULL,
  `descricao` TEXT NULL DEFAULT NULL,
  `imagem` VARCHAR(255) NOT NULL DEFAULT 'default.png',
  `nome` VARCHAR(40) NOT NULL,
  `valorMes` DOUBLE(9,2) UNSIGNED NOT NULL,
  `disponivel` TINYINT(1) NOT NULL,
  `altura` DOUBLE UNSIGNED NOT NULL,
  `largura` DOUBLE UNSIGNED NOT NULL,
  `espessura` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `categoriaId` (`categoriaId` ASC) VISIBLE,
  INDEX `usuarioId` (`usuarioId` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_usuario`
    FOREIGN KEY (`usuarioId`)
    REFERENCES `movella`.`tbl_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_categoria`
    FOREIGN KEY (`categoriaId`)
    REFERENCES `movella`.`tbl_categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_aluguel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_aluguel` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_aluguel` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `movelId` INT UNSIGNED NOT NULL,
  `usuarioId` INT UNSIGNED NOT NULL,
  `dataInicio` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dataFim` DATETIME NOT NULL,
  `valorFrete` DOUBLE(9,2) UNSIGNED NOT NULL,
  `descricao` TEXT NULL DEFAULT NULL,
  `imagem` VARCHAR(255) NULL DEFAULT 'default.png',
  `nome` VARCHAR(40) NULL DEFAULT NULL,
  `valorMes` DOUBLE(9,2) UNSIGNED NULL DEFAULT NULL,
  `chavePagamento` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `movelId` (`movelId` ASC) VISIBLE,
  INDEX `usuarioId` (`usuarioId` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_aluguel_movel`
    FOREIGN KEY (`movelId`)
    REFERENCES `movella`.`tbl_movel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aluguel_usuario`
    FOREIGN KEY (`usuarioId`)
    REFERENCES `movella`.`tbl_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_avaliacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_avaliacao` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_avaliacao` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `avaliacao` INT NOT NULL,
  `avaliadorId` INT UNSIGNED NOT NULL,
  `avaliadoId` INT UNSIGNED NOT NULL,
  `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `avaliadorId` (`avaliadorId` ASC) VISIBLE,
  INDEX `avaliadoId` (`avaliadoId` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_avaliacao_avaliador`
    FOREIGN KEY (`avaliadorId`)
    REFERENCES `movella`.`tbl_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_avaliacao_avaliado`
    FOREIGN KEY (`avaliadoId`)
    REFERENCES `movella`.`tbl_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_contato`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_contato` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_contato` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(64) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `assunto` VARCHAR(64) NOT NULL,
  `mensagem` TEXT NOT NULL,
  `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_denuncia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_denuncia` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_denuncia` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('informacoes inconsistentes', 'anuncio indevido', 'outro') NOT NULL,
  `descricao` TEXT NULL DEFAULT NULL,
  `delatorId` INT UNSIGNED NOT NULL,
  `usuarioId` INT UNSIGNED NULL DEFAULT NULL,
  `movelId` INT UNSIGNED NULL DEFAULT NULL,
  `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `delatorId` (`delatorId` ASC) VISIBLE,
  INDEX `usuarioId` (`usuarioId` ASC) VISIBLE,
  INDEX `movelId` (`movelId` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_denuncia_delator`
    FOREIGN KEY (`delatorId`)
    REFERENCES `movella`.`tbl_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_denuncia_usuario`
    FOREIGN KEY (`usuarioId`)
    REFERENCES `movella`.`tbl_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_denuncia_movel`
    FOREIGN KEY (`movelId`)
    REFERENCES `movella`.`tbl_movel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_foto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_foto` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_foto` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `caminho` VARCHAR(255) NOT NULL,
  `movelId` INT UNSIGNED NULL DEFAULT NULL,
  `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `movelId` (`movelId` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_movel`
    FOREIGN KEY (`movelId`)
    REFERENCES `movella`.`tbl_movel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `movella`.`tbl_pagamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`tbl_pagamento` ;

CREATE TABLE IF NOT EXISTS `movella`.`tbl_pagamento` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('cpf') NOT NULL,
  `chave` VARCHAR(255) NOT NULL,
  `usuarioId` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_pix_usuario_idx` (`usuarioId` ASC) VISIBLE,
  CONSTRAINT `fk_pagamento_usuario`
    FOREIGN KEY (`usuarioId`)
    REFERENCES `movella`.`tbl_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `movella` ;

-- -----------------------------------------------------
-- Placeholder table for view `movella`.`view_aluguel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movella`.`view_aluguel` (`id` INT, `locador` INT, `locatario` INT, `nome` INT, `categoria` INT, `valorMes` INT, `valorFrete` INT, `dataInicio` INT, `dataFim` INT, `descricao` INT, `imagem` INT, `email` INT, `celular` INT);

-- -----------------------------------------------------
-- Placeholder table for view `movella`.`view_denuncia_movel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movella`.`view_denuncia_movel` (`tipo` INT, `denunciaDescricao` INT, `data` INT, `delatorNome` INT, `delatorEmail` INT, `delatorAcesso` INT, `nome` INT, `descricao` INT, `valorMes` INT, `id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `movella`.`view_denuncia_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movella`.`view_denuncia_usuario` (`tipo` INT, `denunciaDescricao` INT, `data` INT, `delatorNome` INT, `delatorEmail` INT, `delatorAcesso` INT, `email` INT, `nome` INT, `acesso` INT);

-- -----------------------------------------------------
-- Placeholder table for view `movella`.`view_movel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movella`.`view_movel` (`usuarioNome` INT, `categoria` INT, `nome` INT, `descricao` INT, `imagem` INT, `valorMes` INT, `disponivel` INT, `id` INT, `cidade` INT);

-- -----------------------------------------------------
-- Placeholder table for view `movella`.`view_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movella`.`view_usuario` (`nome` INT, `email` INT, `celular` INT, `foto` INT, `cep` INT, `logradouro` INT, `complemento` INT, `bairro` INT, `cidade` INT, `uf` INT, `acesso` INT, `avaliacao` INT, `avaliacoes` INT);

-- -----------------------------------------------------
-- View `movella`.`view_aluguel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`view_aluguel`;
DROP VIEW IF EXISTS `movella`.`view_aluguel` ;
USE `movella`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `movella`.`view_aluguel` AS
    SELECT 
        `a`.`id` AS `id`,
        `u2`.`nome` AS `locador`,
        `u`.`nome` AS `locatario`,
        `a`.`nome` AS `nome`,
        `c`.`nome` AS `categoria`,
        `a`.`valorMes` AS `valorMes`,
        `a`.`valorFrete` AS `valorFrete`,
        `a`.`dataInicio` AS `dataInicio`,
        `a`.`dataFim` AS `dataFim`,
        `a`.`descricao` AS `descricao`,
        `a`.`imagem` AS `imagem`,
        `u`.`email` AS `email`,
        `u`.`celular` AS `celular`
    FROM
        ((((`movella`.`tbl_aluguel` `a`
        JOIN `movella`.`tbl_movel` `m` ON ((`a`.`movelId` = `m`.`id`)))
        JOIN `movella`.`tbl_categoria` `c` ON ((`m`.`categoriaId` = `c`.`id`)))
        JOIN `movella`.`tbl_usuario` `u` ON ((`a`.`usuarioId` = `u`.`id`)))
        JOIN `movella`.`tbl_usuario` `u2` ON ((`m`.`usuarioId` = `u2`.`id`)));

-- -----------------------------------------------------
-- View `movella`.`view_denuncia_movel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`view_denuncia_movel`;
DROP VIEW IF EXISTS `movella`.`view_denuncia_movel` ;
USE `movella`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `movella`.`view_denuncia_movel` AS
    SELECT 
        `d`.`tipo` AS `tipo`,
        `d`.`descricao` AS `denunciaDescricao`,
        `d`.`data` AS `data`,
        `delator`.`nome` AS `delatorNome`,
        `delator`.`email` AS `delatorEmail`,
        `delator`.`acesso` AS `delatorAcesso`,
        `m`.`nome` AS `nome`,
        `m`.`descricao` AS `descricao`,
        `m`.`valorMes` AS `valorMes`,
        `m`.`id` AS `id`
    FROM
        ((`movella`.`tbl_denuncia` `d`
        JOIN `movella`.`tbl_usuario` `delator` ON ((`d`.`delatorId` = `delator`.`id`)))
        JOIN `movella`.`tbl_movel` `m` ON ((`d`.`movelId` = `m`.`id`)));

-- -----------------------------------------------------
-- View `movella`.`view_denuncia_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`view_denuncia_usuario`;
DROP VIEW IF EXISTS `movella`.`view_denuncia_usuario` ;
USE `movella`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `movella`.`view_denuncia_usuario` AS
    SELECT 
        `d`.`tipo` AS `tipo`,
        `d`.`descricao` AS `denunciaDescricao`,
        `d`.`data` AS `data`,
        `delator`.`nome` AS `delatorNome`,
        `delator`.`email` AS `delatorEmail`,
        `delator`.`acesso` AS `delatorAcesso`,
        `u`.`email` AS `email`,
        `u`.`nome` AS `nome`,
        `u`.`acesso` AS `acesso`
    FROM
        ((`movella`.`tbl_denuncia` `d`
        JOIN `movella`.`tbl_usuario` `delator` ON ((`d`.`delatorId` = `delator`.`id`)))
        JOIN `movella`.`tbl_usuario` `u` ON ((`d`.`usuarioId` = `u`.`id`)));

-- -----------------------------------------------------
-- View `movella`.`view_movel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`view_movel`;
DROP VIEW IF EXISTS `movella`.`view_movel` ;
USE `movella`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `movella`.`view_movel` AS
    SELECT 
        `u`.`nome` AS `usuarioNome`,
        `c`.`nome` AS `categoria`,
        `m`.`nome` AS `nome`,
        `m`.`descricao` AS `descricao`,
        `m`.`imagem` AS `imagem`,
        `m`.`valorMes` AS `valorMes`,
        `m`.`disponivel` AS `disponivel`,
        `m`.`id` AS `id`,
        `u`.`cidade` AS `cidade`
    FROM
        ((`movella`.`tbl_movel` `m`
        JOIN `movella`.`tbl_categoria` `c` ON ((`m`.`categoriaId` = `c`.`id`)))
        JOIN `movella`.`tbl_usuario` `u` ON ((`m`.`usuarioId` = `u`.`id`)));

-- -----------------------------------------------------
-- View `movella`.`view_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movella`.`view_usuario`;
DROP VIEW IF EXISTS `movella`.`view_usuario` ;
USE `movella`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `movella`.`view_usuario` AS
    SELECT 
        `u`.`nome` AS `nome`,
        `u`.`email` AS `email`,
        `u`.`celular` AS `celular`,
        `u`.`foto` AS `foto`,
        `u`.`cep` AS `cep`,
        `u`.`logradouro` AS `logradouro`,
        `u`.`complemento` AS `complemento`,
        `u`.`bairro` AS `bairro`,
        `u`.`cidade` AS `cidade`,
        `u`.`uf` AS `uf`,
        `u`.`acesso` AS `acesso`,
        (FLOOR(((SUM(`a`.`avaliacao`) / COUNT(`a`.`avaliacao`)) * 10)) / 10) AS `avaliacao`,
        COUNT(`a`.`avaliacao`) AS `avaliacoes`
    FROM
        (`movella`.`tbl_usuario` `u`
        LEFT JOIN `movella`.`tbl_avaliacao` `a` ON ((`a`.`avaliadoId` = `u`.`id`)));
USE `movella`;

DELIMITER $$

USE `movella`$$
DROP TRIGGER IF EXISTS `movella`.`after_aluguel_insert` $$
USE `movella`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `movella`.`after_aluguel_insert`
BEFORE INSERT ON `movella`.`tbl_aluguel`
FOR EACH ROW
begin
  select
    descricao,
    imagem,
    nome,
    valorMes
  into
    @descricao,
    @imagem,
    @nome,
    @valorMes
  from tbl_movel m
  where m.id = new.movelId;

	set new.descricao = @descricao;
	set new.imagem = @imagem;
	set new.nome = @nome;
	set new.valorMes = @valorMes;
end$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
