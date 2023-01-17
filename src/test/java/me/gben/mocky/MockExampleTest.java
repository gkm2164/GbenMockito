package me.gben.mocky;

import me.gben.mocky.examples.DecisionMaker;
import me.gben.mocky.examples.SomeSource;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicBoolean;

import static me.gben.mocky.Mocky.mock;
import static me.gben.mocky.Mocky.when;
import static me.gben.mocky.StartStubbing.doAnswer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    while(st.hasMoreTokens()) {
      assertEquals("Here's next token!", st.nextToken());
      when(st.hasMoreTokens()).thenReturn(false);
    }

    assertFalse(st.hasMoreTokens());
  }

  @Test
  public void custom_library_test() {
    SomeSource someSource = mock(SomeSource.class);
    DecisionMaker dm = new DecisionMaker(someSource);

    when(someSource.read()).thenReturn("Mock Data");

    assertTrue(dm.isValid());
  }
}
