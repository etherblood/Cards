package com.etherblood.cardsjmeclient.match;

/**
 *
 * @author Philipp
 */
public interface UpdateHandler<T> {
    void handle(T update);
}
