package com.movella.model;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Contato {
  int id;
  String nome;
  String email;
  String assunto;
  String mensagem;
  Timestamp data;

  public Contato() {
    setId(0);
    setNome(null);
    setEmail(null);
    setAssunto(null);
    setMensagem(null);
    setData(null);
  }

  public Contato( //
      int id, //
      String nome, //
      String email, //
      String assunto, //
      String mensagem, //
      Timestamp data //
  ) {
    setId(id);
    setNome(nome);
    setEmail(email);
    setAssunto(assunto);
    setMensagem(mensagem);
    setData(data);
  }

  public int getId() {
    return this.id;
  }

  public String getNome() {
    return this.nome;
  }

  public String getEmail() {
    return this.email;
  }

  public String getAssunto() {
    return this.assunto;
  }

  public String getMensagem() {
    return this.mensagem;
  }

  public Timestamp getData() {
    return this.data;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAssunto(String assunto) {
    this.assunto = assunto;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public void setData(Timestamp data) {
    this.data = data;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getId());
    jsonObject.addProperty("nome", getNome());
    jsonObject.addProperty("email", getEmail());
    jsonObject.addProperty("assunto", getAssunto());
    jsonObject.addProperty("mensagem", getMensagem());
    jsonObject.addProperty("data", getData().toString());

    return jsonObject;
  }

  public static Contato fromJson(JsonObject js) throws SQLException {
    final Contato contato = new Contato();

    for (String key : js.keySet()) {
      final String uKey = key.toLowerCase();
      final JsonElement val = js.get(uKey.toLowerCase());

      if (val == null)
        continue;

      switch (uKey) {
      case "id":
        contato.setId(js.get("id").getAsInt());
        break;
      case "nome":
        contato.setNome(js.get("nome").getAsString());
        break;
      case "email":
        contato.setEmail(js.get("email").getAsString());
        break;
      case "assunto":
        contato.setAssunto(js.get("assunto").getAsString());
        break;
      case "mensagem":
        contato.setMensagem(js.get("mensagem").getAsString());
        break;
      case "data":
        contato.setData(Timestamp.valueOf(js.get("data").getAsString()));
        break;
      }
    }

    return contato;
  }
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `nome` VARCHAR(64) NOT NULL,
// `email` VARCHAR(255) NOT NULL,
// `assunto` VARCHAR(64) NOT NULL,
// `mensagem` TEXT NOT NULL,
// `data` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
