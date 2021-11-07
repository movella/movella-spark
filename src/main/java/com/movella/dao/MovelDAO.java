package com.movella.dao;

import com.google.gson.JsonObject;
import com.movella.app.DBConnection;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Movel;
import com.movella.utils.Localization;

public class MovelDAO {
  public static Movel read(int id) throws Exception {
    final JsonObject res = DBConnection.queryOne("select * from tbl_movel where id = ?",
        new String[] { String.valueOf(id) });

    if (res == null)
      throw new InvalidDataException(Localization.furnitureNotFound);

    final Movel movel = Movel.fromJson(res);

    return movel;
  }

  public static void insert(int categoriaId, int usuarioId, String descricao, String imagem, String nome,
      double valorMes, boolean disponivel, double altura, double largura, double espessura) throws Exception {
    DBConnection.execute(
        "insert into tbl_movel (categoriaId, usuarioId, descricao, imagem, nome, valorMes, disponivel, altura, largura, espessura) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
        new String[] { String.valueOf(categoriaId), String.valueOf(usuarioId), descricao, imagem, nome,
            String.valueOf(valorMes), String.valueOf(disponivel), String.valueOf(altura), String.valueOf(largura),
            String.valueOf(espessura) });
  }
}
