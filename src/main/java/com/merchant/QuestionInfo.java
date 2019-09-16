package com.merchant;

import java.util.LinkedList;
import java.util.List;

/**
 * @author created by zzz at 2019/9/2 15:24
 */
public class QuestionInfo {

  /**
   * Question or rule string
   */
  private String source;

  /**
   * The origin symbols in the input source
   */
  private List<String> originalWords = new LinkedList<>();

  /**
   * The translated symbols according to the rules
   */
  private List<RomanNumeral> romansFromWords = new LinkedList<>();

  /**
   * The arabic number of romansFromWords present;
   */
  private Integer arabicFromRomans;

  private List<RomanNumeral> romansFromArabic = new LinkedList<>();

  private Integer arabic;

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

  public List<String> getOriginalWords() {
    return originalWords;
  }

  public void setOriginalWords(List<String> originalWords) {
    this.originalWords = originalWords;
  }

  public List<RomanNumeral> getRomansFromWords() {
    return romansFromWords;
  }

  public void setRomansFromWords(List<RomanNumeral> romansFromWords) {
    this.romansFromWords = romansFromWords;
  }

  public Integer getArabicFromRomans() {
    return arabicFromRomans;
  }

  public void setArabicFromRomans(Integer arabicFromRomans) {
    this.arabicFromRomans = arabicFromRomans;
  }

  public List<RomanNumeral> getRomansFromArabic() {
    return romansFromArabic;
  }

  public void setRomansFromArabic(List<RomanNumeral> romansFromArabic) {
    this.romansFromArabic = romansFromArabic;
  }

  public Integer getArabic() {
    return arabic;
  }

  public void setArabic(Integer arabic) {
    this.arabic = arabic;
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
}
