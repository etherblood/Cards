package com.etherblood.cardsmatch.cardgame;

import com.etherblood.entitysystem.data.EntityId;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class IdConverter {
    private final HashMap<EntityId, Long> map = new HashMap<>();
    private long nextId = 0;
    private NetworkPlayer player;
    
    public EntityId fromLong(Long value) {
        for (Map.Entry<EntityId, Long> entry : map.entrySet()) {
            if(entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("id not registered");
    }
    
    public EntityId[] fromLongs(long[] values) {
        EntityId[] result = new EntityId[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = fromLong(values[i]);
        }
        return result;
    }
    
    public Long toLong(EntityId id) {
        Long value = map.get(id);
        return value;
    }
    
    public Long register(EntityId id) {
        Long value = nextId++;
        map.put(id, value);
        return value;
    }

    public NetworkPlayer getPlayer() {
        return player;
    }

    public void setPlayer(NetworkPlayer player) {
        this.player = player;
    }
}
