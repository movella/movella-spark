package com.movella.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.ContatoDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.responses.BadRequest;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import spark.*;

public class ContatoService {
  public static Route read = (Request req, Response res) -> {
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

    // TODO
    if (_assunto == null)
      return new BadRequest(res, "");

    // TODO
    if (_mensagem == null)
      return new BadRequest(res, "");

    final String nome = _nome.getAsString();
    final String email = _email.getAsString();
    final String assunto = _assunto.getAsString();
    final String mensagem = _mensagem.getAsString();

    try {
      ContatoDAO.insert(nome, email, assunto, mensagem);

      // TODO
      return new Success(res, Localization.contatoSuccessful);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };
}
