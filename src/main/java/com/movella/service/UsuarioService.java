package com.movella.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.UsuarioDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Usuario;
import com.movella.responses.BadRequest;
import com.movella.responses.Success;
import com.movella.utils.FunctionUtils;
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
      final Usuario usuario = UsuarioDAO.login(email, FunctionUtils.sha256hex(senha));

      req.session(true);
      req.session().attribute("user", usuario);

      return new Success(res, Localization.loginSuccess);
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
      final Usuario usuario = UsuarioDAO.register(nome, email, FunctionUtils.sha256hex(senha));

      req.session(true);
      req.session().attribute("user", usuario);

      return new Success(res, Localization.userRegisterSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      if (e.getCause().getClass() == PSQLException.class) {
        if (e.getMessage().contains("usuario_email_unique"))
          return new BadRequest(res, Localization.userRegisterDuplicateEmail);

        if (e.getMessage().contains("usuario_nome_unique"))
          return new BadRequest(res, Localization.userRegisterDuplicateUsername);
      }

      return new BadRequest(res);
    }
  };

  public static Route update = (Request req, Response res) -> {
    final JsonObject body = JsonParser.parseString(req.body()).getAsJsonObject();

    final JsonElement _cpf = body.get("cpf");
    final JsonElement _celular = body.get("celular");
    final JsonElement _cep = body.get("cep");
    final JsonElement _complemento = body.get("complemento");

    final JsonElement _nome = body.get("nome");
    final JsonElement _senha = body.get("senha");

    if (_nome == null)
      return new BadRequest(res, Localization.invalidName);

    if (_senha == null)
      return new BadRequest(res, Localization.invalidPassword);

    if (_cpf == null)
      return new BadRequest(res, Localization.invalidCpf);

    if (_celular == null)
      return new BadRequest(res, Localization.invalidCelular);

    if (_cep == null)
      return new BadRequest(res, Localization.invalidCep);

    if (_complemento == null)
      return new BadRequest(res, Localization.invalidComplemento);

    final String nome = _nome.getAsString();
    final String senha = _senha.getAsString();

    final String cpf = _cpf.getAsString().replaceAll("[\\.\\-]", "");
    final String celular = _celular.getAsString().replaceAll("[\\(\\)\\-\\s]", "");
    final String cep = _cep.getAsString().replaceAll("[\\.\\-]", "");
    final String complemento = _complemento.getAsString();

    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    final String acesso = sessionUsuario.getAcesso();
    final String email = sessionUsuario.getEmail();
    final String newAcesso = acesso == "admin" ? "admin" : "verificado";

    final int id = sessionUsuario.getId();

    final JsonObject viaCep = FunctionUtils.get(String.format("https://viacep.com.br/ws/%s/json/", cep));

    if (viaCep.get("erro") != null)
      return new BadRequest(res, Localization.invalidCep);

    try {
      final String bairro = viaCep.get("bairro").getAsString();
      final String cidade = viaCep.get("localidade").getAsString();
      final String logradouro = viaCep.get("logradouro").getAsString();
      final String uf = viaCep.get("uf").getAsString();

      try {
        final Usuario usuario = UsuarioDAO.update(email, newAcesso, bairro, celular, cep, cidade, cpf, complemento,
            logradouro, uf, nome, FunctionUtils.sha256hex(senha), id);

        req.session().attribute("user", usuario);

        return new Success(res, Localization.userUpdateSuccess);
      } catch (InvalidDataException e) {
        return new BadRequest(res, e.message);
      } catch (RuntimeException e) {
        if (e.getCause().getClass() == PSQLException.class) {
          if (e.getMessage().contains("usuario_email_unique"))
            return new BadRequest(res, Localization.userRegisterDuplicateEmail);

          if (e.getMessage().contains("usuario_nome_unique"))
            return new BadRequest(res, Localization.userRegisterDuplicateUsername);

          if (e.getMessage().contains("usuario_cpf_unique"))
            return new BadRequest(res, Localization.userUpdateError);
        }

        return new BadRequest(res);
      }
    } catch (HaltException e) {
      throw e;
    } catch (Exception e) {
      return new BadRequest(res, e.getMessage());
    }
  };
}
