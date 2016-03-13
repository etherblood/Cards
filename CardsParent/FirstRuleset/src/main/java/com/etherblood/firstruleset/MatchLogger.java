package com.etherblood.firstruleset;

import com.etherblood.cardsmatch.cardgame.EntityUtils;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.firstruleset.logic.battle.MinionComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
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
            String summoned = data.has(damage.minion, MinionComponent.class)? " summoned ": " cast ";
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
        return EntityUtils.toString(data, entity);
    }

}
