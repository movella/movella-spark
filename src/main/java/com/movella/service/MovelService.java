package com.movella.service;

import java.io.FileOutputStream;
import java.util.Base64;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.MovelDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Usuario;
import com.movella.responses.BadRequest;
import com.movella.responses.Forbidden;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import spark.*;

public class MovelService {
  public static Route create = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (sessionUsuario.getAcesso().equals("normal"))
      return new Forbidden(res);

    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _nome = body.get("nome");
    final JsonElement _categoria = body.get("categoria");
    final JsonElement _descricao = body.get("descricao");
    final JsonElement _valorMes = body.get("valorMes");
    final JsonElement _altura = body.get("altura");
    final JsonElement _largura = body.get("largura");
    final JsonElement _espessura = body.get("espessura");

    // TODO: fix, falta checar se é int ou string

    if (_nome == null)
      return new BadRequest(res, Localization.invalidName);

    if (_categoria == null)
      return new BadRequest(res, Localization.invalidCategory);

    if (_descricao == null)
      return new BadRequest(res, Localization.invalidDescription);

    if (_valorMes == null)
      return new BadRequest(res, Localization.invalidMonthlyValue);

    if (_altura == null)
      return new BadRequest(res, Localization.invalidHeight);

    if (_largura == null)
      return new BadRequest(res, Localization.invalidWidth);

    if (_espessura == null)
      return new BadRequest(res, Localization.invalidThickness);

    final String nome = _nome.getAsString();
    final int categoriaId = _categoria.getAsInt();
    final String descricao = _descricao.getAsString();
    final double valorMes = Double
        .parseDouble(_valorMes.getAsString().replace(",", "!").replace(".", "").replace("!", "."));
    final double altura = _altura.getAsDouble();
    final double largura = _largura.getAsDouble();
    final double espessura = _espessura.getAsDouble();

    try {
      MovelDAO.insert(categoriaId, sessionUsuario.getId(), descricao, "movel-default.png", nome, valorMes, true, altura,
          largura, espessura);
      return new Success(res, Localization.furnitureCreateSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route all = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (!sessionUsuario.getAcesso().equals("admin"))
      return new Forbidden(res);

    try {
      final JsonArray out = new JsonArray();

      MovelDAO.all().forEach((v) -> {
        out.add(v.toJson());
      });

      return new Success(res, out);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route pagination = (Request req, Response res) -> {
    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _limit = body.get("limit");
    final JsonElement _offset = body.get("offset");
    final JsonElement _categoria = body.get("categoria");
    final JsonElement _filtro = body.get("filtro");
    final JsonElement _disponivel = body.get("disponivel");
    final JsonElement _order = body.get("order");

    // TODO: fix, falta checar se é int ou string

    if (_limit == null)
      return new BadRequest(res, Localization.invalidLimit);

    if (_offset == null)
      return new BadRequest(res, Localization.invalidOffset);

    if (_categoria == null)
      return new BadRequest(res, Localization.invalidCategory);

    if (_filtro == null)
      return new BadRequest(res, Localization.invalidFilter);

    if (_disponivel == null)
      return new BadRequest(res, Localization.invalidDisponivel);

    if (_order == null)
      return new BadRequest(res, Localization.invalidOrder);

    final int limit = _limit.getAsInt();
    final int offset = _offset.getAsInt();
    final String categoria = _categoria.getAsString();
    final String filtro = _filtro.getAsString();
    final Boolean disponivel = _filtro.getAsBoolean();
    final String order = _order.getAsString();

    try {
      final JsonObject out = new JsonObject();

      final JsonArray array = new JsonArray();

      final int qntPages = MovelDAO.getPages(limit, offset, categoria, filtro, disponivel, order);

      out.add("moveis", array);
      out.addProperty("qntPages", qntPages);

      MovelDAO.pagination(limit, offset, categoria, filtro, disponivel, order).forEach((v) -> {
        array.add(v.toJson());
      });

      return new Success(res, out);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route upload = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    final String _id = req.params("id");

    final String body = req.body();

    if (_id == null)
      return new BadRequest(res, Localization.invalidId);

    int id;

    try {
      id = Integer.parseInt(_id);
    } catch (Exception e) {
      return new BadRequest(res, Localization.invalidId);
    }

    try {
      final String name = UUID.randomUUID().toString();

      final String treatedBody = body.replaceAll(".+,(.+)", "$1");

      try {
        final byte[] bytes = Base64.getDecoder().decode(treatedBody);

        final FileOutputStream f = new FileOutputStream(String.format("src/main/resources/public/img/%s", name));

        f.write(bytes);
        f.close();
      } catch (Exception e) {
        System.out.println(e.getStackTrace());
      }

      final int maxId = MovelDAO.maxUploadId();

      MovelDAO.upload(id == 0 ? maxId : id, sessionUsuario.getId(), name);

      return new Success(res, Localization.furniturePictureUploadSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };
}
