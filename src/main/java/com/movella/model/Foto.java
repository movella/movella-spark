package com.movella.model;

import java.sql.Timestamp;

public class Foto {
  int id;
  String caminho;
  int movelId;
  Timestamp data;
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `caminho` VARCHAR(255) NOT NULL,
// `movelId` INT UNSIGNED NULL DEFAULT NULL,
// `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
