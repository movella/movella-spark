package com.movella.app;

import static spark.Spark.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.movella.model.Usuario;
import com.movella.responses.Unauthorized;
import com.movella.service.CategoriaService;
import com.movella.service.ContatoService;
import com.movella.service.MovelService;
import com.movella.service.UsuarioService;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
  public static void main(String[] args) {
    try {
      final int port = getHerokuAssignedPort();

      port(port);

      staticFiles.location("/public");

      before((req, res) -> {
        String body = req.body();

        if (body == null)
          body = "";

        System.out.println(String.format("[%s] - %s %s %s",
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date(System.currentTimeMillis())), req.ip(),
            req.requestMethod(), req.url()));

        System.out.println(body.length() < 4000 ? body : "== Large body payload ==");
      });

      notFound((req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/not-found");
      });

      internalServerError((req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/error");
      });

      get("/", (req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/moveis");
      });

      get("/moveis", (req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/moveis");
      });

      get("/sobre", (req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/sobre");
      });

      get("/contato", (req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/contato");
      });

      get("/perfil", (req, res) -> {
        if (blockAccess(req, res)) {
          res.redirect("/entrar");
          return null;
        }

        return render(populateHashMap(new HashMap<>(), req), "/perfil");
      });

      get("/cadastrarmovel", (req, res) -> {
        if (blockAccess(req, res)) {
          res.redirect("/entrar");
          return null;
        }

        return render(populateHashMap(new HashMap<>(), req), "/cadastrarmovel");
      });

      get("/entrar", (req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/entrar");
      });

      get("/criarconta", (req, res) -> {
        return render(populateHashMap(new HashMap<>(), req), "/criarconta");
      });

      get("/sair", (req, res) -> {
        req.session().invalidate();

        res.redirect("/entrar");

        return render(populateHashMap(new HashMap<>(), req), "/entrar");
      });

      // API

      before("/api/*", (req, res) -> {
        blockAccess(req, res);
      });

      path("/api", () -> {
        post("/login", UsuarioService.login);

        post("/register", UsuarioService.register);

        get("/contato/:id", ContatoService.read);

        post("/contato/create", ContatoService.create);

        post("/movel/create", MovelService.create);

        get("/categorias", CategoriaService.all);

        post("/categoria/create", CategoriaService.create);

        post("/usuario/update", UsuarioService.update);

        get("/movel/all", MovelService.all);

        post("/moveis", MovelService.pagination);

        post("/movel/upload/:id", MovelService.upload);
      });

      System.out.println(String.format("listening on port %d", port));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  static HashMap<String, Object> populateHashMap(HashMap<String, Object> hashMap, Request req) {
    final Session session = req.session();
    final boolean hasSession = session.attribute("user") != null;

    hashMap.put("hasSession", hasSession);
    hashMap.put("user", session.attribute("user"));

    return hashMap;
  }

  static boolean blockAccess(Request req, Response res) {
    final String path = req.pathInfo();
    final Session session = req.session();
    final boolean hasSession = session.attribute("user") != null;

    if (path.equals("/api/login") || path.equals("/api/register") || path.equals("/api/contato/create")
        || path.equals("/api/moveis") || path.equals("/api/categorias"))
      return false;

    if (path.equals("/perfil") && !hasSession)
      return true;

    if (path.equals("/cadastrarmovel")
        && (!hasSession || ((Usuario) session.attribute("user")).getacesso().equals("normal")))
      return true;

    if (!hasSession && path.contains("/api/"))
      new Unauthorized(res);

    return false;
  }

  static int getHerokuAssignedPort() {
    final String port = new ProcessBuilder().environment().get("PORT");
    return port == null ? 80 : Integer.parseInt(port);
  }

  public static String render(Map<String, Object> model, String templatePath) {
    return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
  }
}

// TODO: Suas classes java seguem princípios de desenvolvimento Java?
// Comentários padronizados, atributos privados começando com letra minúscula e
// tendo um método get/set?

// TODO: O código src/main/java/app/Aplicacao.java tem um
// insert/update/remove/get/listar para cada um dos CRUDs?

// TODO: O pacote dao tem uma classe dao para cada um dos CRUDs? Cada classe DAO
// tem, pelo menos, um método para cada uma das operações
// insert/update/remove/get/listar? Temos também um método, por exemplo, para
// abrir a conexão com o banco de dados e outro para fechá-la?

// TODO: O pacote service tem uma classe para cada um dos CRUDs? Cada uma dessas
// classes tem, pelo menos, um método para cada uma das operações de
// insert/update/remove/get/listar?

// TODO: fix poder ver contato sem ser adm
// TODO: terminar a edição de perfil
// TODO: fazer cadastro de móveis
// TODO: fazer chaves cadastradas
// TODO: fazer sistema de aluguel
// TODO: fazer resto do perfil
// TODO: fazer a parte de admin
// TODO: