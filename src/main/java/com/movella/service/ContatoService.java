package com.movella.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.ContatoDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Usuario;
import com.movella.responses.BadRequest;
import com.movella.responses.Forbidden;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import spark.*;

public class ContatoService {
  public static Route adminRead = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (!sessionUsuario.getAcesso().equals("admin"))
      return new Forbidden(res);

    final String _id = req.params("id");

    if (_id == null)
      return new BadRequest(res, Localization.invalidId);

    int id;

    try {
      id = Integer.parseInt(_id);
    } catch (Exception e) {
      return new BadRequest(res, Localization.invalidId);
    }

    try {
      return new Success(res, ContatoDAO.read(id).toJson());
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    }
  };

  public static Route adminAll = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (!sessionUsuario.getAcesso().equals("admin"))
      return new Forbidden(res);

    try {
      final JsonArray out = new JsonArray();

      ContatoDAO.all().forEach((v) -> {
        out.add(v.toJson());
      });

      return new Success(res, out);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route create = (Request req, Response res) -> {
    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _nome = body.get("nome");
    final JsonElement _email = body.get("email");
    final JsonElement _assunto = body.get("assunto");
    final JsonElement _mensagem = body.get("mensagem");

    if (_nome == null)
      return new BadRequest(res, Localization.invalidName);

    if (_email == null)
      return new BadRequest(res, Localization.invalidEmail);

    if (_assunto == null)
      return new BadRequest(res, Localization.invalidSubject);

    if (_mensagem == null)
      return new BadRequest(res, Localization.invalidMessage);

    final String nome = _nome.getAsString();
    final String email = _email.getAsString();
    final String assunto = _assunto.getAsString();
    final String mensagem = _mensagem.getAsString();

    try {
      ContatoDAO.insert(nome, email, assunto, mensagem);

      return new Success(res, Localization.contactCreateSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };
}
