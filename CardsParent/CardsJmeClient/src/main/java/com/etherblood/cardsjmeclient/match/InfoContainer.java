package com.etherblood.cardsjmeclient.match;

import com.etherblood.cardsjmeclient.match.cards.Card;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

/**
 *
 * @author Philipp
 */
public class InfoContainer extends Container {
    private final Card selectedCard = new Card();
    private final Container effects = new Container();
//    private final Label actionLabel = new Label("");
    
    public void select(Card card) {
//        deselect();
        
        addChild(selectedCard);
        addChild(effects);
        
        selectedCard.setCardName(card.getCardName());
        selectedCard.setAttack(card.getAttack());
        selectedCard.setHealth(card.getHealth());
        selectedCard.setCost(card.getCost());
        
        for (Ability ability : card.getAbilities()) {
            effects.addChild(new Label(ability.getName()));
        }
        for (String property : card.getProperties()) {
            effects.addChild(new Label(property));
        }
    }
    
    public void deselect() {
        detachChild(selectedCard);
        detachChild(effects);
        effects.clearChildren();
    }
}
