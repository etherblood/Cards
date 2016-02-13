/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.firstruleset.cards;

import com.etherblood.cardsmatch.cardgame.MatchRulesDefinition;
import com.etherblood.cardsmatch.cardgame.MatchState;
import com.etherblood.cardsmatch.cardgame.TemplateSet;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.components.player.NextTurnPlayerComponent;
import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
import com.etherblood.cardsmatch.cardgame.events.attack.AttackEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
import com.etherblood.cardsmatch.cardgame.events.death.DeathEvent;
import com.etherblood.cardsmatch.cardgame.events.draw.DrawEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.EndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.draw.RequestDrawEvent;
import com.etherblood.cardsmatch.cardgame.events.setHealth.SetHealthEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.summon.SummonEvent;
import com.etherblood.cardsmatch.cardgame.events.attack.systems.ApplyAttackSystem;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.ApplyDamageSystem;
import com.etherblood.cardsmatch.cardgame.events.death.systems.ApplyDeathSystem;
import com.etherblood.cardsmatch.cardgame.events.draw.systems.ApplyDrawSystem;
import com.etherblood.cardsmatch.cardgame.events.setHealth.systems.HealthDeathSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.EndTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.attack.systems.IncreaseAttackCountSystem;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.DivineShieldSystem;
import com.etherblood.cardsmatch.cardgame.events.death.systems.DeathrattleSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.DrawPhaseSystem;
import com.etherblood.cardsmatch.cardgame.events.draw.systems.RequestDrawSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.NextTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.fatigue.FatigueEvent;
import com.etherblood.cardsmatch.cardgame.events.fatigue.systems.ApplyFatigueSystem;
import com.etherblood.cardsmatch.cardgame.events.gamestart.GameStartEvent;
import com.etherblood.cardsmatch.cardgame.events.gamestart.systems.ApplyGameStartSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZoneMoveEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.systems.BoardAttachSystem;
import com.etherblood.cardsmatch.cardgame.events.setHealth.systems.SetHealthSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryDetachEvent;
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
import com.etherblood.cardsmatch.cardgame.events.damage.SetDivineShieldEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.SetDivineShieldSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.EffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.TargetedTriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.TriggerEffectEvent;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.AttachTemplateEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.AttackEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.BoardAttachEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.CreateSingleTargetEntityEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.DealDamageEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.DealRandomDamageEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.DrawEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.EndTurnEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.PlayerLostEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.HandDetachEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.PayManaCostEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.SelectTargetsEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.SetSameOwnerAsTriggerEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.SummonEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.CanAttackConditionSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.ItsMyTurnConditionSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.TriggerConditionEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.TargetedTriggerEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.triggers.TriggerEffectSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.ApplyEndTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.ApplyEndTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.endTurn.systems.EndTurnTriggerSystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.PlayerLostSystem;
import com.etherblood.cardsmatch.cardgame.events.heal.HealEvent;
import com.etherblood.cardsmatch.cardgame.events.heal.systems.ApplyHealSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.ManaPaymentEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.SetManaLimitEvent;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.ManaPaymentSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.ManaUpkeepPhaseSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.SetManaLimitSystem;
import com.etherblood.cardsmatch.cardgame.events.mana.systems.SetManaSystem;
import com.etherblood.cardsmatch.cardgame.events.setOwner.SetOwnerEvent;
import com.etherblood.cardsmatch.cardgame.events.setOwner.systems.SetOwnerSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.RegenerationSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.StartTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.summon.systems.ApplySummonSystem;
import com.etherblood.cardsmatch.cardgame.events.summon.systems.BattlecrySystem;
import com.etherblood.cardsmatch.cardgame.events.surrender.SurrenderEvent;
import com.etherblood.cardsmatch.cardgame.events.surrender.systems.SurrenderSystem;
import com.etherblood.cardsmatch.cardgame.events.entities.AttachTemplateEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.DeleteEntityEvent;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.AttachTemplateSystem;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.DeleteEntitySystem;
import com.etherblood.cardsmatch.cardgame.events.entities.systems.DeleteEntityTriggerChildsSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.RemoveAttackCountSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.RemoveSummonSicknessSystem;
import com.etherblood.cardsmatch.cardgame.events.effects.systems.conditions.EffectPlayerTriggerConditionSystem;
import com.etherblood.cardsmatch.cardgame.events.gameover.systems.EndMatchSystem;
import com.etherblood.entitysystem.data.EntityId;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class DefaultMatchRulesDefinition implements MatchRulesDefinition {

    @Override
    public void applyTo(MatchState state) {
        initSystems(state);
    }
    
    //TODO
//    public DefaultMatchRulesDefinition(TemplateSet templates, SystemsEventHandler eventLogger) {
//        super(templates, eventLogger);
//    }

    private void initSystems(MatchState state) {
        state.addSystem(ApplyEndTurnEvent.class, new ApplyEndTurnSystem());
        state.addSystem(AttachTemplateEvent.class, new AttachTemplateSystem());
        state.addSystem(AttackEvent.class, new IncreaseAttackCountSystem());
        state.addSystem(AttackEvent.class, new ApplyAttackSystem());
        state.addSystem(BoardAttachEvent.class, new BoardAttachSystem());
        state.addSystem(BoardAttachEvent.class, new AttachAttackAbilitySystem());
        state.addSystem(BoardAttachEvent.class, new AttachSummoningSicknessSystem());
        state.addSystem(BoardDetachEvent.class, new BoardDetachSystem());
        state.addSystem(BoardDetachEvent.class, new DetachAttackAbilitySystem());
        state.addSystem(SetDivineShieldEvent.class, new SetDivineShieldSystem());
        state.addSystem(CardZoneMoveEvent.class, new CardZoneMoveSystem());
//        state.addSystem(ClearEffectTargetsEvent.class, new ClearEffectTargetsSystem());
        state.addSystem(DamageEvent.class, new DivineShieldSystem());
        state.addSystem(DamageEvent.class, new ApplyDamageSystem());
        state.addSystem(DeathEvent.class, new ApplyDeathSystem());
        state.addSystem(DeathEvent.class, new DeathrattleSystem());
        state.addSystem(DeleteEntityEvent.class, new DeleteEntityTriggerChildsSystem());
        state.addSystem(DeleteEntityEvent.class, new DeleteEntitySystem());
        state.addSystem(DrawEvent.class, new ApplyDrawSystem());

        state.addSystem(EffectEvent.class, new ItsMyTurnConditionSystem());
        state.addSystem(EffectEvent.class, new CanAttackConditionSystem());
        state.addSystem(EffectEvent.class, new TriggerConditionEffectSystem());
        state.addSystem(EffectEvent.class, new PayManaCostEffectSystem());
        
        state.addSystem(EffectEvent.class, new AttachTemplateEffectSystem());
        state.addSystem(EffectEvent.class, new DrawEffectSystem());
        state.addSystem(EffectEvent.class, new SetSameOwnerAsTriggerEffectSystem());
        state.addSystem(EffectEvent.class, new SummonEffectSystem());
        state.addSystem(EffectEvent.class, new HandDetachEffectSystem());
        state.addSystem(EffectEvent.class, new BoardAttachEffectSystem());
        state.addSystem(EffectEvent.class, new DealDamageEffectSystem());
        state.addSystem(EffectEvent.class, new DealRandomDamageEffectSystem());
        state.addSystem(EffectEvent.class, new AttackEffectSystem());
        state.addSystem(EffectEvent.class, new EndTurnEffectSystem());

        state.addSystem(EffectEvent.class, new PlayerLostEffectSystem());
//        state.addSystem(EffectEvent.class, new SpawnTokenEffectSystem());
        state.addSystem(EndTurnEvent.class, new EndTurnTriggerSystem());
        state.addSystem(EndTurnEvent.class, new EndTurnSystem());
        state.addSystem(EndTurnEvent.class, new NextTurnSystem());
        state.addSystem(FatigueEvent.class, new ApplyFatigueSystem());
        state.addSystem(GameStartEvent.class, new ApplyGameStartSystem());
        state.addSystem(GraveyardAttachEvent.class, new GraveyardAttachSystem());
        state.addSystem(GraveyardDetachEvent.class, new GraveyardDetachSystem());
        state.addSystem(HandAttachEvent.class, new HandAttachSystem());
//        state.addSystem(HandAttachEvent.class, new AttachSummonAbilitySystem());
        state.addSystem(HandAttachEvent.class, new AttachCastAbilitySystem());
        state.addSystem(HandDetachEvent.class, new HandDetachSystem());
//        state.addSystem(HandDetachEvent.class, new DetachSummonAbilitySystem());
        state.addSystem(HandDetachEvent.class, new DetachCastAbilitySystem());
        state.addSystem(HealEvent.class, new ApplyHealSystem());
//        state.addSystem(InitPlayerEvent.class, new InitPlayerSystem());
//        state.addSystem(InstantiateTemplateEvent.class, new InstantiateTemplateSystem());
        state.addSystem(LibraryAttachEvent.class, new LibraryAttachSystem());
        state.addSystem(LibraryDetachEvent.class, new LibraryDetachSystem());
        state.addSystem(ManaPaymentEvent.class, new ManaPaymentSystem());
        state.addSystem(PlayerLostEvent.class, new PlayerLostSystem());
        state.addSystem(PlayerLostEvent.class, new EndMatchSystem());
        state.addSystem(RequestDrawEvent.class, new RequestDrawSystem());
        state.addSystem(StartTurnEvent.class, new StartTurnSystem());
        state.addSystem(StartTurnEvent.class, new ManaUpkeepPhaseSystem());
        state.addSystem(StartTurnEvent.class, new DrawPhaseSystem());
        state.addSystem(StartTurnEvent.class, new RegenerationSystem());
        state.addSystem(StartTurnEvent.class, new RemoveSummonSicknessSystem());
        state.addSystem(StartTurnEvent.class, new RemoveAttackCountSystem());
        state.addSystem(SetHealthEvent.class, new SetHealthSystem());
        state.addSystem(SetHealthEvent.class, new HealthDeathSystem());
        state.addSystem(SetManaEvent.class, new SetManaSystem());
        state.addSystem(SetManaLimitEvent.class, new SetManaLimitSystem());
        state.addSystem(SetOwnerEvent.class, new SetOwnerSystem());
        state.addSystem(SummonEvent.class, new ApplySummonSystem());
        state.addSystem(SummonEvent.class, new BattlecrySystem());
        state.addSystem(SurrenderEvent.class, new SurrenderSystem());
        
        state.addSystem(TargetedTriggerEffectEvent.class, new TargetedTriggerEffectSystem());
        state.addSystem(TriggerEffectEvent.class, new CreateSingleTargetEntityEffectSystem());
        state.addSystem(TriggerEffectEvent.class, new SelectTargetsEffectSystem());
        state.addSystem(TriggerEffectEvent.class, new EffectPlayerTriggerConditionSystem());
        state.addSystem(TriggerEffectEvent.class, new TriggerEffectSystem());
    }

    public void startGame(MatchState state, EntityId player1, String[] lib1, EntityId player2, String[] lib2) {
        Random rng = new Random();
        initPlayer(state, player1, "Player1", "Hero", lib1, rng);
        initPlayer(state, player2, "Player2", "Hero", lib2, rng);

        state.events.fireEvent(new GameStartEvent());
        state.data.set(player1, new NextTurnPlayerComponent(player2));
        state.data.set(player2, new NextTurnPlayerComponent(player1));

        EntityId startingPlayer = rng.nextBoolean() ? player1 : player2;
        for (int i = 0; i < 3; i++) {
            state.events.fireEvent(new RequestDrawEvent(player2));
            state.events.fireEvent(new RequestDrawEvent(player1));
        }
        state.events.fireEvent(new RequestDrawEvent(startingPlayer == player1 ? player2 : player1));
        state.events.fireEvent(new StartTurnEvent(startingPlayer));
        state.events.handleEvents();
    }

    private void initPlayer(MatchState state, EntityId player, String name, String heroTemplate, String[] library, Random rng) {
        shuffle(library, rng);//TODO: make shuffle event instead
        state.data.set(player, new NameComponent(name));
        state.data.set(player, new PlayerComponent());

        EntityId hero = state.idFactory.createEntity();
        state.events.fireEvent(new AttachTemplateEvent(hero, heroTemplate, player, null));
        state.events.fireEvent(new BoardAttachEvent(hero));

        for (String template : library) {
            EntityId cardId = state.idFactory.createEntity();
            state.events.fireEvent(new AttachTemplateEvent(cardId, template, player, null));
            state.events.fireEvent(new LibraryAttachEvent(cardId));
        }
    }
    
    private void shuffle(String[] array, Random rng) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = rng.nextInt(i);
            swap(array, i, j);
        }
    }
    
    private void swap(String[] array, int i , int j) {
        String tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
