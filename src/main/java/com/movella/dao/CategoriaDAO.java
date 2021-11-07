package com.movella.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.movella.app.DBConnection;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Categoria;
import com.movella.utils.Localization;

public class CategoriaDAO {
  public static Categoria read(int id) throws Exception {
    final JsonObject res = DBConnection.queryOne("select * from tbl_categoria where id = ?",
        new String[] { String.valueOf(id) });

    if (res == null)
      throw new InvalidDataException(Localization.categoryNotFound);

    final Categoria categoria = Categoria.fromJson(res);

    return categoria;
  }

  public static List<Categoria> all() throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_categoria", new String[] {});
    final List<Categoria> categorias = new ArrayList<Categoria>();

    res.forEach((v) -> {
      try {
        categorias.add(Categoria.fromJson(v));
      } catch (Exception e) {
      }
    });

    return categorias;
  }

  public static void insert(String nome) throws Exception {
    DBConnection.execute("insert into tbl_categoria (nome) values (?)", new String[] { nome });
  }
}
