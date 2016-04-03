package com.etherblood.firstruleset.logic;

import com.etherblood.cardsmatch.cardgame.CommandHandler;
import com.etherblood.cardsmatch.cardgame.IllegalCommandException;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardsmatch.cardgame.components.player.ItsMyTurnComponent;
import com.etherblood.cardsmatch.cardgame.match.Autowire;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.version.VersionedEntityComponentMapImpl;
import com.etherblood.eventsystem.GameEventQueueImpl;
import com.etherblood.firstruleset.logic.effects.TargetedTriggerEffectEvent;
import com.etherblood.firstruleset.logic.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class CommandHandlerImpl implements  CommandHandler {

    private final List<Bot> bots = new ArrayList<>();
    @Autowire
    private VersionedEntityComponentMapImpl data;
    @Autowire
    private GameEventQueueImpl events;
    
    @Override
    public void handleCommand(EntityId player, EntityId source, EntityId... targets) {
        if (data.has(player, ItsMyTurnComponent.class) && data.has(source, PlayerActivationTriggerComponent.class)) {
            OwnerComponent ownerComponent = data.get(source, OwnerComponent.class);
            if (ownerComponent != null && ownerComponent.player.equals(player)) {
                int checkpoint = data.getVersion();
                try {
                    notifyBots(source, targets);//notify first to ensure internal events occur in correct order
                    events.fireEvent(new TargetedTriggerEffectEvent(source, targets));
                    events.handleEvents();
                } catch (IllegalCommandException e) {
                    System.out.println("An exception occurred during handling of effect trigger, match will be rolled back.");
                    data.revertTo(checkpoint);
                    throw e;
                }
            }
        }
    }
    
    public void registerBot(Bot bot) {
        bots.add(bot);
    }

    private void notifyBots(EntityId source, EntityId[] targets) {
        for (Bot bot : bots) {
            bot.moveNotification(source, targets);
        }
    }

}
