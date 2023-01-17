package me.gben.matchers;

import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import org.intellij.lang.annotations.Language;

@EqualsAndHashCode
class StringRegexMatcher implements MatcherDetail<String> {
  private final Pattern pattern;

  public StringRegexMatcher(@Language("RegExp") String regex) {
    this.pattern = Pattern.compile(regex);
  }

  @Override
  public boolean test(Object value) {
    if (value == null) {
      return false;
    } else if (!(value instanceof String)) {
      return false;
    }

    return pattern.matcher((String) value).matches();
  }
}
