package com.etherblood.cardsswingclient;

/**
 *
 * @author Philipp
 */
public interface UpdateHandler<T> {
    void handle(T update);
}
