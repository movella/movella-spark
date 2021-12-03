package com.movella.app;

import static spark.Spark.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.movella.model.Usuario;
import com.movella.responses.Unauthorized;
import com.movella.service.AluguelService;
import com.movella.service.CategoriaService;
import com.movella.service.ContatoService;
import com.movella.service.MovelService;
import com.movella.service.PagamentoService;
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

        post("/alugar", AluguelService.create);

        get("/aluguel/all", AluguelService.all);

        get("/categorias", CategoriaService.all);

        post("/contato/create", ContatoService.create);

        post("/moveis", MovelService.pagination);

        get("/movel/all", MovelService.all);

        post("/movel/create", MovelService.create);

        post("/movel/delete", MovelService.delete);

        post("/movel/upload/:id", MovelService.upload);

        get("/pagamentos", PagamentoService.list);

        post("/pagamento/create", PagamentoService.create);

        post("/pagamento/delete", PagamentoService.delete);

        post("/usuario/update", UsuarioService.update);

        // admin

        get("/admin/aluguel/all", AluguelService.adminAll);

        post("/admin/aluguel/delete", AluguelService.adminDelete);

        post("/admin/categoria/create", CategoriaService.adminCreate);

        post("/admin/categoria/delete", CategoriaService.adminDelete);

        get("/admin/contato/all", ContatoService.adminAll);

        get("/admin/contato/read/:id", ContatoService.adminRead);

        get("/admin/movel/all", MovelService.adminAll);

        post("/admin/movel/delete", MovelService.adminDelete);

        get("/admin/pagamento/all", PagamentoService.adminAll);

        post("/admin/pagamento/delete", PagamentoService.adminDelete);
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
        && (!hasSession || ((Usuario) session.attribute("user")).getAcesso().equals("normal")))
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