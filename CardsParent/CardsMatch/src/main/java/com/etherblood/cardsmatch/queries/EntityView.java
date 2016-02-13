package com.etherblood.cardsmatch.queries;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class EntityView {
    private final EntityId id;
    private final EntityComponent[] components;

    public EntityView(EntityId id, EntityComponent... components) {
        this.id = id;
        this.components = components;
    }

    public EntityId getId() {
        return id;
    }
    
    public <T extends EntityComponent> T get(Class<T> componentClass) {
        for (EntityComponent component : components) {
            if(component.getClass() == componentClass) {
                return (T) component;
            }
        }
        return null;
    }
    
    public <T extends EntityComponent> boolean has(Class<T> componentClass) {
        return get(componentClass) != null;
    }

    @Override
    public int hashCode() {
        return 7 + 31 * id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityView other = (EntityView) obj;
        return id.equals(other.id);
    }
}
