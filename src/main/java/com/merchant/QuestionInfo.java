package com.merchant;

import java.util.LinkedList;
import java.util.List;

public class QuestionInfo {

  /**
   * Question or rule string
   */
  private String source;

  /**
   * The origin symbols in the input source
   */
  private List<String> originalSymbols = new LinkedList<>();

  /**
   * The translated symbols according to the rules
   */
  private List<RomanNumeral> translatedSymbols = new LinkedList<>();

    /**
     * The digit number of translatedSymbols present;
     */
  private int digit;

  /**
   * Record the mentioned metal or dirt in input source
   */
  private String metalOrDirt;

  /**
   * Record the credits of the mentioned metal
   */
  private float credits;

  public QuestionInfo(String src) {
    this.source = src;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public List<String> getOriginalSymbols() {
    return originalSymbols;
  }

  public void setOriginalSymbols(List<String> originalSymbols) {
    this.originalSymbols = originalSymbols;
  }

  public List<RomanNumeral> getTranslatedSymbols() {
    return translatedSymbols;
  }

  public void setTranslatedSymbols(List<RomanNumeral> translatedSymbols) {
    this.translatedSymbols = translatedSymbols;
  }

  public String getMetalOrDirt() {
    return metalOrDirt;
  }

  public void setMetalOrDirt(String metalOrDirt) {
    this.metalOrDirt = metalOrDirt;
  }

  public float getCredits() {
    return credits;
  }

  public void setCredits(float credits) {
    this.credits = credits;
  }

  public int getDigit() {
    return digit;
  }

  public void setDigit(int digit) {
    this.digit = digit;
  }
}
