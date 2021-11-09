package com.movella.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.codec.digest.DigestUtils;

public class FunctionUtils {
  public static String sha256hex(String string) {
    return DigestUtils.sha256Hex(String.format("%s%s", string, System.getenv("SALT")));
  }

  public static JsonObject get(String uri) throws Exception {
    final HttpClient client = HttpClient.newHttpClient();
    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

    final HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

    final String body = response.body();

    if (response.statusCode() >= 400)
      // TODO fazer erro mais expressivo depois
      throw new Exception(Localization.invalidCep);

    return JsonParser.parseString(body).getAsJsonObject();
  }
}