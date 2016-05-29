package com.etherblood.cardsmasterserver.cards;

import com.etherblood.cardsmasterserver.cards.model.Card;
import com.etherblood.cardsmasterserver.cards.model.CardGroup;
import com.etherblood.cardsmasterserver.cards.model.CardTemplate;
import com.etherblood.cardsmasterserver.cards.model.QCard;
import com.etherblood.cardsmasterserver.core.AbstractRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Philipp
 */
@Repository
class CardRepository extends AbstractRepository<Card> {
    private final QCard qCard = QCard.card;
    
    public void addCardToGroup(long groupId, long templateId, int amount) {
        Card card = from(qCard)
                .where(qCard.group.id.eq(groupId))
                .where(qCard.template.id.eq(templateId))
                .singleResult(qCard);
        if(card == null) {
            card = new Card();
            card.setAmount(amount);
            card.setGroup(getEntityManager().getReference(CardGroup.class, groupId));
            card.setTemplate(getEntityManager().getReference(CardTemplate.class, templateId));
            persist(card);
        }
        card.setAmount(card.getAmount() + amount);
    }
}
