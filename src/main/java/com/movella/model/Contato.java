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
    setid(0);
    setnome(null);
    setemail(null);
    setassunto(null);
    setmensagem(null);
    setdata(null);
  }

  public Contato( //
      int id, //
      String nome, //
      String email, //
      String assunto, //
      String mensagem, //
      Timestamp data //
  ) {
    setid(id);
    setnome(nome);
    setemail(email);
    setassunto(assunto);
    setmensagem(mensagem);
    setdata(data);
  }

  public int getid() {
    return this.id;
  }

  public String getnome() {
    return this.nome;
  }

  public String getemail() {
    return this.email;
  }

  public String getassunto() {
    return this.assunto;
  }

  public String getmensagem() {
    return this.mensagem;
  }

  public Timestamp getdata() {
    return this.data;
  }

  public void setid(int id) {
    this.id = id;
  }

  public void setnome(String nome) {
    this.nome = nome;
  }

  public void setemail(String email) {
    this.email = email;
  }

  public void setassunto(String assunto) {
    this.assunto = assunto;
  }

  public void setmensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public void setdata(Timestamp data) {
    this.data = data;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getid());
    jsonObject.addProperty("nome", getnome());
    jsonObject.addProperty("email", getemail());
    jsonObject.addProperty("assunto", getassunto());
    jsonObject.addProperty("mensagem", getmensagem());
    jsonObject.addProperty("data", getdata().toString());

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
        contato.setid(js.get("id").getAsInt());
        break;
      case "nome":
        contato.setnome(js.get("nome").getAsString());
        break;
      case "email":
        contato.setemail(js.get("email").getAsString());
        break;
      case "assunto":
        contato.setassunto(js.get("assunto").getAsString());
        break;
      case "mensagem":
        contato.setmensagem(js.get("mensagem").getAsString());
        break;
      case "data":
        contato.setdata(Timestamp.valueOf(js.get("data").getAsString()));
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
