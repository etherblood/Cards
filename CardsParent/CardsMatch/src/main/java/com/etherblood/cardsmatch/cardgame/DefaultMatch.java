/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame;

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
public class DefaultMatch extends MatchState {

    public DefaultMatch(TemplateSet templates, SystemsEventHandler eventLogger) {
        super(templates, eventLogger);
        initSystems();
    }

    private void initSystems() {
        addSystem(ApplyEndTurnEvent.class, new ApplyEndTurnSystem());
        addSystem(AttachTemplateEvent.class, new AttachTemplateSystem());
        addSystem(AttackEvent.class, new IncreaseAttackCountSystem());
        addSystem(AttackEvent.class, new ApplyAttackSystem());
        addSystem(BoardAttachEvent.class, new BoardAttachSystem());
        addSystem(BoardAttachEvent.class, new AttachAttackAbilitySystem());
        addSystem(BoardAttachEvent.class, new AttachSummoningSicknessSystem());
        addSystem(BoardDetachEvent.class, new BoardDetachSystem());
        addSystem(BoardDetachEvent.class, new DetachAttackAbilitySystem());
        addSystem(SetDivineShieldEvent.class, new SetDivineShieldSystem());
        addSystem(CardZoneMoveEvent.class, new CardZoneMoveSystem());
//        addSystem(ClearEffectTargetsEvent.class, new ClearEffectTargetsSystem());
        addSystem(DamageEvent.class, new DivineShieldSystem());
        addSystem(DamageEvent.class, new ApplyDamageSystem());
        addSystem(DeathEvent.class, new ApplyDeathSystem());
        addSystem(DeathEvent.class, new DeathrattleSystem());
        addSystem(DeleteEntityEvent.class, new DeleteEntityTriggerChildsSystem());
        addSystem(DeleteEntityEvent.class, new DeleteEntitySystem());
        addSystem(DrawEvent.class, new ApplyDrawSystem());

        addSystem(EffectEvent.class, new ItsMyTurnConditionSystem());
        addSystem(EffectEvent.class, new CanAttackConditionSystem());
        addSystem(EffectEvent.class, new TriggerConditionEffectSystem());
        addSystem(EffectEvent.class, new PayManaCostEffectSystem());
        
        addSystem(EffectEvent.class, new AttachTemplateEffectSystem());
        addSystem(EffectEvent.class, new DrawEffectSystem());
        addSystem(EffectEvent.class, new SetSameOwnerAsTriggerEffectSystem());
        addSystem(EffectEvent.class, new SummonEffectSystem());
        addSystem(EffectEvent.class, new HandDetachEffectSystem());
        addSystem(EffectEvent.class, new BoardAttachEffectSystem());
        addSystem(EffectEvent.class, new DealDamageEffectSystem());
        addSystem(EffectEvent.class, new DealRandomDamageEffectSystem());
        addSystem(EffectEvent.class, new AttackEffectSystem());
        addSystem(EffectEvent.class, new EndTurnEffectSystem());

        addSystem(EffectEvent.class, new PlayerLostEffectSystem());
//        addSystem(EffectEvent.class, new SpawnTokenEffectSystem());
        addSystem(EndTurnEvent.class, new EndTurnTriggerSystem());
        addSystem(EndTurnEvent.class, new EndTurnSystem());
        addSystem(EndTurnEvent.class, new NextTurnSystem());
        addSystem(FatigueEvent.class, new ApplyFatigueSystem());
        addSystem(GameStartEvent.class, new ApplyGameStartSystem());
        addSystem(GraveyardAttachEvent.class, new GraveyardAttachSystem());
        addSystem(GraveyardDetachEvent.class, new GraveyardDetachSystem());
        addSystem(HandAttachEvent.class, new HandAttachSystem());
//        addSystem(HandAttachEvent.class, new AttachSummonAbilitySystem());
        addSystem(HandAttachEvent.class, new AttachCastAbilitySystem());
        addSystem(HandDetachEvent.class, new HandDetachSystem());
//        addSystem(HandDetachEvent.class, new DetachSummonAbilitySystem());
        addSystem(HandDetachEvent.class, new DetachCastAbilitySystem());
        addSystem(HealEvent.class, new ApplyHealSystem());
//        addSystem(InitPlayerEvent.class, new InitPlayerSystem());
//        addSystem(InstantiateTemplateEvent.class, new InstantiateTemplateSystem());
        addSystem(LibraryAttachEvent.class, new LibraryAttachSystem());
        addSystem(LibraryDetachEvent.class, new LibraryDetachSystem());
        addSystem(ManaPaymentEvent.class, new ManaPaymentSystem());
        addSystem(PlayerLostEvent.class, new PlayerLostSystem());
        addSystem(PlayerLostEvent.class, new EndMatchSystem());
        addSystem(RequestDrawEvent.class, new RequestDrawSystem());
        addSystem(StartTurnEvent.class, new StartTurnSystem());
        addSystem(StartTurnEvent.class, new ManaUpkeepPhaseSystem());
        addSystem(StartTurnEvent.class, new DrawPhaseSystem());
        addSystem(StartTurnEvent.class, new RegenerationSystem());
        addSystem(StartTurnEvent.class, new RemoveSummonSicknessSystem());
        addSystem(StartTurnEvent.class, new RemoveAttackCountSystem());
        addSystem(SetHealthEvent.class, new SetHealthSystem());
        addSystem(SetHealthEvent.class, new HealthDeathSystem());
        addSystem(SetManaEvent.class, new SetManaSystem());
        addSystem(SetManaLimitEvent.class, new SetManaLimitSystem());
        addSystem(SetOwnerEvent.class, new SetOwnerSystem());
        addSystem(SummonEvent.class, new ApplySummonSystem());
        addSystem(SummonEvent.class, new BattlecrySystem());
        addSystem(SurrenderEvent.class, new SurrenderSystem());
        
        addSystem(TargetedTriggerEffectEvent.class, new TargetedTriggerEffectSystem());
        addSystem(TriggerEffectEvent.class, new CreateSingleTargetEntityEffectSystem());
        addSystem(TriggerEffectEvent.class, new SelectTargetsEffectSystem());
        addSystem(TriggerEffectEvent.class, new EffectPlayerTriggerConditionSystem());
        addSystem(TriggerEffectEvent.class, new TriggerEffectSystem());
    }

    public void startGame(EntityId player1, String[] lib1, EntityId player2, String[] lib2) {
        Random rng = new Random();
        initPlayer(player1, "Player1", "Hero", lib1, rng);
        initPlayer(player2, "Player2", "Hero", lib2, rng);

        events.fireEvent(new GameStartEvent());
        data.set(player1, new NextTurnPlayerComponent(player2));
        data.set(player2, new NextTurnPlayerComponent(player1));

        EntityId startingPlayer = rng.nextBoolean() ? player1 : player2;
        for (int i = 0; i < 3; i++) {
            events.fireEvent(new RequestDrawEvent(player2));
            events.fireEvent(new RequestDrawEvent(player1));
        }
        events.fireEvent(new RequestDrawEvent(startingPlayer == player1 ? player2 : player1));
        events.fireEvent(new StartTurnEvent(startingPlayer));
        events.handleEvents();
    }

    private void initPlayer(EntityId player, String name, String heroTemplate, String[] library, Random rng) {
        shuffle(library, rng);//TODO: make shuffle event instead
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
