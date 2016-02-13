/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.filters;

import com.etherblood.entitysystem.data.EntityComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class FilterQuery {
    private Class fromClass = null;
    private final ArrayList<ComponentFilter> whereFilters = new ArrayList<>();

    public List<EntityId> list(EntityComponentMapReadonly data) {
        return list(data, new ArrayList<EntityId>());
    }
    
    private List<EntityId> list(EntityComponentMapReadonly data, ArrayList<EntityId> entities) {
        if(fromClass == null) {
            System.err.println("WARNING: null was used as baseFilter class, this might cause performance issues!");
        }
        for (EntityId entityId : data.entities(fromClass)) {
            if(passesFilters(data, entityId)) {
                entities.add(entityId);
            }
        }
        return entities;
    }
    
    public int count(EntityComponentMapReadonly data) {
        if(whereFilters.isEmpty()) {
            return data.entities(fromClass).size();
        }
        return list(data).size();
    }
    
    public EntityId first(EntityComponentMapReadonly data) {
        List<EntityId> list = list(data);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public <T extends EntityComponent> EntityId max(EntityComponentMapReadonly data, Class<T> componentClass, Comparator<T> comparator) {
        List<EntityId> list = list(data);
        if(list.isEmpty()) {
            return null;
        }
        EntityId best = list.get(0);
        T bestComp = data.get(best, componentClass);
        for (int i = 1; i < list.size(); i++) {
            EntityId current = list.get(i);
            T currentComp = data.get(current, componentClass);
            if(comparator.compare(currentComp, bestComp) < 0) {
                best = current;
                bestComp = currentComp;
            }
        }
        return best;
    }

    public FilterQuery setBaseClass(Class componentClass) {
        if(fromClass != null) {
            System.out.println("WARNING, base class was overwritten " + fromClass);
        }
        this.fromClass = componentClass;
        return this;
    }
    
    public FilterQuery addComponentClassFilter(Class componentClass) {
        addComponentFilter(new ComponentClassFilter(componentClass));
        return this;
    }
    
    public FilterQuery addComponentFilter(ComponentFilter filter) {
        whereFilters.add(filter);
        return this;
    }
    
    public boolean passesFilters(EntityComponentMapReadonly data, EntityId entityId) {
        for (ComponentFilter filter : whereFilters) {
            if(!filter.passesFilter(data.get(entityId, filter.getComponentType()))) {
                return false;
            }
        }
        return true;
    }
}
