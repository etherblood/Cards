package com.etherblood.cardsjmeclient.match;

/**
 *
 * @author Philipp
 */
public interface CommandHandler {
    void triggerEffect(long effect, long... targets);
}
