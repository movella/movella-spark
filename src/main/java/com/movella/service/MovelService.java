package com.movella.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.MovelDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Usuario;
import com.movella.responses.BadRequest;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import org.postgresql.util.PSQLException;

import spark.*;

public class MovelService {
  // public static Route login = (Request req, Response res) -> {
  // final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

  // final JsonElement _email = body.get("email");
  // final JsonElement _senha = body.get("senha");

  // if (_email == null)
  // return new BadRequest(res, Localization.invalidEmail);

  // if (_senha == null)
  // return new BadRequest(res, Localization.invalidPassword);

  // final String email = _email.getAsString();
  // final String senha = _senha.getAsString();

  // try {
  // final Usuario usuario = UsuarioDAO.login(email, senha);

  // req.session(true);
  // req.session().attribute("user", usuario);

  // return new Success(res, Localization.loginSuccessful);
  // } catch (InvalidDataException e) {
  // return new BadRequest(res, e.message);
  // }
  // };

  public static Route create = (Request req, Response res) -> {
    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _nome = body.get("nome");
    final JsonElement _categoriaId = body.get("categoriaId");
    final JsonElement _foto = body.get("foto");
    final JsonElement _descricao = body.get("descricao");
    final JsonElement _valorMes = body.get("valorMes");
    final JsonElement _altura = body.get("altura");
    final JsonElement _largura = body.get("largura");
    final JsonElement _espessura = body.get("espessura");

    // TODO: fix, falta checar se é int ou string

    if (_nome == null)
      return new BadRequest(res, Localization.invalidName);

    if (_categoriaId == null)
      return new BadRequest(res, Localization.invalidCategory);

    if (_foto == null)
      return new BadRequest(res, Localization.invalidPicture);

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
    final int categoriaId = _categoriaId.getAsInt();
    final String foto = _foto.getAsString();
    final String descricao = _descricao.getAsString();
    final double valorMes = _valorMes.getAsDouble();
    final double altura = _altura.getAsDouble();
    final double largura = _largura.getAsDouble();
    final double espessura = _espessura.getAsDouble();

    try {
      MovelDAO.insert(categoriaId, ((Usuario) req.session().attribute("user")).getid(), descricao, foto, nome, valorMes,
          true, altura, largura, espessura);
      return new Success(res, Localization.furnitureCreateSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      if (e.getCause().getClass() == PSQLException.class) {
        // if (e.getMessage().contains("usuario_email_unique"))
        // return new BadRequest(res, Localization.userRegisterDuplicateEmail);

        // if (e.getMessage().contains("usuario_email_unique"))
        // return new BadRequest(res, Localization.userRegisterDuplicateUsername);
      }
      return new BadRequest(res);
    }
  };
}
