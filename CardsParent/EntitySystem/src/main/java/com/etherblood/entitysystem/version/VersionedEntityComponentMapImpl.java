package com.etherblood.entitysystem.version;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.data.EntityComponentMap;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VersionedEntityComponentMapImpl implements VersionedEntityComponentMap {

    private final EntityComponentMap map;
    private final ArrayList<ComponentModification> logs = new ArrayList<>();

    public VersionedEntityComponentMapImpl(EntityComponentMap map) {
        this.map = map;
    }

    @Override
    public int getVersion() {
        return logs.size();
    }

    @Override
    public void revertTo(int version) {
        while (getVersion() > version) {
            revertLast();
        }
        if (version != getVersion()) {
            throw new RuntimeException("rollback of matchData failed");
        }
    }

    private void revertLast() {
        ComponentModification modification = logs.remove(logs.size() - 1);
        revertComponentModification(modification);
    }

    private void revertComponentModification(ComponentModification compMod) {
        if (compMod.after != null) {
            map.remove(compMod.entity, compMod.after.getClass());
        }
        if (compMod.before != null) {
            map.set(compMod.entity, compMod.before);
        }
    }

    @Override
    public <T extends EntityComponent> T set(EntityId entity, T component) {
        T oldComponent = map.set(entity, component);
        logs.add(new ComponentModification<T>(entity, oldComponent, component));
        return oldComponent;
    }

    @Override
    public <T extends EntityComponent> T remove(EntityId entity, Class<T> componentClass) {
        T oldComponent = map.remove(entity, componentClass);
        logs.add(new ComponentModification<T>(entity, oldComponent, null));
        return oldComponent;
    }

    @Override
    public void clear(EntityId entity) {
        for (EntityComponent component : map.components(entity)) {
            remove(entity, component.getClass());
        }
//        map.clear(entity);
    }

    @Override
    public <T extends EntityComponent> T get(EntityId entity, Class<T> componentClass) {
        return map.get(entity, componentClass);
    }

//    @Override
//    public boolean has(int entity, Class componentClass) {
//        return map.has(entity, componentClass);
//    }
//    @Override
//    public boolean hasAllComponents(EntityId entity, Class... componentsClasses) {
//        return map.hasAllComponents(entity, componentsClasses);
//    }
//    @Override
//    public boolean hasAnyComponent(int entity, Class... componentsClasses) {
//        return map.hasAnyComponent(entity, componentsClasses);
//    }
    @Override
    public List<EntityComponent> components(EntityId entity) {
        return map.components(entity);
    }

//    @Override
//    public List<EntityId> getEntitiesWithAll(Class... componentsClasses) {
//        return map.getEntitiesWithAll(componentsClasses);
//    }
//    @Override
//    public Set<Integer> getEntitiesWithAny(Class... componentsClasses) {
//        return map.getEntitiesWithAny(componentsClasses);
//    }
//
//    @Override
//    public boolean hasEntity(int entity) {
//        return map.hasEntity(entity);
//    }
//    @Override
//    public Set<Integer> getEntitiesWithValue(Field componentField, Object componentValue) {
//        return  map.getEntitiesWithValue(componentClass, componentField, componentValue);
//    }
//    @Override
//    public List<EntityId> findEntities(ComponentFilter... filters) {
//        return map.findEntities(filters);
//    }
//
//    @Override
//    public boolean passesAllFilters(EntityId entity, ComponentFilter... filters) {
//        return map.passesAllFilters(entity, filters);
//    }
    @Override
    public Set<EntityId> entities(Class componentType) {
        return map.entities(componentType);
    }

    @Override
    public Set<EntityId> allEntities() {
        return map.allEntities();
    }

    @Override
    public <T extends EntityComponent> boolean has(EntityId entity, Class<T> componentClass) {
        return map.has(entity, componentClass);
    }

    @Override
    public Set<Class> registeredComponentClasses() {
        return map.registeredComponentClasses();
    }

    @Override
    public void copyFrom(EntityComponentMapReadonly source) {
        logs.clear();
        map.copyFrom(source);
    }

    @Override
    public void extractMappingsForComponentType(Class componentType, Map<EntityId, EntityComponent> dest) {
        map.extractMappingsForComponentType(componentType, dest);
    }

}