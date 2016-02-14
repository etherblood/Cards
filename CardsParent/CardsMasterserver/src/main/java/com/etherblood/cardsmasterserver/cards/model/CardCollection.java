package com.etherblood.cardsmasterserver.cards.model;

import com.etherblood.cardsmasterserver.core.MutableEntity;
import com.etherblood.cardsmasterserver.core.jsonb.JSONBUserType;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 *
 * @author Philipp
 */
@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
  @Parameter(name = JSONBUserType.CLASS,
      value = "java.util.ArrayList")})
@Entity
public class CardCollection extends MutableEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Type(type = "jsonb")
    private List<String> cards;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CollectionType type;
    @NotNull
    @ManyToOne
    private UserAccount owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
