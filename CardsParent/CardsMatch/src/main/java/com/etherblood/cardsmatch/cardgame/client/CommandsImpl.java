package com.etherblood.cardsmatch.cardgame.client;

import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.entitysystem.data.EntityId;


public class CommandsImpl implements Commands {
    private boolean loggingEnabled = true;

//    @Override
//    public void surrender(MatchState match, EntityId player) {
//        match.events.fireEvent(new SurrenderEvent(player));
//        if(loggingEnabled) {
//            System.out.println(entityToString(match.data, player) + " surrendered.");
//        }
//    }

//    @Override
//    public void endTurn(MatchState match, EntityId player) {
//        if (match.data.has(player, ItsMyTurnComponent.class)) {
//            match.events.fireEvent(new EndTurnEvent(player));
//            if(loggingEnabled) {
//                System.out.println(entityToString(match.data, player) + " ended his turn.");
//            }
//        }
//    }

//    @Override
//    public void summon(MatchState match, EntityId player, EntityId minion) {
//        EntityId owner = match.data.get(minion, OwnerComponent.class).player;
//        if (owner == player && match.data.has(owner, ItsMyTurnComponent.class) && match.data.has(minion, HandCardComponent.class)) {
//            int cost = match.data.get(minion, ManaCostComponent.class).mana;
//            match.events.fireEvent(new ManaPaymentEvent(owner, cost, new SummonEvent(minion)));
//            if(loggingEnabled) {
//                System.out.println(entityToString(match.data, player) + " cast " + entityToString(match.data, minion) + ".");
//            }
//        }
//    }

//    private final AbstractComponentFieldValueFilter<OwnerComponent> ownerFilter = OwnerComponent.createPlayerFilter(new EqualityOperator());
//    private final FilterQuery playerTauntsQuery = new FilterQuery()
//            .setBaseClass(BoardCardComponent.class)
//            .addComponentClassFilter(TauntComponent.class)
//            .addComponentFilter(ownerFilter);
//    
//    @Override
//    public void attack(MatchState match, EntityId player, EntityId source, EntityId target) {
//        EntityId opp = match.data.get(target, OwnerComponent.class).player;
//        ownerFilter.setValue(opp);
//        if (player == match.data.get(source, OwnerComponent.class).player
//                && match.data.has(player, ItsMyTurnComponent.class)
//                && opp != player
//                && match.data.has(source, BoardCardComponent.class)
//                && match.data.has(target, BoardCardComponent.class)
//                && (match.data.has(target, TauntComponent.class) || playerTauntsQuery.count(match.data) == 0)) {
//            match.events.fireEvent(new AttackEvent(source, target));
//            if(loggingEnabled) {
//                System.out.println(entityToString(match.data, player) + " attacked " + entityToString(match.data, target) + " with " + entityToString(match.data, source) + ".");
//            }
//        }
//    }

    @Override
    public void triggerEffect(MatchState match, EntityId player, EntityId effect, EntityId... targets) {
        if(match.data.has(effect, PlayerActivationTriggerComponent.class)) {
            OwnerComponent ownerComponent = match.data.get(effect, OwnerComponent.class);
            if(ownerComponent != null && ownerComponent.player == player) {
                match.events.fireEvent(new TargetedTriggerEffectEvent(effect, targets));
            }
        }
    }
    
//    private String entityToString(EntityComponentMapReadonly data, EntityId entity) {
//        NameComponent nameComponent = data.get(entity, NameComponent.class);
//        if(nameComponent != null) {
//            return nameComponent.name + entity.toString();
//        }
//        return entity.toString();
//    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

}
