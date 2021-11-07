package com.movella.responses;

import static spark.Spark.*;

import com.movella.utils.Localization;

public class BadRequest {
  public BadRequest(spark.Response res) {
    res.header("content-type", "application/json");

    halt(400, String.format("{\"message\":\"%s\"}", Localization.badRequest));
  }

  public BadRequest(spark.Response res, String message) {
    res.header("content-type", "application/json");

    halt(400, String.format("{\"message\":\"%s\"}", message));
  }
}
