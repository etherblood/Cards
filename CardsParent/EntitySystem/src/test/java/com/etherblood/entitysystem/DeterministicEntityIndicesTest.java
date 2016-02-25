package com.etherblood.entitysystem;

import com.etherblood.entitysystem.data.TestEntityFactory;
import com.etherblood.entitysystem.data.EntityId;
import com.etherblood.entitysystem.util.DeterministicEntityIndices;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import junit.framework.TestCase;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Philipp
 */
public class DeterministicEntityIndicesTest extends TestCase {

    @Test
    public void testEntityFromIndex() {
        DeterministicEntityIndices indices = new DeterministicEntityIndices();
        ArrayList<EntityId> entities = createRandomEntities(20);

        int index = 7;
        EntityId actual = indices.getEntityForDeterministicIndex(entities, index);
        EntityId expected = getExpected(entities, index);

        assertEquals(expected, actual);
    }

    @Test
    public void testIndexFromEntity() {
        DeterministicEntityIndices indices = new DeterministicEntityIndices();
        ArrayList<EntityId> entities = createRandomEntities(20);

        EntityId entity = entities.get(7);
        int actual = indices.getDeterministicIndexForEntity(entities, entity);
        int expected = getExpected(entities, entity);

        assertEquals(expected, actual);
    }

    private EntityId getExpected(ArrayList<EntityId> entities, int index) {
        ArrayList<EntityId> tmp = new ArrayList<>(entities);
        Collections.sort(tmp);
        return tmp.get(index);
    }

    private int getExpected(ArrayList<EntityId> entities, EntityId entity) {
        ArrayList<EntityId> tmp = new ArrayList<>(entities);
        Collections.sort(tmp);
        return tmp.indexOf(entity);
    }

    private ArrayList<EntityId> createRandomEntities(int count) {
        Random rng = new Random();
        ArrayList<EntityId> entities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            EntityId entity;
            do {
                entity = TestEntityFactory.createEntity(rng.nextLong());
            } while (entities.contains(entity));
            entities.add(entity);
        }
        return entities;
    }
}
