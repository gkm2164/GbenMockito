package me.gben.mocky;

/**
 * Any mocked class would implement this interface.
 */
public interface MockyInterceptable {
  void setInterceptor(MockyInterceptor interceptor);

  MockyInterceptor getInterceptor();
}
