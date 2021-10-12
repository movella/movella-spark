package com.movella.model;

public class Usuario {
  int id;
  String celular;
  String email;
  String foto;
  String senha;
  String nome;
  String acesso;
  String cep;
  String logradouro;
  String complemento;
  String bairro;
  String cidade;
  String uf;
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `celular` VARCHAR(11) NULL DEFAULT NULL,
// `email` VARCHAR(255) NOT NULL,
// `foto` VARCHAR(255) NOT NULL DEFAULT 'default.png',
// `senha` TEXT NOT NULL,
// `nome` VARCHAR(20) NOT NULL,
// `acesso` ENUM('normal', 'verificado', 'admin') NOT NULL DEFAULT 'normal',
// `cep` CHAR(8) NULL DEFAULT NULL,
// `logradouro` VARCHAR(255) NULL DEFAULT NULL,
// `complemento` VARCHAR(255) NULL DEFAULT NULL,
// `bairro` VARCHAR(255) NULL DEFAULT NULL,
// `cidade` VARCHAR(255) NULL DEFAULT NULL,
// `uf` CHAR(2) NULL DEFAULT NULL,
