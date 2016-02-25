package com.etherblood.entitysystem.data;

/**
 *
 * @author Philipp
 */
public class TestEntityFactory {
    public static EntityId createEntity(long id) {
        return new EntityId(id);
    }
}
