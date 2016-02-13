/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.entitysystem.data;

/**
 *
 * @author Philipp
 */
public final class EntityId implements Comparable<EntityId> {
    private final long id;

    protected EntityId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityId && equals((EntityId)obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    public boolean equals(EntityId obj) {
        return this.id == obj.id;
    }

    @Override
    public String toString() {
        return "#" + id;
    }

    @Override
    public int compareTo(EntityId o) {
        return (int) (o.id - id);
    }
}