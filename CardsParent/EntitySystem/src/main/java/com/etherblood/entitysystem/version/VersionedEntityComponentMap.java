package com.etherblood.entitysystem.version;

import com.etherblood.entitysystem.data.EntityComponentMap;

/**
 *
 * @author Philipp
 */
public interface VersionedEntityComponentMap extends EntityComponentMap {
    int getVersion();
    void revertTo(int version);
}
