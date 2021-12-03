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
    setId(0);
    setMovelId(0);
    setUsuarioId(0);
    setDataInicio(null);
    setDataFim(null);
    setValorFrete(0);
    setDescricao(null);
    setImagem(null);
    setNome(null);
    setValorMes(0);
    setChavePagamento(null);
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
    setId(id);
    setMovelId(movelId);
    setUsuarioId(usuarioId);
    setDataInicio(dataInicio);
    setDataFim(dataFim);
    setValorFrete(valorFrete);
    setDescricao(descricao);
    setImagem(imagem);
    setNome(nome);
    setValorMes(valorMes);
    setChavePagamento(chavePagamento);
  }

  public int getId() {
    return this.id;
  }

  public int getMovelId() {
    return this.movelId;
  }

  public int getUsuarioId() {
    return this.usuarioId;
  }

  public Timestamp getDataInicio() {
    return this.dataInicio;
  }

  public Timestamp getDataFim() {
    return this.dataFim;
  }

  public double getValorFrete() {
    return this.valorFrete;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public String getImagem() {
    return this.imagem;
  }

  public String getNome() {
    return this.nome;
  }

  public double getValorMes() {
    return this.valorMes;
  }

  public String getChavePagamento() {
    return this.chavePagamento;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMovelId(int movelId) {
    this.movelId = movelId;
  }

  public void setUsuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  }

  public void setDataInicio(Timestamp dataInicio) {
    this.dataInicio = dataInicio;
  }

  public void setDataFim(Timestamp dataFim) {
    this.dataFim = dataFim;
  }

  public void setValorFrete(double valorFrete) {
    this.valorFrete = valorFrete;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setValorMes(double valorMes) {
    this.valorMes = valorMes;
  }

  public void setChavePagamento(String chavePagamento) {
    this.chavePagamento = chavePagamento;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getId());
    jsonObject.addProperty("movelId", getMovelId());
    jsonObject.addProperty("usuarioId", getUsuarioId());
    jsonObject.addProperty("dataInicio", getDataInicio().toString());
    jsonObject.addProperty("dataFim", getDataFim().toString());
    jsonObject.addProperty("valorFrete", getValorFrete());
    jsonObject.addProperty("descricao", getDescricao());
    jsonObject.addProperty("imagem", getImagem());
    jsonObject.addProperty("nome", getNome());
    jsonObject.addProperty("valorMes", getValorMes());
    jsonObject.addProperty("chavePagamento", getChavePagamento());

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
          aluguel.setId(js.get("id").getAsInt());
          break;
        case "movelid":
          aluguel.setMovelId(js.get("movelid").getAsInt());
          break;
        case "usuarioid":
          aluguel.setUsuarioId(js.get("usuarioid").getAsInt());
          break;
        case "datainicio":
          aluguel.setDataInicio(Timestamp.valueOf(js.get("datainicio").getAsString()));
          break;
        case "datafim":
          aluguel.setDataFim(Timestamp.valueOf(js.get("datafim").getAsString()));
          break;
        case "valorfrete":
          aluguel.setValorFrete(js.get("valorfrete").getAsDouble());
          break;
        case "descricao":
          aluguel.setDescricao(js.get("descricao").getAsString());
          break;
        case "imagem":
          aluguel.setImagem(js.get("imagem").getAsString());
          break;
        case "nome":
          aluguel.setNome(js.get("nome").getAsString());
          break;
        case "valormes":
          aluguel.setValorMes(js.get("valormes").getAsDouble());
          break;
        case "chavepagamento":
          aluguel.setChavePagamento(js.get("chavepagamento").getAsString());
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
