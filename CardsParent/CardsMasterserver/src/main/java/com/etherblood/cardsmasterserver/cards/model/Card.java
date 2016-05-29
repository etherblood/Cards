package com.etherblood.cardsmasterserver.cards.model;

import com.etherblood.cardsmasterserver.core.MutableEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Philipp
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"group_id", "template_id"})})
public class Card extends MutableEntity {
    @Id
    @GeneratedValue
//    @Column(name="card_id")
    private long id;
    
    @NotNull
    @ManyToOne
//    @JoinColumn(name="cardgroup_id")
    private CardGroup group;
    
    @NotNull
    @ManyToOne
    private CardTemplate template;
    
    @Min(value=1)
    private int amount;

    public Card() {
    }

    public Card(CardGroup group, CardTemplate template) {
        this(group, template, 1);
    }

    public Card(CardGroup group, CardTemplate template, int amount) {
        this.group = group;
        this.template = template;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CardGroup getGroup() {
        return group;
    }

    public void setGroup(CardGroup group) {
        this.group = group;
    }

    public CardTemplate getTemplate() {
        return template;
    }

    public void setTemplate(CardTemplate template) {
        this.template = template;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
