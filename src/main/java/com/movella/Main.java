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
        Map<String, Object> model = new HashMap<>();
        return render(model, "/not-found");
      });

      internalServerError((req, res) -> {
        Map<String, Object> model = new HashMap<>();
        return render(model, "/error");
      });

      get("/", (req, res) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        return render(model, "/moveis");
      });

      get("/moveis", (req, res) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        return render(model, "/moveis");
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
