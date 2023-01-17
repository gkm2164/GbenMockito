package me.gben.mocky.examples;

public interface TestingInterface {
  String test();
  String test2(String v1, String v2);
  String test3(PolyType polyType);
  String test4(Object anyValue);

  Integer factorial(Integer n);

  Integer factorial(Integer acc, Integer n);

  String numberCompare(Integer ge);
}
