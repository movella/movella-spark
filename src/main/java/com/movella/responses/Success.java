package com.movella.responses;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Success {
  String message = null;
  JsonObject jsonObject = null;
  JsonArray jsonArray = null;

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

  public Success(spark.Response res, JsonArray jsonArray) {
    res.header("content-type", "application/json");

    res.status(200);
    this.jsonArray = jsonArray;
  }

  @Override
  public String toString() {
    if (message != null)
      return String.format("{\"message\":\"%s\"}", message);
    else if (jsonObject != null)
      return jsonObject.toString();
    else
      return jsonArray.toString();
  }
}
