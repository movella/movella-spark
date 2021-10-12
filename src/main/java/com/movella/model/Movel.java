package com.movella.model;

public class Movel {
  int id;
  int categoriaId;
  int usuarioId;
  String descricao;
  String imagem;
  String nome;
  double valorMes;
  int disponivel;
  double altura;
  double largura;
  double espessura;
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `categoriaId` INT UNSIGNED NOT NULL,
// `usuarioId` INT UNSIGNED NOT NULL,
// `descricao` TEXT NULL DEFAULT NULL,
// `imagem` VARCHAR(255) NOT NULL DEFAULT 'default.png',
// `nome` VARCHAR(40) NOT NULL,
// `valorMes` DOUBLE(9,2) UNSIGNED NOT NULL,
// `disponivel` TINYINT(1) NOT NULL,
// `altura` DOUBLE UNSIGNED NOT NULL,
// `largura` DOUBLE UNSIGNED NOT NULL,
// `espessura` DOUBLE UNSIGNED NOT NULL,
