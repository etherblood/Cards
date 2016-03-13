package com.etherblood.firstruleset;

import com.etherblood.cardsmatch.cardgame.MatchGameEventDispatcher;
import com.etherblood.cardsmatch.cardgame.TemplateSet;
import com.etherblood.cardsmatch.cardgame.bot.monteCarlo.MonteCarloController;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandlerDispatcher;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.firstruleset.logic.player.NextTurnPlayerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.EndMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.PlayerLostSystem;
import com.etherblood.cardsmatch.cardgame.events.gamestart.GameStartEvent;
import com.etherblood.firstruleset.logic.shuffle.ShuffleLibraryEvent;
import com.etherblood.cardsmatch.cardgame.events.gamestart.systems.ApplyGameStartSystem;
import com.etherblood.firstruleset.logic.shuffle.ShuffleLibrarySystem;
import com.etherblood.cardsmatch.cardgame.events.surrender.SurrenderEvent;
import com.etherblood.cardsmatch.cardgame.events.surrender.systems.SurrenderSystem;
import com.etherblood.cardsmatch.cardgame.rng.RngFactoryImpl;
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
import com.etherblood.firstruleset.logic.attack.AttackEvent;
import com.etherblood.firstruleset.logic.attack.systems.ApplyAttackSystem;
import com.etherblood.firstruleset.logic.attack.systems.IncreaseAttackCountSystem;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.CardZoneMoveEvent;
import com.etherblood.firstruleset.logic.cardZones.events.GraveyardAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.GraveyardDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.HandAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.HandDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.LibraryAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.LibraryDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.systems.BoardAttachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.BoardDetachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.CardZoneMoveSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.GraveyardAttachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.GraveyardDetachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.HandAttachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.HandDetachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.LibraryAttachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.LibraryDetachSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.attackAbility.AttachSummoningSicknessSystem;
import com.etherblood.firstruleset.logic.cardZones.systems.attackAbility.DetachAttackAbilitySystem;
import com.etherblood.firstruleset.logic.cardZones.systems.castAbility.AttachCastAbilitySystem;
import com.etherblood.firstruleset.logic.cardZones.systems.castAbility.DetachCastAbilitySystem;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.firstruleset.logic.damage.SetDivineShieldEvent;
import com.etherblood.firstruleset.logic.damage.systems.ApplyDamageSystem;
import com.etherblood.firstruleset.logic.damage.systems.DivineShieldSystem;
import com.etherblood.firstruleset.logic.damage.systems.SetDivineShieldSystem;
import com.etherblood.firstruleset.logic.death.DeathEvent;
import com.etherblood.firstruleset.logic.death.systems.ApplyDeathSystem;
import com.etherblood.firstruleset.logic.death.systems.DeathrattleSystem;
import com.etherblood.firstruleset.logic.draw.DrawEvent;
import com.etherblood.firstruleset.logic.draw.RequestDrawEvent;
import com.etherblood.firstruleset.logic.draw.systems.ApplyDrawSystem;
import com.etherblood.firstruleset.logic.draw.systems.RequestDrawSystem;
import com.etherblood.firstruleset.logic.effects.EffectEvent;
import com.etherblood.firstruleset.logic.effects.TargetedTriggerEffectEvent;
import com.etherblood.firstruleset.logic.effects.TriggerEffectEvent;
import com.etherblood.firstruleset.logic.effects.systems.AttachTemplateEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.AttackEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.BoardAttachEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.DealDamageEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.DealRandomDamageEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.DrawEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.EndTurnEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.HandDetachEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.PlayerLostEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.SetOwnerEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.SummonEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.conditions.CanAttackConditionSystem;
import com.etherblood.firstruleset.logic.effects.systems.conditions.ItsMyTurnConditionSystem;
import com.etherblood.firstruleset.logic.effects.systems.conditions.PayManaCostEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.triggers.CreateSingleTargetEntityEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.triggers.SelectTargetsEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.triggers.TargetedTriggerEffectSystem;
import com.etherblood.firstruleset.logic.effects.systems.triggers.TriggerEffectSystem;
import com.etherblood.firstruleset.logic.endTurn.ApplyEndTurnEvent;
import com.etherblood.firstruleset.logic.endTurn.EndTurnEvent;
import com.etherblood.firstruleset.logic.endTurn.systems.ApplyEndTurnSystem;
import com.etherblood.firstruleset.logic.endTurn.systems.EndTurnSystem;
import com.etherblood.firstruleset.logic.endTurn.systems.EndTurnTriggerSystem;
import com.etherblood.firstruleset.logic.endTurn.systems.NextTurnSystem;
import com.etherblood.firstruleset.logic.entities.AttachTemplateEvent;
import com.etherblood.firstruleset.logic.entities.DeleteEntityEvent;
import com.etherblood.firstruleset.logic.entities.systems.AttachTemplateSystem;
import com.etherblood.firstruleset.logic.entities.systems.DeleteEntitySystem;
import com.etherblood.firstruleset.logic.entities.systems.DeleteEntityTriggerChildsSystem;
import com.etherblood.firstruleset.logic.fatigue.FatigueEvent;
import com.etherblood.firstruleset.logic.fatigue.systems.ApplyFatigueSystem;
import com.etherblood.firstruleset.logic.heal.HealEvent;
import com.etherblood.firstruleset.logic.heal.systems.ApplyHealSystem;
import com.etherblood.firstruleset.logic.mana.ManaPaymentEvent;
import com.etherblood.firstruleset.logic.mana.SetManaEvent;
import com.etherblood.firstruleset.logic.mana.SetManaLimitEvent;
import com.etherblood.firstruleset.logic.mana.systems.ManaPaymentSystem;
import com.etherblood.firstruleset.logic.mana.systems.ManaUpkeepPhaseSystem;
import com.etherblood.firstruleset.logic.mana.systems.SetManaLimitSystem;
import com.etherblood.firstruleset.logic.mana.systems.SetManaSystem;
import com.etherblood.firstruleset.logic.setHealth.SetHealthEvent;
import com.etherblood.firstruleset.logic.setHealth.systems.HealthDeathSystem;
import com.etherblood.firstruleset.logic.setHealth.systems.SetHealthSystem;
import com.etherblood.firstruleset.logic.setOwner.SetOwnerEvent;
import com.etherblood.firstruleset.logic.setOwner.systems.SetOwnerSystem;
import com.etherblood.firstruleset.logic.startTurn.RemoveSummonSicknessEvent;
import com.etherblood.firstruleset.logic.startTurn.StartTurnEvent;
import com.etherblood.firstruleset.logic.startTurn.systems.DrawPhaseSystem;
import com.etherblood.firstruleset.logic.startTurn.systems.MultiRemoveSummonSicknessSystem;
import com.etherblood.firstruleset.logic.startTurn.systems.RegenerationSystem;
import com.etherblood.firstruleset.logic.startTurn.systems.RemoveAttackCountSystem;
import com.etherblood.firstruleset.logic.startTurn.systems.RemoveSummonSicknessSystem;
import com.etherblood.firstruleset.logic.startTurn.systems.StartTurnSystem;
import com.etherblood.firstruleset.logic.summon.SummonEvent;
import com.etherblood.firstruleset.logic.summon.systems.ApplySummonSystem;
import com.etherblood.firstruleset.logic.summon.systems.BattlecrySystem;
import com.etherblood.cardsmatch.cardgame.match.MatchContext;
import com.etherblood.cardsmatch.cardgame.match.MatchContextBuilder;
import com.etherblood.cardsmatch.cardgame.match.PlayerDefinition;
import com.etherblood.cardsmatch.cardgame.match.RulesDefinition;
import com.etherblood.entitysystem.version.VersionedEntityComponentMapImpl;
import com.etherblood.firstruleset.bot.CommandGeneratorImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class DefaultRulesDef implements RulesDefinition {

    private final TemplateSet templates;

    public DefaultRulesDef() {
        this(new DefaultTemplateSetFactory().createEntityTemplates("/templates.xml"));
    }

    public DefaultRulesDef(TemplateSet templates) {
        this.templates = templates;
    }

    private <E extends GameEvent> void addSystem(MatchContextBuilder builder, GameEventDispatcher dispatcher, Class<E> eventClass, GameEventHandler<E> system) {
        dispatcher.subscribe(eventClass, system);
        builder.addPassiveBean(system);
    }

    @Override
    public MatchContext start(List<PlayerDefinition> playerDefinitions) {
        if (playerDefinitions.size() != 2) {
            throw new IllegalStateException();
        }
        MatchContext match = buildContext(true);

        EntityIdFactory idFactory = match.getBean(EntityIdFactory.class);
        Random rng = new Random();
        for (PlayerDefinition def : playerDefinitions) {
            EntityId playerEntity = idFactory.createEntity();
            def.setEntity(playerEntity);
            initPlayer(match, playerEntity, def.getName(), def.getHeroTemplate(), def.getLibrary(), rng);
            if (def.isBot()) {
                def.setBotInstance(new MonteCarloController(match, buildContext(false), new CommandGeneratorImpl(), playerDefinitions.get(0).getEntity()));
            }
        }

        EntityId player1 = playerDefinitions.get(0).getEntity();
        EntityId player2 = playerDefinitions.get(1).getEntity();

        EntityComponentMap data = match.getBean(EntityComponentMap.class);
        data.set(player1, new NextTurnPlayerComponent(player2));
        data.set(player2, new NextTurnPlayerComponent(player1));

        GameEventQueue eventQueue = match.getBean(GameEventQueue.class);
        eventQueue.fireEvent(new GameStartEvent());

        for (int i = 0; i < 3; i++) {
            eventQueue.fireEvent(new RequestDrawEvent(player2));
            eventQueue.fireEvent(new RequestDrawEvent(player1));
        }
        EntityId startingPlayer = rng.nextBoolean() ? player1 : player2;
        eventQueue.fireEvent(new RequestDrawEvent(startingPlayer == player1 ? player2 : player1));
        eventQueue.fireEvent(new StartTurnEvent(startingPlayer));

        return match;
    }
    
    private MatchContext buildContext(boolean core) {
        MatchContextBuilder builder = new MatchContextBuilder();
        GameEventDispatcher eventDispatcher = new MatchGameEventDispatcher();
        GameEventQueueImpl events = new GameEventQueueImpl(eventDispatcher);
        EntityComponentMap data = new EntityComponentMapImpl();
        if(core) {
            data = new VersionedEntityComponentMapImpl(data);
        }
        IncrementalEntityIdFactory idFactory = new IncrementalEntityIdFactory();
        SystemsEventHandlerDispatcher systemsDispatcher = new SystemsEventHandlerDispatcher();

        builder.addBean(data);
        builder.addBean(eventDispatcher);
        builder.addBean(systemsDispatcher);
        builder.addBean(events);
        builder.addBean(events.getDataStack());
        builder.addBean(idFactory);
        builder.addBean(new RngFactoryImpl());
        builder.addBean(templates);
        builder.addBean(new ValidEffectTargetsSelectorImpl());
        if(core) {
            systemsDispatcher.getHandlers().add(new MatchLogger(data));
        }

        addSystems(builder, eventDispatcher);

        return builder.build();
    }

    private void addSystems(MatchContextBuilder builder, GameEventDispatcher eventDispatcher) {
        addSystem(builder, eventDispatcher, ApplyEndTurnEvent.class, new ApplyEndTurnSystem());
        addSystem(builder, eventDispatcher, AttachTemplateEvent.class, new AttachTemplateSystem());
        addSystem(builder, eventDispatcher, AttachTemplateEvent.class, new CopyBattlecryConditionsSystem());
        addSystem(builder, eventDispatcher, AttackEvent.class, new IncreaseAttackCountSystem());
        addSystem(builder, eventDispatcher, AttackEvent.class, new ApplyAttackSystem());
        addSystem(builder, eventDispatcher, BoardAttachEvent.class, new BoardAttachSystem());
        addSystem(builder, eventDispatcher, BoardAttachEvent.class, new AttachAttackAbilitySystem());
        addSystem(builder, eventDispatcher, BoardAttachEvent.class, new AttachSummoningSicknessSystem());
        addSystem(builder, eventDispatcher, BoardDetachEvent.class, new BoardDetachSystem());
        addSystem(builder, eventDispatcher, BoardDetachEvent.class, new DetachAttackAbilitySystem());
        addSystem(builder, eventDispatcher, SetDivineShieldEvent.class, new SetDivineShieldSystem());
        addSystem(builder, eventDispatcher, CardZoneMoveEvent.class, new CardZoneMoveSystem());
//        addSystem(builder, dispatcher, ClearEffectTargetsEvent.class, new ClearEffectTargetsSystem());
        addSystem(builder, eventDispatcher, DamageEvent.class, new DivineShieldSystem());
        addSystem(builder, eventDispatcher, DamageEvent.class, new ApplyDamageSystem());
        addSystem(builder, eventDispatcher, DeathEvent.class, new ApplyDeathSystem());
        addSystem(builder, eventDispatcher, DeathEvent.class, new DeathrattleSystem());
        addSystem(builder, eventDispatcher, DeleteEntityEvent.class, new DeleteEntityTriggerChildsSystem());
        addSystem(builder, eventDispatcher, DeleteEntityEvent.class, new DeleteEntitySystem());
        addSystem(builder, eventDispatcher, DrawEvent.class, new ApplyDrawSystem());

        addSystem(builder, eventDispatcher, EffectEvent.class, new ItsMyTurnConditionSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new CanAttackConditionSystem());
//        addSystem(builder, dispatcher, EffectEvent.class, new TriggerConditionEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new PayManaCostEffectSystem());

        addSystem(builder, eventDispatcher, EffectEvent.class, new AttachTemplateEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new DrawEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new SetOwnerEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new SummonEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new HandDetachEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new BoardAttachEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new DealDamageEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new DealRandomDamageEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new AttackEffectSystem());
        addSystem(builder, eventDispatcher, EffectEvent.class, new EndTurnEffectSystem());

        addSystem(builder, eventDispatcher, EffectEvent.class, new PlayerLostEffectSystem());
//        addSystem(builder, dispatcher, EffectEvent.class, new SpawnTokenEffectSystem());
        addSystem(builder, eventDispatcher, EndTurnEvent.class, new EndTurnTriggerSystem());
        addSystem(builder, eventDispatcher, EndTurnEvent.class, new EndTurnSystem());
        addSystem(builder, eventDispatcher, EndTurnEvent.class, new NextTurnSystem());
        addSystem(builder, eventDispatcher, FatigueEvent.class, new ApplyFatigueSystem());
        addSystem(builder, eventDispatcher, GameStartEvent.class, new ApplyGameStartSystem());
        addSystem(builder, eventDispatcher, GraveyardAttachEvent.class, new GraveyardAttachSystem());
        addSystem(builder, eventDispatcher, GraveyardDetachEvent.class, new GraveyardDetachSystem());
        addSystem(builder, eventDispatcher, HandAttachEvent.class, new HandAttachSystem());
//        addSystem(builder, dispatcher, HandAttachEvent.class, new AttachSummonAbilitySystem());
        addSystem(builder, eventDispatcher, HandAttachEvent.class, new AttachCastAbilitySystem());
        addSystem(builder, eventDispatcher, HandDetachEvent.class, new HandDetachSystem());
//        addSystem(builder, dispatcher, HandDetachEvent.class, new DetachSummonAbilitySystem());
        addSystem(builder, eventDispatcher, HandDetachEvent.class, new DetachCastAbilitySystem());
        addSystem(builder, eventDispatcher, HealEvent.class, new ApplyHealSystem());
//        addSystem(builder, dispatcher, InitPlayerEvent.class, new InitPlayerSystem());
//        addSystem(builder, dispatcher, InstantiateTemplateEvent.class, new InstantiateTemplateSystem());
        addSystem(builder, eventDispatcher, LibraryAttachEvent.class, new LibraryAttachSystem());
        addSystem(builder, eventDispatcher, LibraryDetachEvent.class, new LibraryDetachSystem());
        addSystem(builder, eventDispatcher, ManaPaymentEvent.class, new ManaPaymentSystem());
        addSystem(builder, eventDispatcher, PlayerLostEvent.class, new PlayerLostSystem());
        addSystem(builder, eventDispatcher, PlayerLostEvent.class, new EndMatchSystem());
        addSystem(builder, eventDispatcher, RemoveSummonSicknessEvent.class, new RemoveSummonSicknessSystem());
        addSystem(builder, eventDispatcher, RequestDrawEvent.class, new RequestDrawSystem());
        addSystem(builder, eventDispatcher, ShuffleLibraryEvent.class, new ShuffleLibrarySystem());
        addSystem(builder, eventDispatcher, StartTurnEvent.class, new StartTurnSystem());
        addSystem(builder, eventDispatcher, StartTurnEvent.class, new ManaUpkeepPhaseSystem());
        addSystem(builder, eventDispatcher, StartTurnEvent.class, new DrawPhaseSystem());
        addSystem(builder, eventDispatcher, StartTurnEvent.class, new RegenerationSystem());
        addSystem(builder, eventDispatcher, StartTurnEvent.class, new MultiRemoveSummonSicknessSystem());
        addSystem(builder, eventDispatcher, StartTurnEvent.class, new RemoveAttackCountSystem());
        addSystem(builder, eventDispatcher, SetHealthEvent.class, new SetHealthSystem());
        addSystem(builder, eventDispatcher, SetHealthEvent.class, new HealthDeathSystem());
        addSystem(builder, eventDispatcher, SetManaEvent.class, new SetManaSystem());
        addSystem(builder, eventDispatcher, SetManaLimitEvent.class, new SetManaLimitSystem());
        addSystem(builder, eventDispatcher, SetOwnerEvent.class, new SetOwnerSystem());
        addSystem(builder, eventDispatcher, SummonEvent.class, new ApplySummonSystem());
        addSystem(builder, eventDispatcher, SummonEvent.class, new BattlecrySystem());
        addSystem(builder, eventDispatcher, SurrenderEvent.class, new SurrenderSystem());

        addSystem(builder, eventDispatcher, TargetedTriggerEffectEvent.class, new TargetedTriggerEffectSystem());
        addSystem(builder, eventDispatcher, TriggerEffectEvent.class, new CreateSingleTargetEntityEffectSystem());
        addSystem(builder, eventDispatcher, TriggerEffectEvent.class, new SelectTargetsEffectSystem());
//        addSystem(builder, dispatcher, TriggerEffectEvent.class, new EffectPlayerTriggerConditionSystem());
        addSystem(builder, eventDispatcher, TriggerEffectEvent.class, new TriggerEffectSystem());
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

    @Override
    public String getName() {
        return "default rules";
    }

    @Override
    public List<String> getTemplateNames() {
        return Arrays.asList(templates.getCollectableTemplateNames());
    }
}
