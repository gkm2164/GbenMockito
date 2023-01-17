package me.gben.mocky.examples;

public class DecisionMaker {
  private final SomeSource source;

  public DecisionMaker(SomeSource someSource) {
    this.source = someSource;
  }

  public boolean isValid() {
    return !this.source.read().isEmpty();
  }
}
