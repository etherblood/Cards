/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.data;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class EntityComponentMapImpl implements EntityComponentMap, EntityComponentMapReadonly {
    private final HashMap<Class, HashMap<EntityId, EntityComponent>> componentMaps = new HashMap<Class, HashMap<EntityId, EntityComponent>>();
    
    private HashMap<EntityId, EntityComponent> getComponentMap(Class componentClass) {
        HashMap<EntityId, EntityComponent> componentMap = componentMaps.get(componentClass);
        if (componentMap == null) {
            componentMap = new HashMap<EntityId, EntityComponent>();
            componentMaps.put(componentClass, componentMap);
        }
        return componentMap;
    }

    @Override
    public <T extends EntityComponent> T get(EntityId entity, Class<T> componentClass) {
        return (T) getComponentMap(componentClass).get(entity);
    }
    
    @Override
    public <T extends EntityComponent> boolean has(EntityId entity, Class<T> componentClass) {
        return getComponentMap(componentClass).containsKey(entity);
    }

    @Override
    public <T extends EntityComponent> T set(EntityId entity, T component) {
        return (T) getComponentMap(component.getClass()).put(entity, component);
    }

    @Override
    public <T extends EntityComponent> T remove(EntityId entity, Class<T> componentClass) {
        return (T) getComponentMap(componentClass).remove(entity);
    }

    @Override
    public List<EntityComponent> components(EntityId entity) {
        ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
        EntityComponent component;
        for (HashMap<EntityId, EntityComponent> componentMap : componentMaps.values()) {
            component = componentMap.get(entity);
            if (component != null) {
                components.add(component);
            }
        }
        return components;
    }

    @Override
    public void clear(EntityId entity) {
        for (Object component : components(entity)) {
            remove(entity, (Class)component.getClass());
        }
    }

    @Override
    public Set<EntityId> entities(Class componentType) {
        if(componentType == null) {
            return allEntities();
        }
        return getComponentMap(componentType).keySet();
    }
    @Override
    public Set<EntityId> allEntities() {
        HashSet<EntityId> entities = new HashSet<>();
        for (HashMap<EntityId, EntityComponent> componentMap : componentMaps.values()) {
            entities.addAll(componentMap.keySet());
        }
        return entities;
    }

    @Override
    public Set<Class> registeredComponentClasses() {
        return componentMaps.keySet();
    }
}
