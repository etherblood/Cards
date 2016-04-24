package com.etherblood.entitysystem.version;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
class ComponentModification<T extends EntityComponent> {
    public final EntityId entity;
    public final T before, after;

    public ComponentModification(EntityId entity, T before, T after) {
        this.entity = entity;
        this.before = before;
        this.after = after;
    }
}
