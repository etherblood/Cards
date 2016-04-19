package com.etherblood.cardsmatchapi;

/**
 *
 * @author Philipp
 */
public interface StateProxy {
    boolean isGameOver();
    PlayerProxy getCurrentPlayer();
    PlayerResult getResult(PlayerProxy player);
}
