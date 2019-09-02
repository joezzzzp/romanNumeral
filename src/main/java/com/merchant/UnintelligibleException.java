package com.merchant;

/**
 * Custom Exception
 */
public class UnintelligibleException extends RuntimeException {

  public UnintelligibleException() {
    super("I have no idea what you are talking about");
  }

  public UnintelligibleException(String message) {
    super(message);
  }

}
