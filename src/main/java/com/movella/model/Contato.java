package com.movella.model;

import java.sql.Timestamp;

public class Contato {
  int id;
  String nome;
  String email;
  String assunto;
  String mensagem;
  Timestamp data;
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `nome` VARCHAR(64) NOT NULL,
// `email` VARCHAR(255) NOT NULL,
// `assunto` VARCHAR(64) NOT NULL,
// `mensagem` TEXT NOT NULL,
// `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
