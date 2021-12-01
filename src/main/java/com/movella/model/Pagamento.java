package com.movella.model;

import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Pagamento {
  int id;
  String tipo;
  String chave;
  int usuarioId;

  public Pagamento() {
    setId(0);
    setTipo(null);
    setChave(null);
    setUsuarioId(0);
  }

  public Pagamento( //
      int id, //
      String tipo, //
      String chave, //
      int usuarioId //
  ) {
    setId(id);
    setTipo(tipo);
    setChave(chave);
    setUsuarioId(usuarioId);
  }

  public int getId() {
    return this.id;
  }

  public String getTipo() {
    return this.tipo;
  }

  public String getChave() {
    return this.chave;
  }

  public int getUsuarioId() {
    return this.usuarioId;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public void setChave(String chave) {
    this.chave = chave;
  }

  public void setUsuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("id", getId());
    jsonObject.addProperty("tipo", getTipo());
    jsonObject.addProperty("chave", getChave());
    jsonObject.addProperty("usuarioId", getUsuarioId());

    return jsonObject;
  }

  public static Pagamento fromJson(JsonObject js) throws SQLException {
    final Pagamento pagamento = new Pagamento();

    for (String key : js.keySet()) {
      final String uKey = key.toLowerCase();
      final JsonElement val = js.get(uKey);

      if (val == null)
        continue;

      switch (uKey) {
        case "id":
          pagamento.setId(js.get("id").getAsInt());
          break;
        case "tipo":
          pagamento.setTipo(js.get("tipo").getAsString());
          break;
        case "chave":
          pagamento.setChave(js.get("chave").getAsString());
          break;
        case "usuarioid":
          pagamento.setUsuarioId(js.get("usuarioid").getAsInt());
          break;
      }
    }

    return pagamento;
  }
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `tipo` ENUM('cpf') NOT NULL,
// `chave` VARCHAR(255) NOT NULL,
// `usuarioId` INT UNSIGNED NOT NULL,
