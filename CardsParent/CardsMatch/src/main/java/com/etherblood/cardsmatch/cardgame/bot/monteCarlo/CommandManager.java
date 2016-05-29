package com.etherblood.cardsmatch.cardgame.bot.monteCarlo;

import com.etherblood.cardsmatch.cardgame.ValidEffectTargetsSelector;
import com.etherblood.cardsmatch.cardgame.bot.commands.Command;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.cardscontext.CardsContext;

/**
 *
 * @author Philipp
 */
public interface CommandManager {
    void executeCommand(CardsContext context, Command command);
    Command generate(EntityComponentMapReadonly bean, ValidEffectTargetsSelector targetSelector, MoveSelector orginalMoveSelector);
    void selectCommand(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, EntityId effect, EntityId[] targets, MoveConsumer moveConsumer);
//    void validate(EntityComponentMapReadonly data, ValidEffectTargetsSelector targetSelector, EntityId effect, EntityId[] targets);
}
