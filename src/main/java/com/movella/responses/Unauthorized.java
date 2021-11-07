package com.movella.responses;

import static spark.Spark.*;

public class Unauthorized {
  public Unauthorized(spark.Response res) {
    res.header("content-type", "application/json");

    halt(401, String.format("{\"message\":\"%s\"}", "Unauthorized"));
  }
}
