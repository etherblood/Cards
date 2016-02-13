/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.components.effects.effects;

import com.etherblood.cardsmatch.cardgame.components.effects.effects.util.SelectionFilter;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class SetOwnerEffectComponent implements EntityComponent {
    public final SelectionFilter filter;

    public SetOwnerEffectComponent(SelectionFilter filter) {
        this.filter = filter;
    }
}
