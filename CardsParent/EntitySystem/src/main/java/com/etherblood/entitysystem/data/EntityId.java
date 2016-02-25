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
        return (int) id;
    }
    public boolean equals(EntityId obj) {
        return id == obj.id;
    }

    public long longValue() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id;
    }

    @Override
    public int compareTo(EntityId o) {
        return Long.compare(id, o.id);
    }
}