package com.etherblood.cardsjmeclient.events;

/**
 *
 * @author Philipp
 */
public class ExceptionEvent {
    private final Throwable throwable;

    public ExceptionEvent(Throwable throwable) {
        this.throwable = throwable;
    }
}
