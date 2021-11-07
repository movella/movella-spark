package com.movella.exceptions;

public class InvalidDataException extends Exception {
  public final String message;

  public InvalidDataException(String message) {
    this.message = message;
  }
}
