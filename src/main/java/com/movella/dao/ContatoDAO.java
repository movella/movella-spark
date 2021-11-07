package com.movella.dao;

import com.google.gson.JsonObject;
import com.movella.app.DBConnection;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Contato;
import com.movella.utils.Localization;

public class ContatoDAO {
  public static Contato read(int id) throws Exception {
    final JsonObject res = DBConnection.queryOne("select * from tbl_contato where id = ?",
        new String[] { String.valueOf(id) });

    if (res == null)
      throw new InvalidDataException(Localization.contactNotFound);

    final Contato usuario = Contato.fromJson(res);

    return usuario;
  }

  public static void insert(String nome, String email, String assunto, String mensagem) throws Exception {
    DBConnection.execute("insert into tbl_contato (nome, email, assunto, mensagem) values (?, ?, ?, ?)",
        new String[] { nome, email, assunto, mensagem });
  }
}
