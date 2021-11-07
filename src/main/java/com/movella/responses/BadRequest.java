package com.movella.responses;

import static spark.Spark.*;

public class BadRequest {
  public BadRequest(spark.Response res, String message) {
    res.header("content-type", "application/json");

    halt(400, String.format("{\"message\":\"%s\"}", message));
  }
}
