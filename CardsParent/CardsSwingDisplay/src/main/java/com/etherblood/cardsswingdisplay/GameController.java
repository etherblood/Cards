package com.etherblood.cardsswingdisplay;

import java.awt.Component;
import java.awt.Container;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Philipp
 */
public class GameController {

    public static final int OWN_ID = 0;
    public static final int OPP_ID = 1;
    private final GamePanel gamePanel;
    private final EnumMap<CardZone, ZonePanel>[] zones = new EnumMap[2];
    private final HashMap<Long, CardPanel> cards = new HashMap<>();
    private CommandHandler commandHandler;
    private Ability selectedAbility = null;
    private ArrayList<CardPanel> selectedTargets = new ArrayList<>();
//    private CardPanel dragSource = null;
    MouseAdapter dragAndDropAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                selectedTargets.add((CardPanel) e.getSource());
                gamePanel.setAction(selectedAbility, selectedTargets);
//                    if(selectedAbility != null) {
//                        commandHandler.triggerEffect(selectedAbility.getId(), getId((CardPanel) e.getSource()));
//                        selectedAbility = null;
//                    }
            }
//                if(dragSource != null) {
//                    for (Ability effect : dragSource.getAbilities()) {
//                        commandHandler.triggerEffect(effect.getId(), getId((CardPanel) e.getSource()));
//                    }
//                    dragSource.setSelected(false);
//                    dragSource = null;
//                } else {
//                    dragSource = (CardPanel) e.getSource();
//                    dragSource.setSelected(true);
//                }
        }
    };

    public GameController(final GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        zones[OWN_ID] = new EnumMap<CardZone, ZonePanel>(CardZone.class);
        zones[OPP_ID] = new EnumMap<CardZone, ZonePanel>(CardZone.class);

        zones[OPP_ID].put(CardZone.Hand, gamePanel.getOppHand());
        zones[OPP_ID].put(CardZone.Board, gamePanel.getOppBoard());
        zones[OWN_ID].put(CardZone.Board, gamePanel.getOwnBoard());
        zones[OWN_ID].put(CardZone.Hand, gamePanel.getOwnHand());

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                    commandHandler.endTurn();
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        if (selectedAbility != null) {
                            long[] targets = new long[selectedTargets.size()];
                            for (int i = 0; i < targets.length; i++) {
                                targets[i] = getId(selectedTargets.get(i));
                            }
                            commandHandler.triggerEffect(selectedAbility.getId(), targets);

                            selectedAbility = null;
                            selectedTargets.clear();
                            gamePanel.setAction(selectedAbility, selectedTargets);
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        selectedAbility = null;
                        selectedTargets.clear();
                        gamePanel.setAction(selectedAbility, selectedTargets);
                        break;
                }
            }
        });
//        gamePanel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
////                if(dragSource != null) {
////                    dragSource.setSelected(false);
////                    dragSource = null;
////                }
//                selectedAbility = null;
//            }
//        });
    }

    public void reset() {
        selectedAbility = null;
        selectedTargets.clear();
        cards.clear();
        for (EnumMap<CardZone, ZonePanel> zoneMap : zones) {
            for (ZonePanel zonePanel : zoneMap.values()) {
                zonePanel.removeAll();
            }
        }
        gamePanel.setAction(selectedAbility, selectedTargets);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void createCard(Long id, String name) {
        CardPanel cardPanel = new CardPanel();
        cardPanel.addMouseListener(dragAndDropAdapter);
        cardPanel.setCardName(name);
        cards.put(id, cardPanel);
    }

    public void setCardCost(Long id, int mana) {
        cards.get(id).setCost(mana);
    }

    public void setCardAttack(Long id, int attack) {
        cards.get(id).setAttack(attack);
    }

    public void setCardHealth(Long id, int health) {
        cards.get(id).setHealth(health);
    }

    public void setCardZone(Long id, CardZone zone) {
        CardPanel cardPanel = cards.get(id);
        cardPanel.setZone(zone);
        refreshZone(cardPanel);
    }

    public void setCardOwner(Long id, long owner) {
        CardPanel cardPanel = cards.get(id);
        cardPanel.setOwner(owner);
        refreshZone(cardPanel);
    }

    public void setCardProperty(Long card, String key, boolean value) {
        CardPanel cardPanel = cards.get(card);
        JPopupMenu abilities = cardPanel.getAbilityMenu();
        if (value) {
            abilities.add(new JMenuItem(key));
        } else {
            for (Component component : abilities.getComponents()) {
                if (component instanceof JMenuItem) {
                    JMenuItem label = (JMenuItem) component;
                    if (key.equals(label.getText())) {
                        abilities.remove(component);
                        return;
                    }
                }
            }
        }
    }

    public void attachCardEffect(Long card, final Ability ability) {
        AbilityLabel abilityLabel = new AbilityLabel(ability);
        abilityLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedAbility = ability;
                gamePanel.setAction(selectedAbility, selectedTargets);
            }
        });
        CardPanel cardPanel = cards.get(card);
        cardPanel.getAbilityMenu().add(abilityLabel);
    }

    public void detachCardEffect(long effectId) {
        for (CardPanel card : cards.values()) {
            for (Component component : card.getAbilityMenu().getComponents()) {
                if (component instanceof AbilityLabel) {
                    AbilityLabel abilityLabel = (AbilityLabel) component;
                    if (abilityLabel.ability.getId() == effectId) {
                        card.getAbilityMenu().remove(abilityLabel);
                        return;
                    }
                }
            }
        }
    }

    public void attack(long attacker, long target) {
        AudioFactory.INSTANCE.playSound(cards.get(attacker).getCardName() + "attack");
    }

    private void refreshZone(CardPanel cardPanel) {
        Container container = cardPanel.getParent();
        if (container != null) {
            container.remove(cardPanel);
        }
        ZonePanel zone = getZone(cardPanel);
        if (zone != null) {
            zone.add(cardPanel);
        }
    }

    private ZonePanel getZone(CardPanel cardPanel) {
        int owner = (int) cardPanel.getOwner();
        if (owner == -1) {
            return null;
        }
        return zones[owner].get(cardPanel.getZone());
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    private Long getId(CardPanel cardPanel) {
        for (Map.Entry<Long, CardPanel> entry : cards.entrySet()) {
            if (entry.getValue() == cardPanel) {
                return entry.getKey();
            }
        }
        return null;
    }
}
