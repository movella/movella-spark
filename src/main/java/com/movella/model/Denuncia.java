package com.movella.model;

import java.sql.Timestamp;

public class Denuncia {
  int id;
  String tipo;
  String descricao;
  int delatorId;
  int usuarioId;
  int movelId;
  Timestamp data;

}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `tipo` ENUM('informacoes inconsistentes', 'anuncio indevido', 'outro') NOT
// NULL,
// `descricao` TEXT NULL DEFAULT NULL,
// `delatorId` INT UNSIGNED NOT NULL,
// `usuarioId` INT UNSIGNED NULL DEFAULT NULL,
// `movelId` INT UNSIGNED NULL DEFAULT NULL,
// `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
