package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsnetworkshared.match.updates.CreateEntity;
import com.etherblood.cardsnetworkshared.match.updates.SetAttack;
import com.etherblood.cardsnetworkshared.match.updates.SetCost;
import com.etherblood.cardsnetworkshared.match.updates.SetHealth;
import com.etherblood.cardsnetworkshared.match.updates.SetProperty;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.firstruleset.logic.battle.buffs.ChargeComponent;
import com.etherblood.firstruleset.logic.battle.buffs.DivineShieldComponent;
import com.etherblood.firstruleset.logic.battle.buffs.TauntComponent;
import com.etherblood.firstruleset.logic.battle.stats.AttackComponent;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.battle.stats.ManaCostComponent;

public class IdConverterExtendedImpl extends IdConverter {
//TODO: replace this hack with proper network logic
    private final EntityComponentMapReadonly data;
    private final HumanPlayer player;

    public IdConverterExtendedImpl(EntityComponentMapReadonly data, HumanPlayer player) {
        this.data = data;
        this.player = player;
    }

    @Override
    public Long toLong(EntityId id) {
        Long longId = super.toLong(id);
        if (longId == null) {
            longId = register(id);
            NameComponent nameComponent = data.get(id, NameComponent.class);
            player.send(new CreateEntity(longId, nameComponent == null ? null : nameComponent.name));
            AttackComponent attack = data.get(id, AttackComponent.class);
            if (attack != null) {
                player.send(new SetAttack(longId, attack.attack));
            }
            ManaCostComponent cost = data.get(id, ManaCostComponent.class);
            if (cost != null) {
                player.send(new SetCost(longId, cost.mana));
            }
            HealthComponent health = data.get(id, HealthComponent.class);
            if (health != null) {
                player.send(new SetHealth(longId, health.health));
            }
            TauntComponent taunt = data.get(id, TauntComponent.class);
            if (taunt != null) {
                player.send(new SetProperty(longId, "Taunt", 1));
            }
            ChargeComponent charge = data.get(id, ChargeComponent.class);
            if (charge != null) {
                player.send(new SetProperty(longId, "Charge", 1));
            }
            DivineShieldComponent divine = data.get(id, DivineShieldComponent.class);
            if (divine != null) {
                player.send(new SetProperty(longId, "Divine Shield", 1));
            }
        }
        return longId;
    }
}
