package me.gben.mocky;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicBoolean;

import static me.gben.mocky.Mocky.mock;
import static me.gben.mocky.Mocky.when;
import static me.gben.mocky.StartStubbing.doAnswer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MockExampleTest {
    @Test
    public void native_interface_mocking_test() throws IOException {
        AtomicBoolean isClosed = new AtomicBoolean(false);

        try (Closeable cs = mock(Closeable.class)) {
            doAnswer((inv) -> {
                isClosed.set(true);
                return null;
            }).when(cs).close();
        }

        assertTrue(isClosed.get());
    }

    @Test
    public void native_library_mock_test() {
        StringTokenizer st = mock(StringTokenizer.class);

        when(st.nextToken()).thenReturn("Here's next token!");
        when(st.hasMoreTokens()).thenReturn(true);
        assertEquals("Here's next token!", st.nextToken());
    }
}
