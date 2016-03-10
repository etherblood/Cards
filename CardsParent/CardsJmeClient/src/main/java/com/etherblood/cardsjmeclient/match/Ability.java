package com.etherblood.cardsjmeclient.match;

/**
 *
 * @author Philipp
 */
public class Ability {
    private long id;
    private String name;

    public Ability(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
