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
    setid(0);
    setcategoriaId(0);
    setusuarioId(0);
    setdescricao(null);
    setimagem(null);
    setnome(null);
    setvalorMes(0);
    setdisponivel(false);
    setaltura(0);
    setlargura(0);
    setespessura(0);
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
    setid(id);
    setcategoriaId(categoriaId);
    setusuarioId(usuarioId);
    setdescricao(descricao);
    setimagem(imagem);
    setnome(nome);
    setvalorMes(valorMes);
    setdisponivel(disponivel);
    setaltura(altura);
    setlargura(largura);
    setespessura(espessura);
  }

  public int getid() {
    return this.id;
  };

  public int getcategoriaId() {
    return this.categoriaId;
  };

  public int getusuarioId() {
    return this.usuarioId;
  };

  public String getdescricao() {
    return this.descricao;
  };

  public String getimagem() {
    return this.imagem;
  };

  public String getnome() {
    return this.nome;
  };

  public double getvalorMes() {
    return this.valorMes;
  };

  public boolean getdisponivel() {
    return this.disponivel;
  };

  public double getaltura() {
    return this.altura;
  };

  public double getlargura() {
    return this.largura;
  };

  public double getespessura() {
    return this.espessura;
  };

  public void setid(int id) {
    this.id = id;
  };

  public void setcategoriaId(int categoriaId) {
    this.categoriaId = categoriaId;
  };

  public void setusuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  };

  public void setdescricao(String descricao) {
    this.descricao = descricao;
  };

  public void setimagem(String imagem) {
    this.imagem = imagem;
  };

  public void setnome(String nome) {
    this.nome = nome;
  };

  public void setvalorMes(double valorMes) {
    this.valorMes = valorMes;
  };

  public void setdisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  };

  public void setaltura(double altura) {
    this.altura = altura;
  };

  public void setlargura(double largura) {
    this.largura = largura;
  };

  public void setespessura(double espessura) {
    this.espessura = espessura;
  };

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getid());
    jsonObject.addProperty("categoriaId", getcategoriaId());
    jsonObject.addProperty("usuarioId", getusuarioId());
    jsonObject.addProperty("descricao", getdescricao());
    jsonObject.addProperty("imagem", getimagem());
    jsonObject.addProperty("nome", getnome());
    jsonObject.addProperty("valorMes", getvalorMes());
    jsonObject.addProperty("disponivel", getdisponivel());
    jsonObject.addProperty("altura", getaltura());
    jsonObject.addProperty("largura", getlargura());
    jsonObject.addProperty("espessura", getespessura());

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
        movel.setid(js.get("id").getAsInt());
        break;
      case "categoriaid":
        movel.setcategoriaId(js.get("categoriaid").getAsInt());
        break;
      case "usuarioid":
        movel.setusuarioId(js.get("usuarioid").getAsInt());
        break;
      case "descricao":
        movel.setdescricao(js.get("descricao").getAsString());
        break;
      case "imagem":
        movel.setimagem(js.get("imagem").getAsString());
        break;
      case "nome":
        movel.setnome(js.get("nome").getAsString());
        break;
      case "valormes":
        movel.setvalorMes(js.get("valormes").getAsDouble());
        break;
      case "disponivel":
        movel.setdisponivel(js.get("disponivel").getAsBoolean());
        break;
      case "altura":
        movel.setaltura(js.get("altura").getAsDouble());
        break;
      case "largura":
        movel.setlargura(js.get("largura").getAsDouble());
        break;
      case "espessura":
        movel.setespessura(js.get("espessura").getAsDouble());
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
