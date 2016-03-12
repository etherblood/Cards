package com.etherblood.cardsmatch.cardgame.components.effects.effects;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
@ComponentAlias(name="attachTemplate")
public class AttachTemplateEffectComponent implements EntityComponent {
    public final String template;

    public AttachTemplateEffectComponent(String template) {
        this.template = template;
    }
}
