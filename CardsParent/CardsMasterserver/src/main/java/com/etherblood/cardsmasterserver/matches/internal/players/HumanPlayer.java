package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.IdConverter;
import com.etherblood.cardsmasterserver.matches.internal.IdConverterExtendedImpl;
import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.entitysystem.data.EntityId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class HumanPlayer extends AbstractPlayer {
    private final long userId;
    private IdConverter converter;
    private final ArrayList<MatchUpdate> updateList = new ArrayList<>();
    private int receivedUpdates = 0;

    public HumanPlayer(long userId, EntityId player) {
        super(player);
        this.userId = userId;
        assert player != null;
    }

    public void triggerEffect(long source, long... targets) {
        triggerEffect(converter.fromLong(source), converter.fromLongs(targets));
    }
    
    public void send(MatchUpdate update) {
        updateList.add(update);
//        connection.send(new CardsMessage(update));
    }
    
    public void discardLatestUpdates() {
        for (int i = updateList.size() - 1; i >= receivedUpdates; i--) {
            updateList.remove(i);
        }
    }
    
    public List<MatchUpdate> getLatestUpdates() {
        ArrayList<MatchUpdate> result = new ArrayList<>();
        while (receivedUpdates < updateList.size()) {            
            result.add(updateList.get(receivedUpdates++));
        }
        return result;
    }
    
    public void resetReceivedUpdates() {
        receivedUpdates = 0;
    }

    public long getUserId() {
        return userId;
    }

    public IdConverter getConverter() {
        return converter;
    }

    @Override
    public void setMatch(MatchContextWrapper match) {
        super.setMatch(match);
        converter = new IdConverterExtendedImpl(match.getData(), this);//createConverter(match.getData());
    }
    
//    private IdConverter createConverter(final EntityComponentMapReadonly data) {
//        return new IdConverter() {//TODO: fix this hack
//            @Override
//            public Long toLong(EntityId id) {
//                Long longId = super.toLong(id);
//                if (longId == null) {
//                    longId = register(id);
//                    NameComponent nameComponent = data.get(id, NameComponent.class);
//                    send(new CreateEntity(longId, nameComponent == null? null: nameComponent.name));
//                    AttackComponent attack = data.get(id, AttackComponent.class);
//                    if (attack != null) {
//                        send(new SetAttack(longId, attack.attack));
//                    }
//                    ManaCostComponent cost = data.get(id, ManaCostComponent.class);
//                    if (cost != null) {
//                        send(new SetCost(longId, cost.mana));
//                    }
//                    HealthComponent health = data.get(id, HealthComponent.class);
//                    if (health != null) {
//                        send(new SetHealth(longId, health.health));
//                    }
//                    TauntComponent taunt = data.get(id, TauntComponent.class);
//                    if (taunt != null) {
//                        send(new SetProperty(longId, "Taunt", 1));
//                    }
//                    ChargeComponent charge = data.get(id, ChargeComponent.class);
//                    if (charge != null) {
//                        send(new SetProperty(longId, "Charge", 1));
//                    }
//                    DivineShieldComponent divine = data.get(id, DivineShieldComponent.class);
//                    if (divine != null) {
//                        send(new SetProperty(longId, "Divine Shield", 1));
//                    }
//                }
//                return longId;
//            }
//        };
//    }
    
}
