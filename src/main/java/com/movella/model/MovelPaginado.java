package com.movella.model;

import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MovelPaginado {
  public String usuarionome = "";
  public String categoria = "";
  public String nome = "";
  public String descricao = "";
  public String imagem = "";
  public double valormes = 0;
  public boolean disponivel = false;
  public int id = 0;
  public String cidade = "";
  public double avaliacao = 0;
  public int avaliacoes = 0;
  public boolean seu = false;

  public MovelPaginado() {
  }

  public MovelPaginado( //
      String usuarionome, //
      String categoria, //
      String nome, //
      String descricao, //
      String imagem, //
      double valormes, //
      boolean disponivel, //
      int id, //
      String cidade, //
      double avaliacao, //
      int avaliacoes, //
      boolean seu //
  ) {
    this.usuarionome = usuarionome;
    this.categoria = categoria;
    this.nome = nome;
    this.descricao = descricao;
    this.imagem = imagem;
    this.valormes = valormes;
    this.disponivel = disponivel;
    this.id = id;
    this.cidade = cidade;
    this.avaliacao = avaliacao;
    this.avaliacoes = avaliacoes;
    this.seu = seu;
  }

  public String getUsuarioNome() {
    return this.usuarionome;
  }

  public String getCategoria() {
    return this.categoria;
  }

  public String getNome() {
    return this.nome;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public String getImagem() {
    return this.imagem;
  }

  public Double getValorMes() {
    return this.valormes;
  }

  public boolean getDisponivel() {
    return this.disponivel;
  }

  public int getId() {
    return this.id;
  }

  public String getCidade() {
    return this.cidade;
  }

  public Double getAvaliacao() {
    return this.avaliacao;
  }

  public int getAvaliacoes() {
    return this.avaliacoes;
  }

  public boolean getSeu() {
    return this.seu;
  }

  public void setUsuarioNome(String str) {
    this.usuarionome = str;
  }

  public void setCategoria(String str) {
    this.categoria = str;
  }

  public void setNome(String str) {
    this.nome = str;
  }

  public void setDescricao(String str) {
    this.descricao = str;
  }

  public void setImagem(String str) {
    this.imagem = str;
  }

  public void setValorMes(Double str) {
    this.valormes = str;
  }

  public void setDisponivel(boolean str) {
    this.disponivel = str;
  }

  public void setId(int str) {
    this.id = str;
  }

  public void setCidade(String str) {
    this.cidade = str;
  }

  public void setAvaliacao(Double str) {
    this.avaliacao = str;
  }

  public void setAvaliacoes(int str) {
    this.avaliacoes = str;
  }

  public void setSeu(boolean seu) {
    this.seu = seu;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("usuarionome", usuarionome);
    jsonObject.addProperty("categoria", categoria);
    jsonObject.addProperty("nome", nome);
    jsonObject.addProperty("descricao", descricao);
    jsonObject.addProperty("imagem", imagem);
    jsonObject.addProperty("valormes", valormes);
    jsonObject.addProperty("disponivel", disponivel);
    jsonObject.addProperty("id", id);
    jsonObject.addProperty("cidade", cidade);
    jsonObject.addProperty("avaliacao", avaliacao);
    jsonObject.addProperty("avaliacoes", avaliacoes);
    jsonObject.addProperty("seu", seu);

    return jsonObject;
  }

  public static MovelPaginado fromJson(JsonObject js) throws SQLException {
    final MovelPaginado movel = new MovelPaginado();

    for (String key : js.keySet()) {
      final String uKey = key.toLowerCase();
      final JsonElement val = js.get(uKey);

      if (val == null)
        continue;

      switch (uKey) {
      case "usuarionome":
        movel.usuarionome = js.get("usuarionome").getAsString();
        break;
      case "categoria":
        movel.categoria = js.get("categoria").getAsString();
        break;
      case "nome":
        movel.nome = js.get("nome").getAsString();
        break;
      case "descricao":
        movel.descricao = js.get("descricao").getAsString();
        break;
      case "imagem":
        movel.imagem = js.get("imagem").getAsString();
        break;
      case "valormes":
        movel.valormes = js.get("valormes").getAsDouble();
        break;
      case "disponivel":
        movel.disponivel = js.get("disponivel").getAsBoolean();
        break;
      case "id":
        movel.id = js.get("id").getAsInt();
        break;
      case "cidade":
        movel.cidade = js.get("cidade").getAsString();
        break;
      case "avaliacao":
        movel.avaliacao = js.get("avaliacao").getAsDouble();
        break;
      case "avaliacoes":
        movel.avaliacoes = js.get("avaliacoes").getAsInt();
        break;
      case "seu":
        movel.seu = js.get("seu").getAsBoolean();
        break;
      }
    }

    return movel;
  }
}
