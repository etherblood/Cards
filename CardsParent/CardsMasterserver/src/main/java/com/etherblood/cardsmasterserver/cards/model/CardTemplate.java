package com.etherblood.cardsmasterserver.cards.model;

import com.etherblood.cardsmasterserver.core.MutableEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Philipp
 */
@Entity
public class CardTemplate extends MutableEntity {
    @Id
    @GeneratedValue
    private long id;
    
    @Column(unique=true, nullable=false)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
