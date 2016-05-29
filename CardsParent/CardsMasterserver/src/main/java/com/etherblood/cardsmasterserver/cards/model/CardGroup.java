package com.etherblood.cardsmasterserver.cards.model;

import com.etherblood.cardsmasterserver.core.MutableEntity;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Philipp
 */
@Entity
public class CardGroup extends MutableEntity {
    @Id
    @GeneratedValue
//    @Column(name="cardgroup_id")
    private Long id;
    
//    @NotNull
    @ManyToOne
    @JoinColumn(name="useraccount_id")
    private UserAccount owner;
    
    @NotNull
    @OneToMany(mappedBy="group", orphanRemoval=true)
    private Set<Card> cards;
    
    @Enumerated(EnumType.STRING)
    private CollectionType type;
    private String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public CollectionType getType() {
        return type;
    }

    public void setType(CollectionType type) {
        this.type = type;
    }
}
