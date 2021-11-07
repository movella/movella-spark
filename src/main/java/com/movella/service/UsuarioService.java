package com.movella.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.UsuarioDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Usuario;
import com.movella.responses.BadRequest;
import com.movella.responses.Success;
import com.movella.utils.Localization;

import org.postgresql.util.PSQLException;

import spark.*;

public class UsuarioService {
  public static Route login = (Request req, Response res) -> {
    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _email = body.get("email");
    final JsonElement _senha = body.get("senha");

    if (_email == null)
      return new BadRequest(res, Localization.invalidEmail);

    if (_senha == null)
      return new BadRequest(res, Localization.invalidPassword);

    final String email = _email.getAsString();
    final String senha = _senha.getAsString();

    try {
      final Usuario usuario = UsuarioDAO.login(email, senha);

      req.session(true);
      req.session().attribute("user", usuario);

      return new Success(res, Localization.loginSuccessful);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    }
  };

  public static Route register = (Request req, Response res) -> {
    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _nome = body.get("nome");
    final JsonElement _email = body.get("email");
    final JsonElement _senha = body.get("senha");

    if (_nome == null)
      return new BadRequest(res, Localization.invalidName);

    if (_email == null)
      return new BadRequest(res, Localization.invalidEmail);

    if (_senha == null)
      return new BadRequest(res, Localization.invalidPassword);

    final String nome = _nome.getAsString();
    final String email = _email.getAsString();
    final String senha = _senha.getAsString();

    try {
      final Usuario usuario = UsuarioDAO.register(nome, email, senha);

      req.session(true);
      req.session().attribute("user", usuario);

      return new Success(res, Localization.userRegisterSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      if (e.getCause().getClass() == PSQLException.class) {
        if (e.getMessage().contains("usuario_email_unique"))
          return new BadRequest(res, Localization.userRegisterDuplicateEmail);

        if (e.getMessage().contains("usuario_email_unique"))
          return new BadRequest(res, Localization.userRegisterDuplicateUsername);
      }
      return new BadRequest(res);
    }
  };
}
