package com.movella.model;

public class Pagamento {
  int id;
  String tipo;
  String chave;
  int usuarioId;
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `tipo` ENUM('cpf') NOT NULL,
// `chave` VARCHAR(255) NOT NULL,
// `usuarioId` INT UNSIGNED NOT NULL,
