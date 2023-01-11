package me.gben.mocky;

import me.gben.PolyTypeA;
import me.gben.PolyTypeB;
import me.gben.TestingInterface;
import org.junit.Test;

import static me.gben.matchers.Matchers.any;
import static me.gben.matchers.Matchers.eq;
import static me.gben.mocky.Mocky.mock;
import static me.gben.mocky.Mocky.when;
import static me.gben.mocky.StartStubbing.doReturn;
import static org.junit.Assert.assertEquals;

public class MockyTest {
    @Test
    public void basic_mocking_test() {
        TestingInterface ti = mock(TestingInterface.class);
        when(ti.test()).thenReturn("Hello World!");
        when(ti.test2(any(), eq("Something"))).thenReturn("Match with Something");
        when(ti.test2(any(), eq("Other"))).thenReturn("Match with Other");
        when(ti.test3(any(PolyTypeA.class))).thenReturn("PolyType A");
        when(ti.test3(any(PolyTypeB.class))).thenReturn("PolyType B");

        assertEquals(ti.test(), "Hello World!");
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
    }
}