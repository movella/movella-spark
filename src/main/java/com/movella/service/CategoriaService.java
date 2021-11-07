package com.movella.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.CategoriaDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.responses.BadRequest;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import spark.*;

public class CategoriaService {
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
      return new Success(res, CategoriaDAO.read(id).toJson());
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    }
  };

  public static Route create = (Request req, Response res) -> {
    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _nome = body.get("nome");

    if (_nome == null)
      return new BadRequest(res, Localization.invalidName);

    final String nome = _nome.getAsString();

    try {
      CategoriaDAO.insert(nome);

      return new Success(res, Localization.categoriyCreateSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route all = (Request req, Response res) -> {
    try {
      final JsonArray out = new JsonArray();

      CategoriaDAO.all().forEach((v) -> {
        out.add(v.toJson());
      });

      return new Success(res, out);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };
}
