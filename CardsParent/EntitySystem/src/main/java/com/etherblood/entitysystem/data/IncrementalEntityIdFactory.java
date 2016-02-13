/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.data;

/**
 *
 * @author Philipp
 */
public class IncrementalEntityIdFactory implements EntityIdFactory {
    private long nextId = 0;
    
    @Override
    public EntityId createEntity() {
        return new EntityId(nextId++);
    }
}
