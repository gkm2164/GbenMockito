package me.gben;

import java.math.BigInteger;

public interface TestingInterface {
    String test();
    String test2(String v1, String v2);
    String test3(PolyType polyType);
    String test4(Object anyValue);

    BigInteger factorial(BigInteger n);

    BigInteger factorial(BigInteger acc, BigInteger n);
}
