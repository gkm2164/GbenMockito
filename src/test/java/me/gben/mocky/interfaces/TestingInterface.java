package me.gben.mocky.interfaces;

import me.gben.PolyType;

import java.math.BigInteger;

public interface TestingInterface {
    String test();
    String test2(String v1, String v2);
    String test3(PolyType polyType);
    String test4(Object anyValue);

    Integer factorial(Integer n);

    Integer factorial(Integer acc, Integer n);

    String numberCompare(Integer ge);
}
