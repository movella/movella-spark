package com.movella.dao;

import java.util.ArrayList;
import java.util.List;

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

    final Contato contato = Contato.fromJson(res);

    return contato;
  }

  public static List<Contato> all() throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_contato",
        new String[] {});
    final List<Contato> contatos = new ArrayList<Contato>();

    res.forEach((v) -> {
      try {
        contatos.add(Contato.fromJson(v));
      } catch (Exception e) {
      }
    });

    return contatos;
  }

  public static void insert(String nome, String email, String assunto, String mensagem) throws Exception {
    DBConnection.execute("insert into tbl_contato (nome, email, assunto, mensagem) values (?, ?, ?, ?)",
        new String[] { nome, email, assunto, mensagem });
  }
}
