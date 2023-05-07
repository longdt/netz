package com.github.longdt.netz;

public class InterfaceStringConcator implements IStringConcator {
  @Override
  public String concat(String a, String b) {
    return a + b;
  }
}
