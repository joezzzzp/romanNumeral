package com.merchant;

import java.util.*;

/**
 * Each item represents a roman numeral
 * records the String symbol, value and roman numerals can subtract it
 */
public enum RomanNumeral {

  M("M", 1000, 3, Collections.emptyList()),
  D("D", 500, 1, Collections.emptyList()),
  C("C", 100, 3, Arrays.asList(D, M)),
  L("L", 50, 1, Collections.emptyList()),
  X("X", 10, 3, Arrays.asList(L, C)),
  V("V", 5, 1, Collections.emptyList()),
  I("I", 1, 3, Arrays.asList(V, X)),
  NONE("", 0, 1, Collections.emptyList());

  public final String symbol;
  public final int value;
  public final int maxRepeatTimes;
  private final List<RomanNumeral> canBeSubtractedBy;

  RomanNumeral(String symbol, int value, int maxRepeatTimes, List<RomanNumeral> canBeSubtractedBy) {
    this.symbol = symbol;
    this.value = value;
    this.maxRepeatTimes = maxRepeatTimes;
    this.canBeSubtractedBy = canBeSubtractedBy;
  }

  public boolean canBeSubtractedBy(RomanNumeral r) {
    return canBeSubtractedBy.contains(r);
  }

  public int compare(RomanNumeral r) {
    return Integer.compare(value, r.value);
  }

  public int getMaxRepeatTimes() {
    return maxRepeatTimes;
  }

  public static RomanNumeral parse(String s) {
    return Arrays.stream(values()).filter(r -> r.symbol.equals(s)).findAny().orElse(NONE);
  }
}
