package com.etherblood.entitysystem.util;

import com.etherblood.entitysystem.data.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class DeterministicEntityIndices {

    public int getDeterministicIndexForEntity(List<EntityId> randomList, EntityId selectedElement) {
        int moveId = 0;
        boolean found = false;
        for (EntityId entityId : randomList) {
            if (entityId.longValue() < selectedElement.longValue()) {
                moveId++;
            } else {
                found |= entityId.equals(selectedElement);
            }
        }
        if (!found) {
            moveId = -1;
        }
        return moveId;
    }

    public EntityId getEntityForDeterministicIndex(List<EntityId> randomList, int moveId) {
        EntityId result = select(randomList, 0, randomList.size() - 1, moveId);
        return result;
    }

    private EntityId select(List<EntityId> list, int left, int right, int n) {
        while (true) {
            if (left == right) {
                return list.get(left);
            }
            int pivotIndex = (left + right) / 2;//alternative: select random element from left to right inclusive
            pivotIndex = partition(list, left, right, pivotIndex);
            if (n == pivotIndex) {
                return list.get(n);
            } else if (n < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private int partition(List<EntityId> list, int left, int right, int pivotIndex) {
        EntityId pivotValue = list.get(pivotIndex);
        swap(list, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (list.get(i).longValue() < pivotValue.longValue()) {
                swap(list, storeIndex, i);
                storeIndex++;
            }
        }
        swap(list, right, storeIndex);
        return storeIndex;
    }

    private void swap(List<EntityId> list, int left, int right) {
        EntityId tmp = list.get(left);
        list.set(left, list.get(right));
        list.set(right, tmp);
    }
}
