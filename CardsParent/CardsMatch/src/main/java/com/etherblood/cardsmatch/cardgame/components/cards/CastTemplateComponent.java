package com.etherblood.cardsmatch.cardgame.components.cards;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name="castEffect")
public class CastTemplateComponent implements EntityComponent {
    public final String template;

    public CastTemplateComponent(String template) {
        this.template = template;
    }
}
