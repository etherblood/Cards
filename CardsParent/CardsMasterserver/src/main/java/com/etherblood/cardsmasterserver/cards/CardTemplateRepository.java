package com.etherblood.cardsmasterserver.cards;

import com.etherblood.cardsmasterserver.cards.model.CardTemplate;
import com.etherblood.cardsmasterserver.cards.model.QCardTemplate;
import com.etherblood.cardsmasterserver.core.AbstractRepository;
import com.mysema.query.types.expr.NumberExpression;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Philipp
 */
@Repository
class CardTemplateRepository extends AbstractRepository<CardTemplate> {
    private final QCardTemplate qCardTemplate = QCardTemplate.cardTemplate;
    
    public CardTemplate findByName(String name) {
        return from(qCardTemplate)
                .where(qCardTemplate.name.eq(name))
                .uniqueResult(qCardTemplate);
    }
    
    public List<CardTemplate> randomTemplates(int num) {
        return from(qCardTemplate)
                .orderBy(NumberExpression.random().asc())
                .limit(num)
                .list(qCardTemplate);
    }
}
