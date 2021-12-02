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

  // public static List<MovelPaginado> pagination(int limit, int offset, String
  // categoria, String filtro,
  // Boolean disponivel, String order, String usuarioNome) throws Exception {
  // final String disponivelClause = disponivel ? "'disponivel'" : "true";
  // final String categoriaClause = categoria.equals("Todos") ? "true" :
  // String.format("categoria = '%s'", categoria);
  // final String filterClause = filtro.length() == 0 ? "true"
  // : String.format("lower(nome) like lower('%%%s%%')", filtro);
  // final String orderClause = order;

  // final List<JsonObject> res = DBConnection.query(String.format(
  // "select *, usuarionome = ? as seu from view_movel where %s and %s and %s
  // order by %s limit cast(? as integer) offset cast(? as integer)",
  // disponivelClause, filterClause, categoriaClause, orderClause),
  // new String[] { usuarioNome, String.valueOf(limit), String.valueOf(offset) });
  // final List<MovelPaginado> moveis = new ArrayList<MovelPaginado>();

  // res.forEach((v) -> {
  // try {
  // moveis.add(MovelPaginado.fromJson(v));
  // } catch (Exception e) {
  // }
  // });

  // return moveis;
  // }

  // public static int getPages(int limit, int offset, String categoria, String
  // filtro, Boolean disponivel, String order)
  // throws Exception {
  // final String disponivelClause = disponivel ? "'disponivel'" : "true";
  // final String categoriaClause = categoria.equals("Todos") ? "true" :
  // String.format("categoria = '%s'", categoria);
  // final String filterClause = filtro.length() == 0 ? "true"
  // : String.format("lower(nome) like lower('%%%s%%')", filtro);

  // final JsonObject res = DBConnection
  // .queryOne(String.format("select count(*) as qnt from view_movel where %s and
  // %s and %s", disponivelClause,
  // filterClause, categoriaClause), new String[] {});
  // final int qntPages = (int) Math.ceil(res.get("qnt").getAsInt() / (float)
  // limit);

  // return qntPages;
  // }
}
