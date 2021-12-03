package com.movella.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.movella.app.DBConnection;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Movel;
import com.movella.model.MovelPaginado;
import com.movella.utils.Localization;

public class MovelDAO {
  public static Movel read(int id) throws Exception {
    final JsonObject res = DBConnection.queryOne("select * from tbl_movel where id = cast(? as integer)",
        new String[] { String.valueOf(id) });

    if (res == null)
      throw new InvalidDataException(Localization.furnitureNotFound);

    final Movel movel = Movel.fromJson(res);

    return movel;
  }

  public static void insert(int categoriaId, int usuarioId, String descricao, String imagem, String nome,
      double valorMes, boolean disponivel, double altura, double largura, double espessura) throws Exception {
    DBConnection.execute(
        "insert into tbl_movel (categoriaId, usuarioId, descricao, imagem, nome, valorMes, disponivel, altura, largura, espessura) values (cast(? as integer), cast(? as integer), ?, ?, ?, cast(? as real), cast(? as boolean), cast(? as real), cast(? as real), cast(? as real))",
        new String[] { String.valueOf(categoriaId), String.valueOf(usuarioId), descricao, imagem, nome,
            String.valueOf(valorMes), String.valueOf(disponivel), String.valueOf(altura), String.valueOf(largura),
            String.valueOf(espessura) });
  }

  public static int maxUploadId() throws Exception {
    final JsonObject res = DBConnection.queryOne("select max(id) as max from tbl_movel", new String[] {});

    return res.get("max").getAsInt();
  }

  public static void upload(int id, int usuarioId, String imagem) throws Exception {
    DBConnection.execute(
        "update tbl_movel set imagem = ? where id = cast(? as integer) and usuarioId = cast(? as integer)",
        new String[] { imagem, String.valueOf(id), String.valueOf(usuarioId) });
  }

  public static List<Movel> all() throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_movel", new String[] {});
    final List<Movel> moveis = new ArrayList<Movel>();

    res.forEach((v) -> {
      try {
        moveis.add(Movel.fromJson(v));
      } catch (Exception e) {
      }
    });

    return moveis;
  }

  public static List<Movel> all(int id) throws Exception {
    final List<JsonObject> res = DBConnection.query("select * from tbl_movel where usuarioId = cast(? as integer)",
        new String[] { String.valueOf(id) });
    final List<Movel> moveis = new ArrayList<Movel>();

    res.forEach((v) -> {
      try {
        moveis.add(Movel.fromJson(v));
      } catch (Exception e) {
      }
    });

    return moveis;
  }

  public static List<MovelPaginado> pagination(int limit, int offset, String categoria, String filtro,
      Boolean disponivel, String order, String usuarioNome) throws Exception {
    final String disponivelClause = disponivel ? "'disponivel'" : "true";
    final String categoriaClause = categoria.equals("Todos") ? "true" : String.format("categoria = '%s'", categoria);
    final String filterClause = filtro.length() == 0 ? "true"
        : String.format("lower(nome) like lower('%%%s%%')", filtro);
    final String orderClause = order;

    final List<JsonObject> res = DBConnection.query(String.format(
        "select *, usuarionome = ? as seu from view_movel where %s and %s and %s order by %s limit cast(? as integer) offset cast(? as integer)",
        disponivelClause, filterClause, categoriaClause, orderClause),
        new String[] { usuarioNome, String.valueOf(limit), String.valueOf(offset) });
    final List<MovelPaginado> moveis = new ArrayList<MovelPaginado>();

    res.forEach((v) -> {
      try {
        moveis.add(MovelPaginado.fromJson(v));
      } catch (Exception e) {
      }
    });

    return moveis;
  }

  public static int getPages(int limit, int offset, String categoria, String filtro, Boolean disponivel, String order)
      throws Exception {
    final String disponivelClause = disponivel ? "'disponivel'" : "true";
    final String categoriaClause = categoria.equals("Todos") ? "true" : String.format("categoria = '%s'", categoria);
    final String filterClause = filtro.length() == 0 ? "true"
        : String.format("lower(nome) like lower('%%%s%%')", filtro);

    final JsonObject res = DBConnection
        .queryOne(String.format("select count(*) as qnt from view_movel where %s and %s and %s", disponivelClause,
            filterClause, categoriaClause), new String[] {});
    final int qntPages = (int) Math.ceil(res.get("qnt").getAsInt() / (float) limit);

    return qntPages;
  }

  public static void updateDisponivel(int id, boolean disponivel) throws Exception {
    DBConnection.execute(
        "update tbl_movel set disponivel = cast(? as boolean) where id = cast(? as integer)",
        new String[] { String.valueOf(disponivel), String.valueOf(id) });
  }

  public static void delete(int id, int usuarioId) throws Exception {
    DBConnection.execute(
        "delete from tbl_movel where id = cast(? as integer) and usuarioId = cast(? as integer) and disponivel = true",
        new String[] { String.valueOf(id), String.valueOf(usuarioId) });
  }

  public static void delete(int id) throws Exception {
    DBConnection.execute(
        "delete from tbl_movel where id = cast(? as integer)",
        new String[] { String.valueOf(id) });
  }
}
