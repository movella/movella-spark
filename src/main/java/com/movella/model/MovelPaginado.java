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
      int avaliacoes //
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
      }
    }

    return movel;
  }
}
