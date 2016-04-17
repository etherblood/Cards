package com.etherblood.firstruleset.bot.monteCarlo;

import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.cardscontext.MatchContext;

/**
 *
 * @author Philipp
 */
public interface CommandManager {
    void executeCommand(MatchContext context, Command command);
    Command generate(EntityComponentMapReadonly bean, ValidEffectTargetsSelector bean0, MoveSelector orginalMoveSelector);
    void selectCommand(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, EntityId effect, EntityId[] targets, MoveConsumer moveConsumer);

}
