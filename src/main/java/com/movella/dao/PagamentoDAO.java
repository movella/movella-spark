package com.movella.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.movella.app.DBConnection;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Pagamento;
import com.movella.utils.Localization;

public class PagamentoDAO {
  public static Pagamento read(int id) throws Exception {
    final JsonObject res = DBConnection.queryOne("select * from tbl_pagamento where id = cast(? as integer)",
        new String[] { String.valueOf(id) });

    if (res == null)
      throw new InvalidDataException(Localization.paymentNotFound);

    final Pagamento pagamento = Pagamento.fromJson(res);

    return pagamento;
  }

  public static void insert(String tipo, String chave, int usuarioId) throws Exception {
    DBConnection.execute(
        "insert into tbl_pagamento (tipo, chave, usuarioId) values (cast(? as tipopagamento), ?, cast(? as integer))",
        new String[] { tipo, chave, String.valueOf(usuarioId) });
  }

  public static List<Pagamento> all() throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_pagamento", new String[] {});
    final List<Pagamento> pagamentos = new ArrayList<Pagamento>();

    res.forEach((v) -> {
      try {
        pagamentos.add(Pagamento.fromJson(v));
      } catch (Exception e) {
      }
    });

    return pagamentos;
  }

  public static List<Pagamento> all(int id) throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_pagamento where usuarioId = cast(? as integer)",
        new String[] {
            String.valueOf(id)
        });
    final List<Pagamento> pagamentos = new ArrayList<Pagamento>();

    res.forEach((v) -> {
      try {
        pagamentos.add(Pagamento.fromJson(v));
      } catch (Exception e) {
      }
    });

    return pagamentos;
  }

  public static void delete(int id, int usuarioId) throws Exception {
    DBConnection.execute(
        "delete from tbl_pagamento where id = cast(? as integer) and usuarioId = cast(? as integer)",
        new String[] { String.valueOf(id), String.valueOf(usuarioId) });
  }
}
