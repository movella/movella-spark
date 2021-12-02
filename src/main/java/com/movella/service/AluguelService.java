package com.movella.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movella.dao.AluguelDAO;
import com.movella.dao.MovelDAO;
import com.movella.dao.PagamentoDAO;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Movel;
import com.movella.model.Pagamento;
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
    final JsonElement _dias = body.get("dias");
    final JsonElement _pagamento = body.get("pagamento");

    if (_movel == null)
      return new BadRequest(res, Localization.invalidFurniture);

    if (_dias == null)
      return new BadRequest(res, Localization.invalidDays);

    if (_pagamento == null)
      return new BadRequest(res, Localization.invalidPayment);

    final int dias = _dias.getAsInt();
    final int movelId = _movel.getAsInt();
    final int pagamentoId = _pagamento.getAsInt();
    final int usuarioId = sessionUsuario.getId();
    final Timestamp dataInicio = Timestamp.valueOf(LocalDateTime.now());
    final Timestamp dataFim = Timestamp.valueOf(LocalDateTime.now().plusDays(dias));
    final double valorFrete = 0;

    try {
      final Pagamento pagamento = PagamentoDAO.read(pagamentoId);

      final String chavePagamento = pagamento.getChave();

      final Movel movel = MovelDAO.read(movelId);

      final String descricao = movel.getDescricao();
      final String imagem = movel.getImagem();
      final String nome = movel.getNome();
      final double valorMes = movel.getValorMes();

      AluguelDAO.insert(movelId, usuarioId, dataInicio, dataFim, valorFrete, descricao, imagem, nome, valorMes,
          chavePagamento);

      MovelDAO.updateDisponivel(movelId, false);

      return new Success(res, Localization.rentCreateSuccess);
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

      AluguelDAO.all().forEach((v) -> {
        out.add(v.toJson());
      });

      return new Success(res, out);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };

  public static Route all = (Request req, Response res) -> {
    final Session session = req.session();
    final Usuario sessionUsuario = (Usuario) session.attribute("user");

    if (sessionUsuario.getAcesso().equals("normal"))
      return new Forbidden(res);

    final int id = sessionUsuario.getId();

    try {
      final JsonArray out = new JsonArray();

      AluguelDAO.all(id).forEach((v) -> {
        out.add(v.toJson());
      });

      return new Success(res, out);
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
      AluguelDAO.delete(id);

      return new Success(res, Localization.rentalDeleteSuccess);
    } catch (InvalidDataException e) {
      return new BadRequest(res, e.message);
    } catch (RuntimeException e) {
      return new BadRequest(res);
    }
  };
}