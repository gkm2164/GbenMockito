package me.gben.mocky;

import me.gben.PolyTypeA;
import me.gben.PolyTypeB;
import me.gben.TestingInterface;
import org.junit.Test;

import java.math.BigInteger;

import static me.gben.matchers.Matchers.*;
import static me.gben.mocky.Mocky.mock;
import static me.gben.mocky.Mocky.when;
import static me.gben.mocky.StartStubbing.doAnswer;
import static me.gben.mocky.StartStubbing.doReturn;
import static org.junit.Assert.assertEquals;

public class MockyTest {
    @Test
    public void empty_parameter_stubbing_test() {
        TestingInterface ti = mock(TestingInterface.class);
        when(ti.test()).thenReturn("Hello World!");

        assertEquals("Hello World!", ti.test());
    }

    @Test
    public void raw_value_pattern_matching_test() {
        TestingInterface ti = mock(TestingInterface.class);
        when(ti.test2("Hello", "World")).thenReturn("1");
        when(ti.test2("Goodbye", "World")).thenReturn("2");

        assertEquals("1", ti.test2("Hello", "World"));
        assertEquals("2", ti.test2("Goodbye", "World"));
    }

    @Test
    public void pattern_matching_with_matcher_test() {
        TestingInterface ti = mock(TestingInterface.class);
        when(ti.test2(any(), eq("Something"))).thenReturn("Match with Something");
        when(ti.test2(any(), eq("Other"))).thenReturn("Match with Other");
        when(ti.test3(any(PolyTypeA.class))).thenReturn("PolyType A");
        when(ti.test3(any(PolyTypeB.class))).thenReturn("PolyType B");

        assertEquals(ti.test2("Hello", "Something"), "Match with Something");
        assertEquals(ti.test2("Hello", "Other"), "Match with Other");
        assertEquals(ti.test3(mock(PolyTypeA.class)), "PolyType A");
        assertEquals(ti.test3(mock(PolyTypeB.class)), "PolyType B");

        doReturn("Other").when(ti).test();

        assertEquals(ti.test(), "Other");

        doReturn("PolyType PolyType A").when(ti)
                .test3(any(PolyTypeA.class));

        assertEquals(ti.test3(mock(PolyTypeA.class)), "PolyType PolyType A");
        assertEquals(ti.test3(mock(PolyTypeB.class)), "PolyType B");

        PolyTypeA polyTypeA = mock(PolyTypeA.class);

        when(ti.test3(polyTypeA)).thenReturn("Exactly that value");

        assertEquals("Exactly that value", ti.test3(polyTypeA));

        when(ti.test3(polyTypeA)).thenReturn("Exactly that value2");
        when(ti.test3(polyTypeA)).thenReturn("Exactly that value3");

        assertEquals("Exactly that value3", ti.test3(polyTypeA));
    }

    @Test
    public void multiple_match_priority_test() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.test4(anyString())).thenReturn("This is String!");
        when(ti.test4(anyInteger())).thenReturn("This is Integer!");
        when(ti.test4(any())).thenReturn("Fallback mock");

        assertEquals("This is String!", ti.test4("SomeString"));
        assertEquals("This is Integer!", ti.test4(30));
        assertEquals("Fallback mock", ti.test4(30L));
    }

    @Test
    public void pattern_matching_works() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.factorial(eq(BigInteger.ZERO))).thenReturn(BigInteger.ONE);
        doAnswer(invoke -> {
            BigInteger current = invoke.get(0);
            return current.multiply(ti.factorial(current.subtract(BigInteger.ONE)));
        }).when(ti).factorial(any(BigInteger.class));

        assertEquals(BigInteger.valueOf(120), ti.factorial(BigInteger.valueOf(5)));
    }

    @Test
    public void pattern_matching_tail_recursion() {
        TestingInterface ti = mock(TestingInterface.class);

        doAnswer(invoke -> invoke.get(0)).when(ti).factorial(any(), eq(BigInteger.ZERO));
        doAnswer(invoke -> {
            BigInteger acc = invoke.get(0);
            BigInteger elem = invoke.get(1);
            return ti.factorial(acc.multiply(elem), elem.subtract(BigInteger.ONE));
        }).when(ti).factorial(any(), any());

        assertEquals(BigInteger.valueOf(120), ti.factorial(BigInteger.ONE, BigInteger.valueOf(5)));
    }
}