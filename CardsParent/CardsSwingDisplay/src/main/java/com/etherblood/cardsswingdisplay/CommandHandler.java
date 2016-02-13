package com.etherblood.cardsswingdisplay;

/**
 *
 * @author Philipp
 */
public interface CommandHandler {
    void triggerEffect(long effect, long... targets);
}
