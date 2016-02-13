package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.IdConverter;
import com.etherblood.cardsmasterserver.matches.internal.MatchWrapper;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.AttackComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.ManaCostComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.cardsnetworkshared.match.updates.CreateEntity;
import com.etherblood.cardsnetworkshared.match.updates.SetAttack;
import com.etherblood.cardsnetworkshared.match.updates.SetCost;
import com.etherblood.cardsnetworkshared.match.updates.SetHealth;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class HumanPlayer extends AbstractPlayer {
    private final long userId;
    private final IdConverter converter;
    private final ArrayList<MatchUpdate> updateList = new ArrayList<>();
    private int receivedUpdates = 0;

    public HumanPlayer(long userId, MatchWrapper match, EntityId player) {
        super(match, player);
        this.userId = userId;
        converter = createConverter(match.getState().data);
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
    
    private IdConverter createConverter(final EntityComponentMapReadonly data) {
        return new IdConverter() {//TODO: fix this hack
            @Override
            public Long toLong(EntityId id) {
                Long longId = super.toLong(id);
                if (longId == null) {
                    longId = register(id);
                    NameComponent nameComponent = data.get(id, NameComponent.class);
                    send(new CreateEntity(longId, nameComponent == null? null: nameComponent.name));
                    AttackComponent attack = data.get(id, AttackComponent.class);
                    if (attack != null) {
                        send(new SetAttack(longId, attack.attack));
                    }
                    ManaCostComponent cost = data.get(id, ManaCostComponent.class);
                    if (cost != null) {
                        send(new SetCost(longId, cost.mana));
                    }
                    HealthComponent health = data.get(id, HealthComponent.class);
                    if (health != null) {
                        send(new SetHealth(longId, health.health));
                    }
                }
                return longId;
            }
        };
    }
    
}
