package com.etherblood.cardsmatch.cardgame.components.effects.effects.targeting;

import com.etherblood.cardsmatch.cardgame.components.effects.effects.util.SelectionFilter;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class SelectEffectTargetsComponent implements EntityComponent {
    public final SelectionFilter filter;

    public SelectEffectTargetsComponent(SelectionFilter filter) {
        this.filter = filter;
    }
}
