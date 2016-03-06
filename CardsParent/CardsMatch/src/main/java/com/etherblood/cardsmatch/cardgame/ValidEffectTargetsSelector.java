package com.etherblood.cardsmatch.cardgame;

import com.etherblood.entitysystem.data.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public interface ValidEffectTargetsSelector {
    List<EntityId> selectTargets(EntityId effect);
}
