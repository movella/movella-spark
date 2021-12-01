package com.movella.model;

import java.sql.Timestamp;
import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

  public Aluguel() {
    setid(0);
    setmovelId(0);
    setusuarioId(0);
    setdataInicio(null);
    setdataFim(null);
    setvalorFrete(0);
    setdescricao(null);
    setimagem(null);
    setnome(null);
    setvalorMes(0);
    setchavePagamento(null);
  }

  public Aluguel( //
      int id, //
      int movelId, //
      int usuarioId, //
      Timestamp dataInicio, //
      Timestamp dataFim, //
      double valorFrete, //
      String descricao, //
      String imagem, //
      String nome, //
      double valorMes, //
      String chavePagamento //
  ) {
    setid(id);
    setmovelId(movelId);
    setusuarioId(usuarioId);
    setdataInicio(dataInicio);
    setdataFim(dataFim);
    setvalorFrete(valorFrete);
    setdescricao(descricao);
    setimagem(imagem);
    setnome(nome);
    setvalorMes(valorMes);
    setchavePagamento(chavePagamento);
  }

  public int getid() {
    return this.id;
  }

  public int getmovelId() {
    return this.movelId;
  }

  public int getusuarioId() {
    return this.usuarioId;
  }

  public Timestamp getdataInicio() {
    return this.dataInicio;
  }

  public Timestamp getdataFim() {
    return this.dataFim;
  }

  public double getvalorFrete() {
    return this.valorFrete;
  }

  public String getdescricao() {
    return this.descricao;
  }

  public String getimagem() {
    return this.imagem;
  }

  public String getnome() {
    return this.nome;
  }

  public double getvalorMes() {
    return this.valorMes;
  }

  public String getchavePagamento() {
    return this.chavePagamento;
  }

  public void setid(int id) {
    this.id = id;
  }

  public void setmovelId(int movelId) {
    this.movelId = movelId;
  }

  public void setusuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  }

  public void setdataInicio(Timestamp dataInicio) {
    this.dataInicio = dataInicio;
  }

  public void setdataFim(Timestamp dataFim) {
    this.dataFim = dataFim;
  }

  public void setvalorFrete(double valorFrete) {
    this.valorFrete = valorFrete;
  }

  public void setdescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setimagem(String imagem) {
    this.imagem = imagem;
  }

  public void setnome(String nome) {
    this.nome = nome;
  }

  public void setvalorMes(double valorMes) {
    this.valorMes = valorMes;
  }

  public void setchavePagamento(String chavePagamento) {
    this.chavePagamento = chavePagamento;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getid());
    jsonObject.addProperty("movelId", getmovelId());
    jsonObject.addProperty("usuarioId", getusuarioId());
    jsonObject.addProperty("dataInicio", getdataInicio().toString());
    jsonObject.addProperty("dataFim", getdataFim().toString());
    jsonObject.addProperty("valorFrete", getvalorFrete());
    jsonObject.addProperty("descricao", getdescricao());
    jsonObject.addProperty("imagem", getimagem());
    jsonObject.addProperty("nome", getnome());
    jsonObject.addProperty("valorMes", getvalorMes());
    jsonObject.addProperty("chavePagamento", getchavePagamento());

    return jsonObject;
  }

  public static Aluguel fromJson(JsonObject js) throws SQLException {
    final Aluguel aluguel = new Aluguel();

    for (String key : js.keySet()) {
      final String uKey = key.toLowerCase();
      final JsonElement val = js.get(uKey);

      if (val == null)
        continue;

      switch (uKey) {

        case "id":
          aluguel.setid(js.get("id").getAsInt());
          break;
        case "movelId":
          aluguel.setmovelId(js.get("movelId").getAsInt());
          break;
        case "usuarioId":
          aluguel.setusuarioId(js.get("usuarioId").getAsInt());
          break;
        case "dataInicio":
          aluguel.setdataInicio(Timestamp.valueOf(js.get("dataInicio").getAsString()));
          break;
        case "dataFim":
          aluguel.setdataFim(Timestamp.valueOf(js.get("dataFim").getAsString()));
          break;
        case "valorFrete":
          aluguel.setvalorFrete(js.get("valorFrete").getAsDouble());
          break;
        case "descricao":
          aluguel.setdescricao(js.get("descricao").getAsString());
          break;
        case "imagem":
          aluguel.setimagem(js.get("imagem").getAsString());
          break;
        case "nome":
          aluguel.setnome(js.get("nome").getAsString());
          break;
        case "valorMes":
          aluguel.setvalorMes(js.get("valorMes").getAsDouble());
          break;
        case "chavePagamento":
          aluguel.setchavePagamento(js.get("chavePagamento").getAsString());
          break;
      }
    }

    return aluguel;
  }
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
