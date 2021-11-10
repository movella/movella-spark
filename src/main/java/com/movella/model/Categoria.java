package com.movella.model;

import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Categoria {
  int id;
  String nome;

  public Categoria() {
    setid(0);
    setnome(null);
  }

  public Categoria( //
      int id, //
      String nome //
  ) {
    setid(id);
    setnome(nome);
  }

  public int getid() {
    return id;
  }

  public String getnome() {
    return nome;
  }

  public void setid(int id) {
    this.id = id;
  }

  public void setnome(String nome) {
    this.nome = nome;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getid());
    jsonObject.addProperty("nome", getnome());

    return jsonObject;
  }

  public static Categoria fromJson(JsonObject js) throws SQLException {
    final Categoria categoria = new Categoria();

    for (String key : js.keySet()) {
      final String uKey = key.toLowerCase();
      final JsonElement val = js.get(uKey.toLowerCase());

      if (val == null)
        continue;

      switch (uKey) {
      case "id":
        categoria.setid(js.get("id").getAsInt());
        break;
      case "nome":
        categoria.setnome(js.get("nome").getAsString());
        break;
      }
    }

    return categoria;
  }
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `nome` VARCHAR(20) NOT NULL,
