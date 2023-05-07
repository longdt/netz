package com.github.longdt.netz;

import org.openjdk.jmh.annotations.*;

import java.util.function.BiFunction;

@State(Scope.Benchmark)
public class MethodCall {
  private final StringConcator stringConcator = new StringConcator();
  private final BiFunction<String, String, String> genericStringConcator = new GenericStringConcator();
  private final IStringConcator iStringConcator = new InterfaceStringConcator();
  @Param({ "500" })
  private String a;
  @Param({ "1000" })
  private String b;

  @Benchmark
  public String stringConcator() {
    return stringConcator.concat(a, b);
  }

  @Benchmark
  public String genericStringConcator() {
    return genericStringConcator.apply(a, b);
  }

  @Benchmark
  public String iStringConcator() {
    return iStringConcator.concat(a, b);
  }
}
