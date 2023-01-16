package me.gben.mocky;

import me.gben.PolyTypeA;
import me.gben.PolyTypeB;
import me.gben.mocky.interfaces.TestingInterface;
import org.junit.Test;

import static me.gben.matchers.Matchers.and;
import static me.gben.matchers.Matchers.any;
import static me.gben.matchers.Matchers.anyInteger;
import static me.gben.matchers.Matchers.anyString;
import static me.gben.matchers.Matchers.contains;
import static me.gben.matchers.Matchers.eq;
import static me.gben.matchers.Matchers.ge;
import static me.gben.matchers.Matchers.gt;
import static me.gben.matchers.Matchers.le;
import static me.gben.matchers.Matchers.lt;
import static me.gben.matchers.Matchers.not;
import static me.gben.matchers.Matchers.or;
import static me.gben.matchers.Matchers.regex;
import static me.gben.mocky.Mocky.mock;
import static me.gben.mocky.Mocky.when;
import static me.gben.mocky.StartStubbing.doAnswer;
import static me.gben.mocky.StartStubbing.doReturn;
import static me.gben.mocky.StartStubbing.doThrow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
    public void pattern_matching_works_implement_factorial() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.factorial(eq(0))).thenReturn(1);
        doAnswer(invoke -> {
            Integer current = invoke.get(0);
            return current * ti.factorial(current - 1);
        }).when(ti).factorial(gt(0));
        doThrow(IllegalArgumentException.class).when(ti).factorial(lt(0));

        assertEquals(Integer.valueOf(120), ti.factorial(5));
        assertThrows(IllegalArgumentException.class, () -> ti.factorial(-1));
    }

    @Test
    public void throw_exception_mock() {
        TestingInterface ti = mock(TestingInterface.class);

        doThrow(RuntimeException.class).when(ti).test();
        assertThrows(RuntimeException.class, ti::test);
    }

    @Test
    public void pattern_matching_tail_recursion() {
        TestingInterface ti = mock(TestingInterface.class);

        doAnswer(invoke -> invoke.get(0)).when(ti).factorial(gt(0), eq(0));
        doAnswer(invoke -> {
            Integer acc = invoke.get(0);
            Integer elem = invoke.get(1);
            return ti.factorial(acc * elem, elem - 1);
        }).when(ti).factorial(gt(0), gt(0));

        assertEquals(Integer.valueOf(120), ti.factorial(1, 5));
    }

    @Test
    public void numeric_comparator_mock_test() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.numberCompare(lt(30))).thenReturn("Less than 30!");
        when(ti.numberCompare(ge(30))).thenReturn("Greater or Equal 30!");

        assertEquals("Less than 30!", ti.numberCompare(29));
        assertEquals("Greater or Equal 30!", ti.numberCompare(31));
    }

    @Test
    public void numeric_comparator_with_throw_test() {
        TestingInterface ti = mock(TestingInterface.class);

        doThrow(IllegalArgumentException.class).when(ti).numberCompare(le(0));
        doAnswer(invoc -> invoc.get(0).toString()).when(ti).numberCompare(gt(0));

        assertThrows(IllegalArgumentException.class, () -> ti.numberCompare(-1));
        assertEquals("1", ti.numberCompare(1));
    }

    @Test
    public void argument_exact_match_test() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.test4("SomeValue")).thenReturn("Match SomeValue");
        when(ti.test4(10)).thenReturn("Match 10");

        assertEquals(ti.test4("SomeValue"), "Match SomeValue");
        assertEquals(ti.test4(10), "Match 10");
    }

    @Test
    public void invalid_matcher_usages_throw_error() {
        TestingInterface ti = mock(TestingInterface.class);

        assertThrows(IllegalStateException.class, () -> when(ti.test2("SomeValue", eq("Other"))));
    }

    @Test
    public void string_contains_mock_test() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.test4(contains("A"))).thenReturn("PartA");
        when(ti.test4(contains("B"))).thenReturn("PartB");

        assertEquals("PartA", ti.test4("ACDE"));
        assertEquals("PartB", ti.test4("BCDE"));
    }

    @Test
    public void test_with_conditional() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.test4(or(lt(-10), gt(10))))
                .thenReturn("Out range!");
        when(ti.test4(and(ge(-10), le(10))))
                .thenReturn("In range!");
        when(ti.test4(not(anyInteger())))
                .thenReturn("Not an integer!");


        assertEquals("Out range!", ti.test4(100));
        assertEquals("In range!", ti.test4(0));
        assertEquals("Not an integer!", ti.test4("AnyValue"));
    }

    @Test
    public void test_with_complex_condition() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.test4(or(and(contains("A"), contains("B")), and(contains("C"), contains("D")))))
                .thenReturn("Complex condition");
        when(ti.test4(any(Number.class)))
                .thenReturn("Number type is given");

        assertEquals("Complex condition", ti.test4("AB"));
        assertEquals("Complex condition", ti.test4("CD"));
        assertEquals("Number type is given", ti.test4(100));
    }

    @Test
    public void test_with_regex() {
        TestingInterface ti = mock(TestingInterface.class);

        when(ti.test4(or(regex("3+"), regex("4+")))).thenReturn("Matches with regex!");

        assertEquals("Matches with regex!", ti.test4("3333"));
        assertEquals("Matches with regex!", ti.test4("4444"));
    }
}