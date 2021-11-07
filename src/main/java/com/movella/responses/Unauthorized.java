package com.movella.responses;

import static spark.Spark.*;

import com.movella.utils.Localization;

public class Unauthorized {
  public Unauthorized(spark.Response res) {
    res.header("content-type", "application/json");

    halt(401, String.format("{\"message\":\"%s\"}", Localization.unauthorized));
  }
}
