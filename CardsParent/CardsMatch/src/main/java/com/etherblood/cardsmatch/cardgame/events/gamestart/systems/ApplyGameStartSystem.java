/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.events.gamestart.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.gamestart.GameStartEvent;

/**
 *
 * @author Philipp
 */
public class ApplyGameStartSystem extends AbstractMatchSystem<GameStartEvent> {

    @Override
    public GameStartEvent handle(GameStartEvent event) {
        return event;
    }
    
}
