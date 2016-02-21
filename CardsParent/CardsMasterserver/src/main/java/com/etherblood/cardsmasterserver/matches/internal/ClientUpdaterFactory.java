package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmatch.cardgame.components.battle.hero.HeroComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.cardZone.BoardCardComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.WinnerComponent;
import com.etherblood.cardsmatch.cardgame.events.attack.AttackEvent;
import com.etherblood.cardsmatch.cardgame.events.attack.systems.ApplyAttackSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.BoardAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.BoardDetachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.GraveyardAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.HandAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.HandDetachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.attackAbility.AttachSummoningSicknessSystem;
import com.etherblood.cardsmatch.cardgame.events.damage.SetDivineShieldEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.SetDivineShieldSystem;
import com.etherblood.cardsmatch.cardgame.events.entities.AttachTemplateEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.DeleteEntityEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.AttachTemplateSystem;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.DeleteEntitySystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.EndMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.SetManaSystem;
import com.etherblood.cardsmatch.cardgame.events.setHealth.SetHealthEvent;
import com.etherblood.cardsmatch.cardgame.events.setHealth.systems.SetHealthSystem;
import com.etherblood.cardsmatch.cardgame.events.setOwner.SetOwnerEvent;
import com.etherblood.cardsmatch.cardgame.events.setOwner.systems.SetOwnerSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.RemoveSummonSicknessEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.MultiRemoveSummonSicknessSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.RemoveSummonSicknessSystem;
import com.etherblood.cardsnetworkshared.match.misc.CardZone;
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
                    if(data.get(hero, OwnerComponent.class).player == event.entity) {
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
