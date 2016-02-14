package com.etherblood.cardsmasterserver.cards;

import com.etherblood.cardsmasterserver.cards.model.CollectionType;
import com.etherblood.cardsmasterserver.cards.model.CardCollection;
import com.etherblood.cardsmasterserver.cards.model.QCardCollection;
import com.etherblood.cardsmasterserver.core.AbstractRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Philipp
 */
@Repository
public class CardCollectionRepository extends AbstractRepository<CardCollection> {
    private QCardCollection collection = QCardCollection.cardCollection;
    
    public List<CardCollection> getAllUserCollections(long userId) {
        return from(collection)
                .where(collection.owner.id.eq(userId))
                .list(collection);
    }

    public CardCollection getFirstUserCollectionByType(long userId, CollectionType collectionType) {
        return from(collection)
                .where(collection.owner.id.eq(userId))
                .where(collection.type.eq(collectionType))
                .singleResult(collection);
    }
}
