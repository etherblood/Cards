package com.etherblood.firstruleset;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardslogging.LogLevel;
import com.etherblood.cardslogging.Logger;
import com.etherblood.cardsmatch.cardgame.EntityUtils;
import com.etherblood.firstruleset.logic.battle.MinionComponent;
import com.etherblood.firstruleset.logic.player.OwnerComponent;
import com.etherblood.firstruleset.logic.shuffle.ShuffleLibraryEvent;
import com.etherblood.firstruleset.logic.attack.AttackEvent;
import com.etherblood.firstruleset.logic.attack.systems.ApplyAttackSystem;
import com.etherblood.firstruleset.logic.damage.SetDivineShieldEvent;
import com.etherblood.firstruleset.logic.damage.DamageEvent;
import com.etherblood.firstruleset.logic.damage.systems.ApplyDamageSystem;
import com.etherblood.firstruleset.logic.damage.systems.SetDivineShieldSystem;
import com.etherblood.firstruleset.logic.death.DeathEvent;
import com.etherblood.firstruleset.logic.death.systems.ApplyDeathSystem;
import com.etherblood.firstruleset.logic.draw.DrawEvent;
import com.etherblood.firstruleset.logic.draw.systems.ApplyDrawSystem;
import com.etherblood.firstruleset.logic.startTurn.StartTurnEvent;
import com.etherblood.firstruleset.logic.startTurn.systems.StartTurnSystem;
import com.etherblood.firstruleset.logic.summon.SummonEvent;
import com.etherblood.firstruleset.logic.summon.systems.ApplySummonSystem;
import com.etherblood.firstruleset.logic.shuffle.ShuffleLibrarySystem;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;
import com.etherblood.cardsmatch.cardgame.GlobalEventHandler;

/**
 *
 * @author Philipp
 */
public class MatchLogger implements GlobalEventHandler {
    @Autowire
    private EntityComponentMapReadonly data;
    @Autowire
    private Logger logger;
    
    @Override
    public void onEvent(Class systemClass, GameEvent gameEvent) {
        if(systemClass == ApplyAttackSystem.class) {
            AttackEvent attack = (AttackEvent) gameEvent;
            logger.log(LogLevel.INFO, toString(attack.source) + " attacked " + toString(attack.target));
        } else if(systemClass == ApplyDamageSystem.class) {
            DamageEvent damage = (DamageEvent) gameEvent;
            logger.log(LogLevel.INFO, toString(damage.target) + " took " + damage.damage + " damage");
        } else if(systemClass == ApplySummonSystem.class) {
            SummonEvent damage = (SummonEvent) gameEvent;
            String summoned = data.has(damage.minion, MinionComponent.class)? " summoned ": " cast ";
            logger.log(LogLevel.INFO, ownerToString(damage.minion) + summoned + toString(damage.minion));
        } else if(systemClass == ApplyDeathSystem.class) {
            DeathEvent death = (DeathEvent) gameEvent;
            logger.log(LogLevel.INFO, toString(death.entity) + " died");
        } else if(systemClass == StartTurnSystem.class) {
            StartTurnEvent startTurn = (StartTurnEvent) gameEvent;
            logger.log(LogLevel.INFO, toString(startTurn.player) + "'s turn");
        } else if(systemClass == SetDivineShieldSystem.class) {
            SetDivineShieldEvent divine = (SetDivineShieldEvent) gameEvent;
            if(!divine.value) {
            logger.log(LogLevel.INFO, toString(divine.target) + " lost DIVINE SHIELD");
            }
        } else if(systemClass == ApplyDrawSystem.class) {
            DrawEvent draw = (DrawEvent) gameEvent;
            logger.log(LogLevel.INFO, ownerToString(draw.card) + " drew " + toString(draw.card));
        } else if(systemClass == ShuffleLibrarySystem.class) {
            ShuffleLibraryEvent shuffle = (ShuffleLibraryEvent) gameEvent;
            logger.log(LogLevel.INFO, toString(shuffle.player) + "'s library was shuffled.");
        }
    }

    private String ownerToString(EntityId entity) {
        return toString(data.get(entity, OwnerComponent.class).player);
    }
    
    private String toString(EntityId entity) {
        return EntityUtils.toString(data, entity);
    }

}
