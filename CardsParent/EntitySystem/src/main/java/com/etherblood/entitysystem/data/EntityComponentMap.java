/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.data;

/**
 *
 * @author Philipp
 */
public interface EntityComponentMap extends EntityComponentMapReadonly {
//    int createEntity();
//    void removeEntity(int entity);
    <T extends EntityComponent> T set(EntityId entity, T component);
    <T extends EntityComponent> T remove(EntityId entity, Class<T> componentClass);
    
    void clear(EntityId entity);
    void copyFrom(EntityComponentMapReadonly source);
}
