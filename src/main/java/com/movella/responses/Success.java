package com.movella.responses;

import com.google.gson.JsonObject;

public class Success {
  String message = null;
  JsonObject jsonObject = null;

  public Success(spark.Response res, String message) {
    res.header("content-type", "application/json");

    res.status(200);
    this.message = message;
  }

  public Success(spark.Response res, JsonObject jsonObject) {
    res.header("content-type", "application/json");

    res.status(200);
    this.jsonObject = jsonObject;
  }

  @Override
  public String toString() {
    return message != null ? String.format("{\"message\":\"%s\"}", message) : jsonObject.toString();
  }
}
