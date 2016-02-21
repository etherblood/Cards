package com.etherblood.firstruleset;

import com.etherblood.cardsmatch.cardgame.MatchGameEventDispatcher;
import com.etherblood.cardsmatch.cardgame.RngFactoryImpl;
import com.etherblood.cardsmatch.cardgame.TemplateSet;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.cardsmatch.cardgame.events.ShuffleLibraryEvent;
import com.etherblood.cardsmatch.cardgame.events.attack.AttackEvent;
import com.etherblood.cardsmatch.cardgame.events.attack.systems.ApplyAttackSystem;
import com.etherblood.cardsmatch.cardgame.events.attack.systems.IncreaseAttackCountSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZoneMoveEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.BoardAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.BoardDetachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.CardZoneMoveSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.GraveyardAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.GraveyardDetachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.HandAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.HandDetachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.LibraryAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.LibraryDetachSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.attackAbility.AttachAttackAbilitySystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.attackAbility.AttachSummoningSicknessSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.attackAbility.DetachAttackAbilitySystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.castAbility.AttachCastAbilitySystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.castAbility.DetachCastAbilitySystem;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.SetDivineShieldEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.ApplyDamageSystem;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.DivineShieldSystem;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.SetDivineShieldSystem;
import com.etherblood.cardsmatch.cardgame.events.death.DeathEvent;
import com.etherblood.cardsmatch.cardgame.events.death.systems.ApplyDeathSystem;
import com.etherblood.cardsmatch.cardgame.events.death.systems.DeathrattleSystem;
import com.etherblood.cardsmatch.cardgame.events.draw.DrawEvent;
import com.etherblood.cardsmatch.cardgame.events.draw.RequestDrawEvent;
import com.etherblood.cardsmatch.cardgame.events.draw.systems.ApplyDrawSystem;
import com.etherblood.cardsmatch.cardgame.events.draw.systems.RequestDrawSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.AttachTemplateEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.AttackEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.BoardAttachEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.DealDamageEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.DealRandomDamageEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.DrawEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.EndTurnEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.HandDetachEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.PlayerLostEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.SetSameOwnerAsTriggerEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.SummonEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.CanAttackConditionSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.EffectPlayerTriggerConditionSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.ItsMyTurnConditionSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.PayManaCostEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.TriggerConditionEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.CreateSingleTargetEntityEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.SelectTargetsEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.TargetedTriggerEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.TriggerEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.ApplyEndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.ApplyEndTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.EndTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.EndTurnTriggerSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.NextTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.entities.AttachTemplateEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.DeleteEntityEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.AttachTemplateSystem;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.DeleteEntitySystem;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.DeleteEntityTriggerChildsSystem;
import com.etherblood.cardsmatch.cardgame.events.fatigue.FatigueEvent;
import com.etherblood.cardsmatch.cardgame.events.fatigue.systems.ApplyFatigueSystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.EndMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.PlayerLostSystem;
import com.etherblood.cardsmatch.cardgame.events.gamestart.GameStartEvent;
import com.etherblood.cardsmatch.cardgame.events.gamestart.systems.ApplyGameStartSystem;
import com.etherblood.cardsmatch.cardgame.events.heal.HealEvent;
import com.etherblood.cardsmatch.cardgame.events.heal.systems.ApplyHealSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.ManaPaymentEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaLimitEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.ManaPaymentSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.ManaUpkeepPhaseSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.SetManaLimitSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.SetManaSystem;
import com.etherblood.cardsmatch.cardgame.events.setHealth.SetHealthEvent;
import com.etherblood.cardsmatch.cardgame.events.setHealth.systems.HealthDeathSystem;
import com.etherblood.cardsmatch.cardgame.events.setHealth.systems.SetHealthSystem;
import com.etherblood.cardsmatch.cardgame.events.setOwner.SetOwnerEvent;
import com.etherblood.cardsmatch.cardgame.events.setOwner.systems.SetOwnerSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.DrawPhaseSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.RegenerationSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.RemoveAttackCountSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.MultiRemoveSummonSicknessSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.StartTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.summon.SummonEvent;
import com.etherblood.cardsmatch.cardgame.events.summon.systems.ApplySummonSystem;
import com.etherblood.cardsmatch.cardgame.events.summon.systems.BattlecrySystem;
import com.etherblood.cardsmatch.cardgame.events.surrender.SurrenderEvent;
import com.etherblood.cardsmatch.cardgame.events.surrender.systems.SurrenderSystem;
import com.etherblood.cardsmatch.cardgame.events.systems.ShuffleLibrarySystem;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityComponentMapImpl;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityIdFactory;
import com.etherblood.entitysystem.data.IncrementalEntityIdFactory;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.eventsystem.GameEventDispatcher;
import com.etherblood.eventsystem.GameEventHandler;
import com.etherblood.eventsystem.GameEventQueue;
import com.etherblood.eventsystem.GameEventQueueImpl;
import com.etherblood.match.MatchContext;
import com.etherblood.match.MatchContextBuilder;
import com.etherblood.match.PlayerDefinition;
import com.etherblood.match.RulesDefinition;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandlerDispatcher;
import com.etherblood.cardsmatch.cardgame.components.player.NextTurnPlayerComponent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.RemoveSummonSicknessEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.RemoveSummonSicknessSystem;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class DefaultRulesDef implements RulesDefinition {
    private final MatchContextBuilder builder = new MatchContextBuilder();

    public DefaultRulesDef(TemplateSet templates) {
        GameEventDispatcher dispatcher = new MatchGameEventDispatcher();
        GameEventQueueImpl events = new GameEventQueueImpl(dispatcher);
        
        builder.addBean(new EntityComponentMapImpl());
        builder.addBean(dispatcher);
        builder.addBean(new SystemsEventHandlerDispatcher());
        builder.addBean(events);
        builder.addBean(events.getDataStack());
        builder.addBean(new IncrementalEntityIdFactory());
        builder.addBean(new RngFactoryImpl());
        builder.addBean(templates);
        
        addSystem(dispatcher, ApplyEndTurnEvent.class, new ApplyEndTurnSystem());
        addSystem(dispatcher, AttachTemplateEvent.class, new AttachTemplateSystem());
        addSystem(dispatcher, AttackEvent.class, new IncreaseAttackCountSystem());
        addSystem(dispatcher, AttackEvent.class, new ApplyAttackSystem());
        addSystem(dispatcher, BoardAttachEvent.class, new BoardAttachSystem());
        addSystem(dispatcher, BoardAttachEvent.class, new AttachAttackAbilitySystem());
        addSystem(dispatcher, BoardAttachEvent.class, new AttachSummoningSicknessSystem());
        addSystem(dispatcher, BoardDetachEvent.class, new BoardDetachSystem());
        addSystem(dispatcher, BoardDetachEvent.class, new DetachAttackAbilitySystem());
        addSystem(dispatcher, SetDivineShieldEvent.class, new SetDivineShieldSystem());
        addSystem(dispatcher, CardZoneMoveEvent.class, new CardZoneMoveSystem());
//        addSystem(dispatcher, ClearEffectTargetsEvent.class, new ClearEffectTargetsSystem());
        addSystem(dispatcher, DamageEvent.class, new DivineShieldSystem());
        addSystem(dispatcher, DamageEvent.class, new ApplyDamageSystem());
        addSystem(dispatcher, DeathEvent.class, new ApplyDeathSystem());
        addSystem(dispatcher, DeathEvent.class, new DeathrattleSystem());
        addSystem(dispatcher, DeleteEntityEvent.class, new DeleteEntityTriggerChildsSystem());
        addSystem(dispatcher, DeleteEntityEvent.class, new DeleteEntitySystem());
        addSystem(dispatcher, DrawEvent.class, new ApplyDrawSystem());

        addSystem(dispatcher, EffectEvent.class, new ItsMyTurnConditionSystem());
        addSystem(dispatcher, EffectEvent.class, new CanAttackConditionSystem());
        addSystem(dispatcher, EffectEvent.class, new TriggerConditionEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new PayManaCostEffectSystem());
        
        addSystem(dispatcher, EffectEvent.class, new AttachTemplateEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new DrawEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new SetSameOwnerAsTriggerEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new SummonEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new HandDetachEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new BoardAttachEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new DealDamageEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new DealRandomDamageEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new AttackEffectSystem());
        addSystem(dispatcher, EffectEvent.class, new EndTurnEffectSystem());

        addSystem(dispatcher, EffectEvent.class, new PlayerLostEffectSystem());
//        addSystem(dispatcher, EffectEvent.class, new SpawnTokenEffectSystem());
        addSystem(dispatcher, EndTurnEvent.class, new EndTurnTriggerSystem());
        addSystem(dispatcher, EndTurnEvent.class, new EndTurnSystem());
        addSystem(dispatcher, EndTurnEvent.class, new NextTurnSystem());
        addSystem(dispatcher, FatigueEvent.class, new ApplyFatigueSystem());
        addSystem(dispatcher, GameStartEvent.class, new ApplyGameStartSystem());
        addSystem(dispatcher, GraveyardAttachEvent.class, new GraveyardAttachSystem());
        addSystem(dispatcher, GraveyardDetachEvent.class, new GraveyardDetachSystem());
        addSystem(dispatcher, HandAttachEvent.class, new HandAttachSystem());
//        addSystem(dispatcher, HandAttachEvent.class, new AttachSummonAbilitySystem());
        addSystem(dispatcher, HandAttachEvent.class, new AttachCastAbilitySystem());
        addSystem(dispatcher, HandDetachEvent.class, new HandDetachSystem());
//        addSystem(dispatcher, HandDetachEvent.class, new DetachSummonAbilitySystem());
        addSystem(dispatcher, HandDetachEvent.class, new DetachCastAbilitySystem());
        addSystem(dispatcher, HealEvent.class, new ApplyHealSystem());
//        addSystem(dispatcher, InitPlayerEvent.class, new InitPlayerSystem());
//        addSystem(dispatcher, InstantiateTemplateEvent.class, new InstantiateTemplateSystem());
        addSystem(dispatcher, LibraryAttachEvent.class, new LibraryAttachSystem());
        addSystem(dispatcher, LibraryDetachEvent.class, new LibraryDetachSystem());
        addSystem(dispatcher, ManaPaymentEvent.class, new ManaPaymentSystem());
        addSystem(dispatcher, PlayerLostEvent.class, new PlayerLostSystem());
        addSystem(dispatcher, PlayerLostEvent.class, new EndMatchSystem());
        addSystem(dispatcher, RemoveSummonSicknessEvent.class, new RemoveSummonSicknessSystem());
        addSystem(dispatcher, RequestDrawEvent.class, new RequestDrawSystem());
        addSystem(dispatcher, ShuffleLibraryEvent.class, new ShuffleLibrarySystem());
        addSystem(dispatcher, StartTurnEvent.class, new StartTurnSystem());
        addSystem(dispatcher, StartTurnEvent.class, new ManaUpkeepPhaseSystem());
        addSystem(dispatcher, StartTurnEvent.class, new DrawPhaseSystem());
        addSystem(dispatcher, StartTurnEvent.class, new RegenerationSystem());
        addSystem(dispatcher, StartTurnEvent.class, new MultiRemoveSummonSicknessSystem());
        addSystem(dispatcher, StartTurnEvent.class, new RemoveAttackCountSystem());
        addSystem(dispatcher, SetHealthEvent.class, new SetHealthSystem());
        addSystem(dispatcher, SetHealthEvent.class, new HealthDeathSystem());
        addSystem(dispatcher, SetManaEvent.class, new SetManaSystem());
        addSystem(dispatcher, SetManaLimitEvent.class, new SetManaLimitSystem());
        addSystem(dispatcher, SetOwnerEvent.class, new SetOwnerSystem());
        addSystem(dispatcher, SummonEvent.class, new ApplySummonSystem());
        addSystem(dispatcher, SummonEvent.class, new BattlecrySystem());
        addSystem(dispatcher, SurrenderEvent.class, new SurrenderSystem());
        
        addSystem(dispatcher, TargetedTriggerEffectEvent.class, new TargetedTriggerEffectSystem());
        addSystem(dispatcher, TriggerEffectEvent.class, new CreateSingleTargetEntityEffectSystem());
        addSystem(dispatcher, TriggerEffectEvent.class, new SelectTargetsEffectSystem());
        addSystem(dispatcher, TriggerEffectEvent.class, new EffectPlayerTriggerConditionSystem());
        addSystem(dispatcher, TriggerEffectEvent.class, new TriggerEffectSystem());
    }
    
    private <E extends GameEvent> void addSystem(GameEventDispatcher dispatcher, Class<E> eventClass, GameEventHandler system) {
        dispatcher.subscribe(eventClass, system);
        builder.addHiddenBean(system);
    }

    @Override
    public MatchContext start(List<PlayerDefinition> playerDefinitions) {
        if(playerDefinitions.size() != 2) {
            throw new IllegalStateException();
        }
        MatchContext match = builder.build();
//        List<SystemsEventHandler> handlers = match.getBean(SystemsEventHandlerDispatcher.class).getHandlers();
//        for (PlayerDefinition playerDef : playerDefinitions) {
//            SystemsEventHandler handler = playerDef.getUpdateHandler();
//            if(handler != null) {
//                handlers.add(handler);
//            }
//        }
        Random rng = new Random();
        for (PlayerDefinition def : playerDefinitions) {
            EntityId playerEntity = match.getBean(EntityIdFactory.class).createEntity();
            def.setEntity(playerEntity);
            initPlayer(match, playerEntity, def.getName(), def.getHeroTemplate(), def.getLibrary(), rng);
        }
        
        EntityId player1 = playerDefinitions.get(0).getEntity();
        EntityId player2 = playerDefinitions.get(1).getEntity();
        
        EntityComponentMap data = match.getBean(EntityComponentMap.class);
        data.set(player1, new NextTurnPlayerComponent(player2));
        data.set(player2, new NextTurnPlayerComponent(player1));
        
        GameEventQueue events = match.getBean(GameEventQueue.class);
        events.fireEvent(new GameStartEvent());

        for (int i = 0; i < 3; i++) {
            events.fireEvent(new RequestDrawEvent(player2));
            events.fireEvent(new RequestDrawEvent(player1));
        }
        EntityId startingPlayer = rng.nextBoolean() ? player1 : player2;
        events.fireEvent(new RequestDrawEvent(startingPlayer == player1 ? player2 : player1));
        events.fireEvent(new StartTurnEvent(startingPlayer));
        
        return match;
    }

    private void initPlayer(MatchContext match, EntityId player, String name, String heroTemplate, String[] library, Random rng) {
        EntityComponentMap data = match.getBean(EntityComponentMap.class);
        EntityIdFactory idFactory = match.getBean(EntityIdFactory.class);
        GameEventQueue events = match.getBean(GameEventQueue.class);
        
        data.set(player, new NameComponent(name));
        data.set(player, new PlayerComponent());

        EntityId hero = idFactory.createEntity();
        events.fireEvent(new AttachTemplateEvent(hero, heroTemplate, player, null));
        events.fireEvent(new BoardAttachEvent(hero));

        for (String template : library) {
            EntityId cardId = idFactory.createEntity();
            events.fireEvent(new AttachTemplateEvent(cardId, template, player, null));
            events.fireEvent(new LibraryAttachEvent(cardId));
        }
        events.fireEvent(new ShuffleLibraryEvent(player));
    }
}
