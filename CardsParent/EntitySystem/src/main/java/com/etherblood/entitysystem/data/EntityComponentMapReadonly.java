package com.etherblood.entitysystem.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public interface EntityComponentMapReadonly {

    <T extends EntityComponent> boolean has(EntityId entity, Class<T> componentClass);
    <T extends EntityComponent> T get(EntityId entity, Class<T> componentClass);
    Set<EntityId> entities(Class componentType);
    Set<EntityId> allEntities();
    List<EntityComponent> components(EntityId entity);
    Set<Class> registeredComponentClasses();
    void extractMappingsForComponentType(Class componentType, Map<EntityId, EntityComponent> dest);
}
