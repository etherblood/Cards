package com.etherblood.cardsmatchapi;

/**
 *
 * @author Philipp
 */
public class IllegalCommandException extends RuntimeException {

    public IllegalCommandException(String message) {
        super(message);
    }

    public IllegalCommandException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
