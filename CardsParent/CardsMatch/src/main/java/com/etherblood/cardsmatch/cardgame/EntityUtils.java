package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.entitysystem.data.EntityComponentMapReadonly;
import com.etherblood.entitysystem.data.EntityId;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class EntityUtils {

    public static String toString(EntityComponentMapReadonly data, EntityId entity) {
        return entity.toString() + "_" + data.get(entity, NameComponent.class).name;
    }

    public static String toString(EntityComponentMapReadonly data, EntityId[] entities) {
        return toString(data, Arrays.asList(entities));
    }

    public static String toString(EntityComponentMapReadonly data, List<EntityId> entities) {
        String result = "[";
        for (int i = 0; i < entities.size(); i++) {
            if (i != 0) {
                result += ", ";
            }
            result += toString(data, entities.get(i));
        }
        result += "]";
        return result;
    }
}
