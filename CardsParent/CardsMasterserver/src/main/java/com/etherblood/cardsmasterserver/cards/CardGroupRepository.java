package com.etherblood.cardsmasterserver.cards;

import com.etherblood.cardsmasterserver.cards.model.Card;
import com.etherblood.cardsmasterserver.cards.model.CardGroup;
import com.etherblood.cardsmasterserver.cards.model.CollectionType;
import com.etherblood.cardsmasterserver.cards.model.QCard;
import com.etherblood.cardsmasterserver.cards.model.QCardGroup;
import com.etherblood.cardsmasterserver.core.AbstractRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Philipp
 */
@Repository
class CardGroupRepository extends AbstractRepository<CardGroup> {
    private final QCardGroup qGroup = QCardGroup.cardGroup;
    
    public List<CardGroup> getAllUserCardGroups(long userId) {
        return from(qGroup)
                .where(qGroup.owner.id.eq(userId))
                .list(qGroup);
    }

    public CardGroup getFirstUserCollectionByType(long ownerId, CollectionType collectionType) {
        return from(qGroup)
                .where(qGroup.owner.id.eq(ownerId))
                .where(qGroup.type.eq(collectionType))
                .singleResult(qGroup);
    }

    public List<CardGroup> getGroupsForOwner(long ownerId) {
        return from(qGroup)
                .where(qGroup.owner.id.eq(ownerId))
                .list(qGroup);
    }

    public List<CardGroup> getBotLibraries() {
        return from(qGroup)
                .where(qGroup.owner.isNull())
                .list(qGroup);
    }

    public CardGroup findCardGroup(long groupId) {
        return from(qGroup)
                .where(qGroup.id.eq(groupId))
                .where(qGroup.type.eq(CollectionType.Library))
                .uniqueResult(qGroup);
    }
}
