package com.etherblood.cardsswingdisplay;

import javax.swing.JMenuItem;

/**
 *
 * @author Philipp
 */
public class AbilityLabel extends JMenuItem {
    public final Ability ability;

    public AbilityLabel(Ability ability) {
        super(ability.getName());
        this.ability = ability;
    }
}
