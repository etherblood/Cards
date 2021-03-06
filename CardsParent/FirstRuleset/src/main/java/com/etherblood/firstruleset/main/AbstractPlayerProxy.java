package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.CardsContext;
import com.etherblood.cardsmatch.cardgame.CommandHandler;
import com.etherblood.cardsmatchapi.PlayerDefinition;
import com.etherblood.cardsmatchapi.PlayerProxy;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class AbstractPlayerProxy implements PlayerProxy {
    private final CardsContext context;
    private final PlayerDefinition definition;
    private EntityId entity;

    public AbstractPlayerProxy(CardsContext context, PlayerDefinition definition) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public PlayerDefinition getPlayerDefinition() {
        return definition;
    }

    public EntityId getEntity() {
        return entity;
    }

    public void setEntity(EntityId entity) {
        this.entity = entity;
    }
    
    public void apply(EntityId source, EntityId... targets) {
        CommandHandler commands = context.getBean(CommandHandler.class);
        commands.handleCommand(entity, source, targets);
    }

    public CardsContext getContext() {
        return context;
    }

}
