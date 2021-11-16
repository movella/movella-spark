package com.movella.model;

import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Categoria {
  int id;
  String nome;

  public Categoria() {
    setId(0);
    setNome(null);
  }

  public Categoria( //
      int id, //
      String nome //
  ) {
    setId(id);
    setNome(nome);
  }

  public int getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getId());
    jsonObject.addProperty("nome", getNome());

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
        categoria.setId(js.get("id").getAsInt());
        break;
      case "nome":
        categoria.setNome(js.get("nome").getAsString());
        break;
      }
    }

    return categoria;
  }
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `nome` VARCHAR(20) NOT NULL,
