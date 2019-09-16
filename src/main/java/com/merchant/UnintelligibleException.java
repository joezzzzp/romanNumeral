package com.merchant;

/**
 * Custom Exception
 * @author created by zzz at 2019/9/2 14:26
 */
public class UnintelligibleException extends RuntimeException {

    public UnintelligibleException() {
        super("I have no idea what you are talking about");
    }

    public UnintelligibleException(String message) {
        super(message);
    }

}
