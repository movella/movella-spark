package com.movella.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class FunctionUtils {
  public static String sha256hex(String string) {
    return DigestUtils.sha256Hex(String.format("%smovella748237", string));
  }
}