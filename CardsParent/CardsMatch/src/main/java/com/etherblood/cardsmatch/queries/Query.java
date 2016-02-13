package com.etherblood.cardsmatch.queries;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.filters.ComponentFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class Query {
    private final EntityComponentMapReadonly data;
    private Class<? extends EntityComponent>[] selectComponentClasses;
    private Class<? extends EntityComponent> fromComponentClass;
    private ArrayList<ComponentFilter<? extends EntityComponent>> filters = new ArrayList<>();

    public Query(EntityComponentMapReadonly data) {
        this.data = data;
    }
    
    public Query select(Class<? extends EntityComponent>... componentClasses) {
        selectComponentClasses = componentClasses;
        return this;
    }
    
    public Query from(Class<? extends EntityComponent> componentClass) {
        fromComponentClass = componentClass;
        return this;
    }
    
    public Query where(ComponentFilter<? extends EntityComponent> filter) {
        filters.add(filter);
        return this;
    }
    
    public List<EntityView> list() {
        if(fromComponentClass == null) {
            System.err.println("WARNING: no from class specified.");
        }
        List<EntityView> entities = new ArrayList<>();
        for (EntityId entity : data.entities(fromComponentClass)) {
            if(passesFilters(data, entity)) {
                entities.add(createView(entity));
            }
        }
        return entities;
    }
    
    public int count() {
        if(fromComponentClass == null) {
            System.err.println("WARNING: no from class specified.");
        }
        int amount = 0;
        for (EntityId entity : data.entities(fromComponentClass)) {
            if(passesFilters(data, entity)) {
                amount++;
            }
        }
        return amount;
    }
    
    private boolean passesFilters(EntityComponentMapReadonly data, EntityId entityId) {
        for (ComponentFilter filter : filters) {
            if(!filter.passesFilter(data.get(entityId, filter.getComponentType()))) {
                return false;
            }
        }
        return true;
    }
    
    private EntityView createView(EntityId entity) {
        if(selectComponentClasses == null) {
            return new EntityView(entity);
        }
        EntityComponent[] components = new EntityComponent[selectComponentClasses.length];
        for (int i = 0; i < components.length; i++) {
            components[i] = data.get(entity, selectComponentClasses[i]);
        }
        return new EntityView(entity, components);
    }
    
}
