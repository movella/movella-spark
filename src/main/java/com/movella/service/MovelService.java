package com.movella.service;

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

    if (sessionUsuario.getacesso().equals("normal"))
      return new Forbidden(res);

    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _nome = body.get("nome");
    final JsonElement _categoria = body.get("categoria");
    // final JsonElement _foto = body.get("foto");
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

    // if (_foto == null)
    // return new BadRequest(res, Localization.invalidPicture);

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
    // final String foto = _foto.getAsString();
    final String descricao = _descricao.getAsString();
    final double valorMes = Double
        .parseDouble(_valorMes.getAsString().replace(",", "!").replace(".", "").replace("!", "."));
    final double altura = _altura.getAsDouble();
    final double largura = _largura.getAsDouble();
    final double espessura = _espessura.getAsDouble();

    try {
      MovelDAO.insert(categoriaId, sessionUsuario.getid(), descricao, "movel-default.png", nome, valorMes, true, altura,
          largura, espessura);
      return new Success(res, Localization.furnitureCreateSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route all = (Request req, Response res) -> {
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
}
