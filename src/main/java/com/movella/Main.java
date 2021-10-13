package com.movella;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

  public static void main(String[] args) {
    try {
      final int port = getHerokuAssignedPort();

      port(port);

      staticFiles.location("/public");

      notFound((req, res) -> {
        return render(new HashMap<>(), "/not-found");
      });

      internalServerError((req, res) -> {
        return render(new HashMap<>(), "/error");
      });

      get("/", (req, res) -> {
        return render(new HashMap<>(), "/moveis");
      });

      get("/moveis", (req, res) -> {
        return render(new HashMap<>(), "/moveis");
      });

      get("/sobre", (req, res) -> {
        return render(new HashMap<>(), "/sobre");
      });

      get("/contato", (req, res) -> {
        return render(new HashMap<>(), "/contato");
      });

      get("/entrar", (req, res) -> {
        return render(new HashMap<>(), "/entrar");
      });

      get("/criarconta", (req, res) -> {
        return render(new HashMap<>(), "/criarconta");
      });

      System.out.println(String.format("listening on port %d", port));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  static int getHerokuAssignedPort() {
    String port = new ProcessBuilder().environment().get("PORT");
    return port == null ? 80 : Integer.parseInt(port);
  }

  public static String render(Map<String, Object> model, String templatePath) {
    return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
  }
}
