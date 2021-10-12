package com.movella.model;

import java.security.Timestamp;

public class Aluguel {
  int id;
  int movelId;
  int usuarioId;
  Timestamp dataInicio;
  Timestamp dataFim;
  double valorFrete;
  String descricao;
  String imagem;
  String nome;
  double valorMes;
  String chavePagamento;
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `movelId` INT UNSIGNED NOT NULL,
// `usuarioId` INT UNSIGNED NOT NULL,
// `dataInicio` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
// `dataFim` DATETIME NOT NULL,
// `valorFrete` DOUBLE(9,2) UNSIGNED NOT NULL,
// `descricao` TEXT NULL DEFAULT NULL,
// `imagem` VARCHAR(255) NULL DEFAULT 'default.png',
// `nome` VARCHAR(40) NULL DEFAULT NULL,
// `valorMes` DOUBLE(9,2) UNSIGNED NULL DEFAULT NULL,
// `chavePagamento` VARCHAR(255) NOT NULL,
