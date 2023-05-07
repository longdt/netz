package com.github.longdt.netz;

import java.util.function.BiFunction;

public class GenericStringConcator implements BiFunction<String, String, String> {
  @Override
  public String apply(String s, String s2) {
    return s + s2;
  }
}
