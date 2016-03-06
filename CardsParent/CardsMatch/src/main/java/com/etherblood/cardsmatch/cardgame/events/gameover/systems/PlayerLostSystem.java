/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.gameover.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.match.Autowire;
import com.etherblood.cardsmatch.cardgame.components.player.LoserComponent;
import com.etherblood.cardsmatch.cardgame.events.gameover.PlayerLostEvent;
import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public class PlayerLostSystem extends AbstractMatchSystem<PlayerLostEvent> {
    @Autowire
    private EntityComponentMap data;

    @Override
    public PlayerLostEvent handle(PlayerLostEvent event) {
        data.set(event.player, new LoserComponent());
//        System.out.println("GAME OVER");
        return event;
    }
    
}
