package com.github.longdt.netz.http.exception;

public class NoStackTraceException extends RuntimeException {
  public NoStackTraceException(String message) {
    super(message, null, false, false);
  }

  public NoStackTraceException(String message, Throwable cause) {
    super(message, cause, false, false);
  }
}
