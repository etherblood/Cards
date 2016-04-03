package com.etherblood.cardsmatch.cardgame;

/**
 *
 * @author Philipp
 */
public interface NetworkPlayer<N> {
    void send(N message);
}
