package com.github.longdt.netz.http.exception;

public class IncompleteReadException extends NoStackTraceException {
  public static final IncompleteReadException INSTANCE = new IncompleteReadException();

  private IncompleteReadException() {
    super("Incomplete Read");
  }
}
