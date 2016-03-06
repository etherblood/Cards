package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.cardsmatch.cardgame.components.battle.MinionComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.OwnerComponent;
import com.etherblood.cardsmatch.cardgame.events.gamestart.ShuffleLibraryEvent;
import com.etherblood.cardsmatch.cardgame.events.attack.AttackEvent;
import com.etherblood.cardsmatch.cardgame.events.attack.systems.ApplyAttackSystem;
import com.etherblood.cardsmatch.cardgame.events.damage.SetDivineShieldEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.DamageEvent;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.ApplyDamageSystem;
import com.etherblood.cardsmatch.cardgame.events.damage.systems.SetDivineShieldSystem;
import com.etherblood.cardsmatch.cardgame.events.death.DeathEvent;
import com.etherblood.cardsmatch.cardgame.events.death.systems.ApplyDeathSystem;
import com.etherblood.cardsmatch.cardgame.events.draw.DrawEvent;
import com.etherblood.cardsmatch.cardgame.events.draw.systems.ApplyDrawSystem;
import com.etherblood.cardsmatch.cardgame.events.startTurn.StartTurnEvent;
import com.etherblood.cardsmatch.cardgame.events.startTurn.systems.StartTurnSystem;
import com.etherblood.cardsmatch.cardgame.events.summon.SummonEvent;
import com.etherblood.cardsmatch.cardgame.events.summon.systems.ApplySummonSystem;
import com.etherblood.cardsmatch.cardgame.events.gamestart.systems.ShuffleLibrarySystem;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.eventsystem.GameEvent;

/**
 *
 * @author Philipp
 */
public class MatchLogger implements SystemsEventHandler {
    private final EntityComponentMapReadonly data;

    public MatchLogger(EntityComponentMapReadonly data) {
        this.data = data;
    }
    
    @Override
    public void onEvent(Class systemClass, GameEvent gameEvent) {
        if(systemClass == ApplyAttackSystem.class) {
            AttackEvent attack = (AttackEvent) gameEvent;
            System.out.println(toString(attack.source) + " attacked " + toString(attack.target));
        } else if(systemClass == ApplyDamageSystem.class) {
            DamageEvent damage = (DamageEvent) gameEvent;
            System.out.println(toString(damage.target) + " took " + damage.damage + " damage");
        } else if(systemClass == ApplySummonSystem.class) {
            SummonEvent damage = (SummonEvent) gameEvent;
            String summoned = data.has(damage.minion, MinionComponent.class)? " summoned ": " casted ";
            System.out.println(toString(data.get(damage.minion, OwnerComponent.class).player) + summoned + toString(damage.minion));
        } else if(systemClass == ApplyDeathSystem.class) {
            DeathEvent damage = (DeathEvent) gameEvent;
            System.out.println(toString(damage.entity) + " died");
        } else if(systemClass == StartTurnSystem.class) {
            StartTurnEvent endTurn = (StartTurnEvent) gameEvent;
            System.out.println(toString(endTurn.player) + "'s turn");
        } else if(systemClass == SetDivineShieldSystem.class) {
            SetDivineShieldEvent damage = (SetDivineShieldEvent) gameEvent;
            if(!damage.value) {
                System.out.println(toString(damage.target) + " lost DIVINE SHIELD");
            }
        } else if(systemClass == ApplyDrawSystem.class) {
            DrawEvent draw = (DrawEvent) gameEvent;
            System.out.println(toString(data.get(draw.card, OwnerComponent.class).player) + " drew " + toString(draw.card));
        } else if(systemClass == ShuffleLibrarySystem.class) {
            ShuffleLibraryEvent shuffle = (ShuffleLibraryEvent) gameEvent;
            System.out.println(toString(shuffle.player) + "'s library was shuffled.");
        }
    }
    
    private String toString(EntityId entity) {
        return entity.toString() + "_" + data.get(entity, NameComponent.class).name;
    }

}
