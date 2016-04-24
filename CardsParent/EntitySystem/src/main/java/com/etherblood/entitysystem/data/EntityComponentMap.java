package com.etherblood.entitysystem.data;

/**
 *
 * @author Philipp
 */
public interface EntityComponentMap extends EntityComponentMapReadonly {
    <T extends EntityComponent> T set(EntityId entity, T component);
    <T extends EntityComponent> T remove(EntityId entity, Class<T> componentClass);
    
    void clear(EntityId entity);
    void copyFrom(EntityComponentMapReadonly source);
}
