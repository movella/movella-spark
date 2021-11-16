package com.movella.model;

import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Movel {
  int id;
  int categoriaId;
  int usuarioId;
  String descricao;
  String imagem;
  String nome;
  double valorMes;
  boolean disponivel;
  double altura;
  double largura;
  double espessura;

  public Movel() {
    setId(0);
    setCategoriaId(0);
    setUsuarioId(0);
    setDescricao(null);
    setImagem(null);
    setNome(null);
    setValorMes(0);
    setDisponivel(false);
    setAltura(0);
    setLargura(0);
    setEspessura(0);
  }

  public Movel( //
      int id, //
      int categoriaId, //
      int usuarioId, //
      String descricao, //
      String imagem, //
      String nome, //
      double valorMes, //
      boolean disponivel, //
      double altura, //
      double largura, //
      double espessura //
  ) {
    setId(id);
    setCategoriaId(categoriaId);
    setUsuarioId(usuarioId);
    setDescricao(descricao);
    setImagem(imagem);
    setNome(nome);
    setValorMes(valorMes);
    setDisponivel(disponivel);
    setAltura(altura);
    setLargura(largura);
    setEspessura(espessura);
  }

  public int getId() {
    return this.id;
  };

  public int getCategoriaId() {
    return this.categoriaId;
  };

  public int getUsuarioId() {
    return this.usuarioId;
  };

  public String getDescricao() {
    return this.descricao;
  };

  public String getImagem() {
    return this.imagem;
  };

  public String getNome() {
    return this.nome;
  };

  public double getValorMes() {
    return this.valorMes;
  };

  public boolean getDisponivel() {
    return this.disponivel;
  };

  public double getAltura() {
    return this.altura;
  };

  public double getLargura() {
    return this.largura;
  };

  public double getEspessura() {
    return this.espessura;
  };

  public void setId(int id) {
    this.id = id;
  };

  public void setCategoriaId(int categoriaId) {
    this.categoriaId = categoriaId;
  };

  public void setUsuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  };

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  };

  public void setImagem(String imagem) {
    this.imagem = imagem;
  };

  public void setNome(String nome) {
    this.nome = nome;
  };

  public void setValorMes(double valorMes) {
    this.valorMes = valorMes;
  };

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  };

  public void setAltura(double altura) {
    this.altura = altura;
  };

  public void setLargura(double largura) {
    this.largura = largura;
  };

  public void setEspessura(double espessura) {
    this.espessura = espessura;
  };

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getId());
    jsonObject.addProperty("categoriaId", getCategoriaId());
    jsonObject.addProperty("usuarioId", getUsuarioId());
    jsonObject.addProperty("descricao", getDescricao());
    jsonObject.addProperty("imagem", getImagem());
    jsonObject.addProperty("nome", getNome());
    jsonObject.addProperty("valorMes", getValorMes());
    jsonObject.addProperty("disponivel", getDisponivel());
    jsonObject.addProperty("altura", getAltura());
    jsonObject.addProperty("largura", getLargura());
    jsonObject.addProperty("espessura", getEspessura());

    return jsonObject;
  }

  public static Movel fromJson(JsonObject js) throws SQLException {
    final Movel movel = new Movel();

    for (String key : js.keySet()) {
      final String uKey = key.toLowerCase();
      final JsonElement val = js.get(uKey);

      if (val == null)
        continue;

      switch (uKey) {
      case "id":
        movel.setId(js.get("id").getAsInt());
        break;
      case "categoriaid":
        movel.setCategoriaId(js.get("categoriaid").getAsInt());
        break;
      case "usuarioid":
        movel.setUsuarioId(js.get("usuarioid").getAsInt());
        break;
      case "descricao":
        movel.setDescricao(js.get("descricao").getAsString());
        break;
      case "imagem":
        movel.setImagem(js.get("imagem").getAsString());
        break;
      case "nome":
        movel.setNome(js.get("nome").getAsString());
        break;
      case "valormes":
        movel.setValorMes(js.get("valormes").getAsDouble());
        break;
      case "disponivel":
        movel.setDisponivel(js.get("disponivel").getAsBoolean());
        break;
      case "altura":
        movel.setAltura(js.get("altura").getAsDouble());
        break;
      case "largura":
        movel.setLargura(js.get("largura").getAsDouble());
        break;
      case "espessura":
        movel.setEspessura(js.get("espessura").getAsDouble());
        break;
      }
    }

    return movel;
  }
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
