package com.movella.responses;

public class Success {
  final String message;

  public Success(spark.Response res, String message) {
    res.header("content-type", "application/json");

    res.status(200);
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format("{\"message\":\"%s\"}", message);
  }
}
