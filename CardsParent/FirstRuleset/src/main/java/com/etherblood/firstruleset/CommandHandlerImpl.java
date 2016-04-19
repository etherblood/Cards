package com.etherblood.firstruleset;

import com.etherblood.cardsmatch.cardgame.CommandHandler;
import com.etherblood.cardsmatchapi.IllegalCommandException;
import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.cardscontext.Autowire;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.version.VersionedEntityComponentMapImpl;
import com.etherblood.eventsystem.GameEventQueueImpl;
import com.etherblood.firstruleset.logic.effects.TargetedTriggerEffectEvent;
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
        int checkpoint = data.getVersion();
        try {
            notifyBots(source, targets);//notify first to ensure internal events occur in correct order
            events.fireEvent(new TargetedTriggerEffectEvent(player, source, targets));
            events.handleEvents();
        } catch (IllegalCommandException e) {
            System.out.println("illegal command.");
            if(data.getVersion() != checkpoint) {
                System.err.println("illegal command modified match data, this should not have happened, rolling back...");
                data.revertTo(checkpoint);
            }
            if(!events.isEmpty()) {
                System.err.println("illegal command invalidated eventQueue, clearing eventQueue...");
                events.clear();
            }
            if(!events.getDataStack().isEmpty()) {
                System.err.println("illegal command invalidated eventDataStack, clearing eventDataStack...");
                events.getDataStack().clear();
            }
            throw e;
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
