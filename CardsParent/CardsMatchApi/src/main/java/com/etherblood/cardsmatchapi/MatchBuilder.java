package com.etherblood.cardsmatchapi;

/**
 *
 * @author Philipp
 */
public interface MatchBuilder<A, U> {
    HumanProxy<A, U> createHuman(PlayerDefinition def);
    BotProxy createBot(PlayerDefinition def);
    SpectatorProxy<U> getSpectator();
    StateProxy getStateTracker();
    void start();
}
