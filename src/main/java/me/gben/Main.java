package me.gben;

import static me.gben.mocky.Matchers.*;
import static me.gben.mocky.Mocky.mock;
import static me.gben.mocky.Mocky.when;
import static me.gben.mocky.StubbingStart.doReturn;

public class Main {
    public static void main(String[] args) {
        TestingInterface ti = mock(TestingInterface.class);
        when(ti.test()).thenReturn("Hello World!");
        when(ti.test2(any(), eq("Something"))).thenReturn("Match with Something");
        when(ti.test2(any(), eq("Other"))).thenReturn("Match with Other");
        when(ti.test3(any(PolyTypeA.class))).thenReturn("PolyType A");
        when(ti.test3(any(PolyTypeB.class))).thenReturn("PolyType B");

        System.out.println("=== Stub done. Now testing ===");

        System.out.println(ti.test());
        System.out.println(ti.test2("Hello", "Something"));
        System.out.println(ti.test2("Hello", "Other"));
        System.out.println(ti.test3(mock(PolyTypeA.class)));
        System.out.println(ti.test3(mock(PolyTypeB.class)));

        doReturn("Other").when(ti).test();

        System.out.println(ti.test());

        doReturn("PolyType PolyType A").when(ti)
                .test3(any(PolyTypeA.class));

        System.out.println(ti.test3(mock(PolyTypeA.class)));
        System.out.println(ti.test3(mock(PolyTypeB.class)));

    }
}