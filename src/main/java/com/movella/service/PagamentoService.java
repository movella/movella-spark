package com.movella.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.PagamentoDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Usuario;
import com.movella.responses.BadRequest;
import com.movella.responses.Forbidden;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import spark.*;

public class PagamentoService {
  public static Route create = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (sessionUsuario.getAcesso().equals("normal"))
      return new Forbidden(res);

    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _chave = body.get("chave");

    if (_chave == null)
      return new BadRequest(res, Localization.invalidKey);

    final int usuarioId = sessionUsuario.getId();

    final String chave = _chave.getAsString().replaceAll("[\\.\\-]", "");

    try {
      PagamentoDAO.insert("cpf", chave, usuarioId);

      return new Success(res, Localization.paymentCreateSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route adminAll = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (!sessionUsuario.getAcesso().equals("admin"))
      return new Forbidden(res);

    try {
      final JsonArray out = new JsonArray();

      PagamentoDAO.all().forEach((v) -> {
        out.add(v.toJson());
      });

      return new Success(res, out);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route list = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    final int id = sessionUsuario.getId();

    try {
      final JsonArray out = new JsonArray();

      PagamentoDAO.all(id).forEach((v) -> {
        out.add(v.toJson());
      });

      return new Success(res, out);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route delete = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (sessionUsuario.getAcesso().equals("normal"))
      return new Forbidden(res);

    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _id = body.get("id");

    if (_id == null)
      return new BadRequest(res, Localization.invalidId);

    final int usuarioId = sessionUsuario.getId();
    final int id = _id.getAsInt();

    try {
      PagamentoDAO.delete(id, usuarioId);

      return new Success(res, Localization.paymentDeleteSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route adminDelete = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (!sessionUsuario.getAcesso().equals("admin"))
      return new Forbidden(res);

    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _id = body.get("id");

    if (_id == null)
      return new BadRequest(res, Localization.invalidId);

    final int id = _id.getAsInt();

    try {
      PagamentoDAO.delete(id);

      return new Success(res, Localization.paymentDeleteSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };
}
