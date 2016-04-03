package com.etherblood.cardsmatch.cardgame;

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
