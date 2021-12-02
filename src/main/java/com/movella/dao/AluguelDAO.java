package com.movella.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.movella.app.DBConnection;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Aluguel;
import com.movella.utils.Localization;

public class AluguelDAO {
  public static Aluguel read(int id) throws Exception {
    final JsonObject res = DBConnection.queryOne("select * from tbl_aluguel where id = ?",
        new String[] { String.valueOf(id) });

    if (res == null)
      throw new InvalidDataException(Localization.rentalNotFound);

    final Aluguel aluguel = Aluguel.fromJson(res);

    return aluguel;
  }

  public static void insert(
      int movelId,
      int usuarioId,
      Timestamp dataInicio,
      Timestamp dataFim,
      double valorFrete,
      String descricao,
      String imagem,
      String nome,
      double valorMes,
      String chavePagamento) throws Exception {
    DBConnection.execute(
        "insert into tbl_aluguel (movelId, usuarioId, dataInicio, dataFim, valorFrete, descricao, imagem, nome, valorMes, chavePagamento) values (cast(? as integer), cast(? as integer), cast(? as timestamp), cast(? as timestamp), cast(? as real), ?, ?, ?, cast(? as real), ?)",
        new String[] { String.valueOf(movelId), String.valueOf(usuarioId), dataInicio.toString(), dataFim.toString(),
            String.valueOf(valorFrete), descricao, imagem, nome, String.valueOf(valorMes), chavePagamento });
  }

  public static List<Aluguel> all() throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_aluguel", new String[] {});
    final List<Aluguel> alugueis = new ArrayList<Aluguel>();

    res.forEach((v) -> {
      try {
        alugueis.add(Aluguel.fromJson(v));
      } catch (Exception e) {
      }
    });

    return alugueis;
  }

  public static List<Aluguel> all(int id) throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_aluguel where usuarioId = cast(? as integer)",
        new String[] { String.valueOf(id) });
    final List<Aluguel> alugueis = new ArrayList<Aluguel>();

    res.forEach((v) -> {
      try {
        alugueis.add(Aluguel.fromJson(v));
      } catch (Exception e) {
      }
    });

    return alugueis;
  }

  public static void delete(int id, int usuarioId) throws Exception {
    DBConnection.execute(
        "delete from tbl_aluguel where id = cast(? as integer) and usuarioId = cast(? as integer)",
        new String[] { String.valueOf(id), String.valueOf(usuarioId) });
  }

  public static void delete(int id) throws Exception {
    DBConnection.execute(
        "delete from tbl_aluguel where id = cast(? as integer)",
        new String[] { String.valueOf(id) });
  }
}
