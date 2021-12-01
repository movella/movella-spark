package com.movella.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.AluguelDAO;
import com.movella.dao.MovelDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Movel;
import com.movella.model.Usuario;
import com.movella.responses.BadRequest;
import com.movella.responses.Forbidden;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import spark.*;

public class AluguelService {
  public static Route create = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (sessionUsuario.getAcesso().equals("normal"))
      return new Forbidden(res);

    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _movel = body.get("movel");

    // TODO: fix, falta checar se é int ou string
    // TODO: fix tudo

    if (_movel == null)
      return new BadRequest(res, Localization.invalidFurniture);

    final int movelId = _movel.getAsInt();
    final int usuarioId = sessionUsuario.getId();
    final Timestamp dataInicio = Timestamp.valueOf(LocalDateTime.now());
    final Timestamp dataFim = Timestamp.valueOf(LocalDateTime.now().plusDays(1));
    final double valorFrete = 20;
    final String chavePagamento = "aaa";
    // final double valorFrete = Double
    // .parseDouble(_valorMes.getAsString().replace(",", "!").replace(".",
    // "").replace("!", "."));

    try {
      final Movel movel = MovelDAO.read(movelId);

      final String descricao = movel.getDescricao();
      final String imagem = movel.getImagem();
      final String nome = movel.getNome();
      final double valorMes = movel.getValorMes();

      AluguelDAO.insert(movelId, usuarioId, dataInicio, dataFim, valorFrete, descricao, imagem, nome, valorMes,
          chavePagamento);

      return new Success(res, Localization.rentCreateSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  // public static Route all = (Request req, Response res) -> {
  // final Session session = req.session();
  // final Usuario sessionUsuario = (Usuario) session.attribute("user");

  // if (!sessionUsuario.getAcesso().equals("admin"))
  // return new Forbidden(res);

  // try {
  // final JsonArray out = new JsonArray();

  // MovelDAO.all().forEach((v) -> {
  // out.add(v.toJson());
  // });

  // return new Success(res, out);
  // } catch (InvalidDataException e) {
  // return new BadRequest(res, e.message);
  // } catch (RuntimeException e) {
  // return new BadRequest(res);
  // }
  // };

  // public static Route pagination = (Request req, Response res) -> {
  // final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

  // final JsonElement _limit = body.get("limit");
  // final JsonElement _offset = body.get("offset");
  // final JsonElement _categoria = body.get("categoria");
  // final JsonElement _filtro = body.get("filtro");
  // final JsonElement _disponivel = body.get("disponivel");
  // final JsonElement _order = body.get("order");

  // // TODO: fix, falta checar se é int ou string

  // if (_limit == null)
  // return new BadRequest(res, Localization.invalidLimit);

  // if (_offset == null)
  // return new BadRequest(res, Localization.invalidOffset);

  // if (_categoria == null)
  // return new BadRequest(res, Localization.invalidCategory);

  // if (_filtro == null)
  // return new BadRequest(res, Localization.invalidFilter);

  // if (_disponivel == null)
  // return new BadRequest(res, Localization.invalidDisponivel);

  // if (_order == null)
  // return new BadRequest(res, Localization.invalidOrder);

  // final int limit = _limit.getAsInt();
  // final int offset = _offset.getAsInt();
  // final String categoria = _categoria.getAsString();
  // final String filtro = _filtro.getAsString();
  // final Boolean disponivel = _filtro.getAsBoolean();
  // final String order = _order.getAsString();

  // try {
  // String usuarioNome = "";

  // try {
  // final Session session = req.session();
  // final Usuario sessionUsuario = (Usuario) session.attribute("user");

  // usuarioNome = sessionUsuario.getNome();
  // } catch (Exception e) {
  // }

  // final JsonObject out = new JsonObject();

  // final JsonArray array = new JsonArray();

  // final int qntPages = MovelDAO.getPages(limit, offset, categoria, filtro,
  // disponivel, order);

  // out.add("moveis", array);
  // out.addProperty("qntPages", qntPages);

  // MovelDAO.pagination(limit, offset, categoria, filtro, disponivel, order,
  // usuarioNome).forEach((v) -> {
  // array.add(v.toJson());
  // });

  // return new Success(res, out);
  // } catch (InvalidDataException e) {
  // return new BadRequest(res, e.message);
  // } catch (RuntimeException e) {
  // return new BadRequest(res);
  // }
  // };

  // public static Route upload = (Request req, Response res) -> {
  // final Session session = req.session();
  // final Usuario sessionUsuario = (Usuario) session.attribute("user");

  // final String _id = req.params("id");

  // final String body = req.body();

  // if (_id == null)
  // return new BadRequest(res, Localization.invalidId);

  // int id;

  // try {
  // id = Integer.parseInt(_id);
  // } catch (Exception e) {
  // return new BadRequest(res, Localization.invalidId);
  // }

  // try {
  // final String name = UUID.randomUUID().toString();

  // final String treatedBody = body.replaceAll(".+,(.+)", "$1");

  // try {
  // final byte[] bytes = Base64.getDecoder().decode(treatedBody);

  // final FileOutputStream f = new
  // FileOutputStream(String.format("src/main/resources/public/img/%s", name));

  // f.write(bytes);
  // f.close();
  // } catch (Exception e) {
  // System.out.println(e.getStackTrace());
  // }

  // final int maxId = MovelDAO.maxUploadId();

  // MovelDAO.upload(id == 0 ? maxId : id, sessionUsuario.getId(), name);

  // return new Success(res, Localization.furniturePictureUploadSuccess);
  // } catch (InvalidDataException e) {
  // return new BadRequest(res, e.message);
  // } catch (RuntimeException e) {
  // return new BadRequest(res);
  // }
  // };
}
