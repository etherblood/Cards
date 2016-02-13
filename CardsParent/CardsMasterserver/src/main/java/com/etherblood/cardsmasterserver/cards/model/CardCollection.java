package com.etherblood.cardsmasterserver.cards.model;

import com.etherblood.cardsmasterserver.core.MutableEntity;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Philipp
 */
@Entity
public class CardCollection extends MutableEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    private List<String> cards;
    @NotNull
    private CollectionType type;
    @NotNull
    @ManyToOne
    private UserAccount owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public CollectionType getType() {
        return type;
    }

    public void setType(CollectionType type) {
        this.type = type;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }
}
