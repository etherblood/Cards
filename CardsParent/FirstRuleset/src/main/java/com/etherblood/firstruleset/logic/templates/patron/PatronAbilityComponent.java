package com.etherblood.firstruleset.logic.templates.patron;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name="patronAbility")
public class PatronAbilityComponent implements EntityComponent {
    public final String template;

    public PatronAbilityComponent(String template) {
        this.template = template;
    }
}
