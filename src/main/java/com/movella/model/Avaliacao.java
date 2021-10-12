package com.movella.model;

import java.sql.Timestamp;

public class Avaliacao {
  int id;
  int avaliacao;
  int avaliadorId;
  int avaliadoId;
  Timestamp data;
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `avaliacao` INT NOT NULL,
// `avaliadorId` INT UNSIGNED NOT NULL,
// `avaliadoId` INT UNSIGNED NOT NULL,
// `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
