package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.firstruleset.logic.battle.hero.HeroComponent;
import com.etherblood.firstruleset.logic.battle.stats.HealthComponent;
import com.etherblood.firstruleset.logic.cardZones.components.BoardCardComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.firstruleset.logic.attack.AttackEvent;
import com.etherblood.firstruleset.logic.attack.systems.ApplyAttackSystem;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.GraveyardAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.HandAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.HandDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.systems.BoardAttachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.BoardDetachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.GraveyardAttachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.HandAttachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.HandDetachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.attackAbility.AttachSummoningSicknessSystem;
import com.etherblood.firstruleset.logic.damage.SetDivineShieldEvent;
import com.etherblood.firstruleset.logic.damage.systems.SetDivineShieldSystem;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
import com.etherblood.firstruleset.logic.entities.DeleteEntityEvent;
import com.etherblood.firstruleset.logic.entities.systems.AttachTemplateSystem;
import com.etherblood.firstruleset.logic.entities.systems.DeleteEntitySystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.EndMatchSystem;
import com.etherblood.cardsnetworkshared.match.misc.CardZone;
import com.etherblood.firstruleset.logic.mana.SetManaEvent;
import com.etherblood.firstruleset.logic.mana.systems.SetManaSystem;
import com.etherblood.firstruleset.logic.setHealth.SetHealthEvent;
import com.etherblood.firstruleset.logic.setHealth.systems.SetHealthSystem;
import com.etherblood.firstruleset.logic.setOwner.SetOwnerEvent;
import com.etherblood.firstruleset.logic.setOwner.systems.SetOwnerSystem;
import com.etherblood.firstruleset.logic.startTurn.RemoveSummonSicknessEvent;
import com.etherblood.firstruleset.logic.startTurn.systems.RemoveSummonSicknessSystem;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.cardsnetworkshared.match.updates.AttachEffect;
import com.etherblood.cardsnetworkshared.match.updates.AttackUpdate;
import com.etherblood.cardsnetworkshared.match.updates.DetachEffect;
import com.etherblood.cardsnetworkshared.match.updates.GameOver;
import com.etherblood.cardsnetworkshared.match.updates.SetCost;
import com.etherblood.cardsnetworkshared.match.updates.SetHealth;
import com.etherblood.cardsnetworkshared.match.updates.SetOwner;
import com.etherblood.cardsnetworkshared.match.updates.SetProperty;
import com.etherblood.cardsnetworkshared.match.updates.SetZone;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.FilterQuery;
import java.util.HashMap;

/**
 *
 * @author Philipp
 */
public class ClientUpdaterFactory {
    public static HashMap<Class, UpdateBuilder> createUpdateBuilders() {
        final FilterQuery heroQuery = new FilterQuery()
            .setBaseClass(HeroComponent.class)
            .addComponentClassFilter(BoardCardComponent.class)
            .addComponentClassFilter(HealthComponent.class);
        
        HashMap<Class, UpdateBuilder> updateBuilders = new HashMap<>();
        updateBuilders.put(BoardAttachSystem.class, new UpdateBuilder<BoardAttachEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, BoardAttachEvent event) {
                return new SetZone(converter.toLong(event.target), CardZone.Board.ordinal());
            }
        });
        updateBuilders.put(HandAttachSystem.class, new UpdateBuilder<HandAttachEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, HandAttachEvent event) {
                return new SetZone(converter.toLong(event.target), CardZone.Hand.ordinal());
            }
        });
        updateBuilders.put(GraveyardAttachSystem.class, new UpdateBuilder<GraveyardAttachEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, GraveyardAttachEvent event) {
                return new SetZone(converter.toLong(event.target), CardZone.Graveyard.ordinal());
            }
        });
        
        updateBuilders.put(BoardDetachSystem.class, new UpdateBuilder<BoardDetachEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, BoardDetachEvent event) {
                return new SetZone(converter.toLong(event.target), CardZone.None.ordinal());
            }
        });
        updateBuilders.put(HandDetachSystem.class, new UpdateBuilder<HandDetachEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, HandDetachEvent event) {
                return new SetZone(converter.toLong(event.target), CardZone.None.ordinal());
            }
        });
        
        updateBuilders.put(SetOwnerSystem.class, new UpdateBuilder<SetOwnerEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, SetOwnerEvent event) {
                return new SetOwner(converter.toLong(event.target), converter.toLong(event.owner));
            }
        });
        updateBuilders.put(SetHealthSystem.class, new UpdateBuilder<SetHealthEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, SetHealthEvent event) {
                return new SetHealth(converter.toLong(event.entity), event.health);
            }
        });
        updateBuilders.put(EndMatchSystem.class, new UpdateBuilder<PlayerLostEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, PlayerLostEvent event) {
                return new GameOver(converter.toLong(data.entities(WinnerComponent.class).iterator().next()));
            }
        });
        
        updateBuilders.put(SetManaSystem.class, new UpdateBuilder<SetManaEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, SetManaEvent event) {
                for (EntityId hero : heroQuery.list(data)) {
                    if(data.get(hero, OwnerComponent.class).player.equals(event.entity)) {
                        return new SetCost(converter.toLong(hero), event.mana);
                    }
                }
                throw new IllegalStateException();
            }
        });
//        updateBuilders.put(AttachTemplateSystem.class, new UpdateBuilder<AttachTemplateEvent>() {
//            @Override
//            public MatchUpdate build(MatchState match, IdConverter converter, AttachTemplateEvent event) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
        updateBuilders.put(AttachTemplateSystem.class, new UpdateBuilder<AttachTemplateEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, AttachTemplateEvent event) {
                return new AttachEffect(converter.toLong(event.parent), converter.toLong(event.target), event.template);
            }
        });
        updateBuilders.put(DeleteEntitySystem.class, new UpdateBuilder<DeleteEntityEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, DeleteEntityEvent event) {
                return new DetachEffect(converter.toLong(event.target));
            }
        });
        updateBuilders.put(ApplyAttackSystem.class, new UpdateBuilder<AttackEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, AttackEvent event) {
                return new AttackUpdate(converter.toLong(event.source), converter.toLong(event.target));
            }
        });
        updateBuilders.put(SetDivineShieldSystem.class, new UpdateBuilder<SetDivineShieldEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, SetDivineShieldEvent event) {
                return new SetProperty(converter.toLong(event.target), "Divine Shield", event.value? 1: 0);
            }
        });
        updateBuilders.put(AttachSummoningSicknessSystem.class, new UpdateBuilder<BoardAttachEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, BoardAttachEvent event) {
                return new SetProperty(converter.toLong(event.target), "Sickness", 1);
            }
        });
        updateBuilders.put(RemoveSummonSicknessSystem.class, new UpdateBuilder<RemoveSummonSicknessEvent>() {
            @Override
            public MatchUpdate build(EntityComponentMapReadonly data, IdConverter converter, RemoveSummonSicknessEvent event) {
                return new SetProperty(converter.toLong(event.target), "Sickness", 0);
            }
        });
        return updateBuilders;
    }
}
