package com.etherblood.cardsmatch.cardgame.bot.commands;

import com.etherblood.cardsmatch.cardgame.Autowire;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.version.VersionedEntityComponentMap;
import com.etherblood.eventsystem.GameEventQueueImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class CommandManager {
    @Autowire private VersionedEntityComponentMap data;
    @Autowire private GameEventQueueImpl events;
    @Autowire private EndTurnCommandFactory endTurnFactory;
    @Autowire private SummonCommandFactory summonFactory;
    @Autowire private AttackCommandFactory attackFactory;
    
//    private final FilterQuery query = new FilterQuery().setBaseClass(PlayerActivationTriggerComponent.class);
    
    public List<Command> generateCommands(boolean includeSummons) {
        ArrayList<Command> result = new ArrayList<>();
        attackFactory.generateAttackCommands(result);
        if(includeSummons) {
            summonFactory.generateSummonCommands(result);
        }
        result.add(endTurnFactory.generateEndTurnCommand());
        return result;
    }
    
    public boolean executeCommand(EntityId commandingPlayer, Command command) {
        if(data.has(command.effect, PlayerActivationTriggerComponent.class)) {
            OwnerComponent ownerComponent = data.get(command.effect, OwnerComponent.class);
            if(ownerComponent != null && ownerComponent.player == commandingPlayer) {
                events.fireEvent(new TargetedTriggerEffectEvent(command.effect, command.targets));
                int version = data.getVersion();
                events.handleEvents();
                return data.getVersion() != version;
            }
        }
        return false;
    }
}
