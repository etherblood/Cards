package com.etherblood.cardsmatch.cardgame.components.effects.effects;

import com.etherblood.entitysystem.data.EntityComponent;

/**
 *
 * @author Philipp
 */
public class AttachTemplateEffectComponent implements EntityComponent {
    public final String template;

    public AttachTemplateEffectComponent(String template) {
        this.template = template;
    }
}
