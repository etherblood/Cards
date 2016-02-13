package com.etherblood.cardsmatch.cardgame.client;

import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public interface Commands {
//    void surrender(MatchState match, EntityId player);
//    void endTurn(MatchState match, EntityId player);
//    void summon(MatchState match, EntityId player, EntityId minion);
//    void attack(MatchState match, EntityId player, EntityId source, EntityId target);
    void triggerEffect(MatchState match, EntityId player, EntityId effect, EntityId... targets);
}
