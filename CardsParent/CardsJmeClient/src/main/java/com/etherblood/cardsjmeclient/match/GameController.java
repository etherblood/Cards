package com.etherblood.cardsjmeclient.match;

import com.etherblood.cardsjmeclient.match.cards.Card;
import com.etherblood.cardsnetworkshared.match.misc.CardZone;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.event.DefaultMouseListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class GameController {

    public static final int OWN_ID = 0;
    public static final int OPP_ID = 1;
    private final MatchContainer gamePanel;
    private final EnumMap<CardZone, Container>[] zones = new EnumMap[2];
    private final HashMap<Long, Card> cards = new HashMap<>();
    private CommandHandler commandHandler;
//    private Ability selectedAbility = null;
//    private ArrayList<Card> selection = new ArrayList<>();
    private Card dragSource = null;
    DefaultMouseListener dragAndDropAdapter = new DefaultMouseListener() {
//        @Override
//        public void mousePressed(MouseEvent e) {
//            if (e.getButton() == MouseEvent.BUTTON1) {
//                selectedTargets.add((CardPanel) e.getSource());
//                gamePanel.setAction(selectedAbility, selectedTargets);
////                    if(selectedAbility != null) {
////                        commandHandler.triggerEffect(selectedAbility.getId(), getId((CardPanel) e.getSource()));
////                        selectedAbility = null;
////                    }
//            }
////                if(dragSource != null) {
////                    for (Ability effect : dragSource.getAbilities()) {
////                        commandHandler.triggerEffect(effect.getId(), getId((CardPanel) e.getSource()));
////                    }
////                    dragSource.setSelected(false);
////                    dragSource = null;
////                } else {
////                    dragSource = (CardPanel) e.getSource();
////                    dragSource.setSelected(true);
////                }
//        }

        @Override
        protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
            if (getSelectedAbility() == null) {
                dragSource = (Card) target;
                dragSource.setSelected(true);
            } else {
                commandHandler.triggerEffect(getSelectedAbility().getId(), getId((Card) target));
                dragSource.setSelected(false);
                dragSource = null;
            }
            gamePanel.select(dragSource);
//            gamePanel.setAction(getSelectedAbility(), Collections.<Card>emptyList());
        }

    };

    public GameController(final MatchContainer gamePanel) {
        this.gamePanel = gamePanel;
        zones[OWN_ID] = new EnumMap<>(CardZone.class);
        zones[OPP_ID] = new EnumMap<>(CardZone.class);

        zones[OPP_ID].put(CardZone.Hand, gamePanel.getOppHand());
        zones[OPP_ID].put(CardZone.Board, gamePanel.getOppBoard());
        zones[OWN_ID].put(CardZone.Board, gamePanel.getOwnBoard());
        zones[OWN_ID].put(CardZone.Hand, gamePanel.getOwnHand());
//
//        gamePanel.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
////                    commandHandler.endTurn();
//                }
//                switch (e.getKeyCode()) {
//                    case KeyEvent.VK_ENTER:
//                        if (selectedAbility != null) {
//                            long[] targets = new long[selectedTargets.size()];
//                            for (int i = 0; i < targets.length; i++) {
//                                targets[i] = getId(selectedTargets.get(i));
//                            }
//                            commandHandler.triggerEffect(selectedAbility.getId(), targets);
//
//                            selectedAbility = null;
//                            selectedTargets.clear();
//                            gamePanel.setAction(selectedAbility, selectedTargets);
//                        }
//                        break;
//                    case KeyEvent.VK_ESCAPE:
//                        selectedAbility = null;
//                        selectedTargets.clear();
//                        gamePanel.setAction(selectedAbility, selectedTargets);
//                        break;
//                }
//            }
//        });
        gamePanel.addMouseListener(new DefaultMouseListener() {
            @Override
            protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
//                if(dragSource != null) {
//                    dragSource.setSelected(false);
//                    dragSource = null;
//                }
                if (dragSource != null) {
                    dragSource.setSelected(false);
                }
                dragSource = null;
                gamePanel.select(dragSource);
//                gamePanel.setAction(getSelectedAbility(), Collections.<Card>emptyList());
            }
        });
    }

    public void reset() {
//        selectedAbility = null;
        if (dragSource != null) {
            dragSource.setSelected(false);
        }
        dragSource = null;
        cards.clear();
        for (EnumMap<CardZone, Container> zoneMap : zones) {
            for (Container zonePanel : zoneMap.values()) {
                zonePanel.clearChildren();
            }
        }
        gamePanel.select(dragSource);
//        gamePanel.setAction(getSelectedAbility(), Collections.<Card>emptyList());
    }

    private Ability getSelectedAbility() {
        if (dragSource != null) {
            for (Ability ability : dragSource.getAbilities()) {
                if (ability.getName().contains("Activation")) {
                    return ability;
                }
            }
        }
        return null;
    }

    public MatchContainer getGamePanel() {
        return gamePanel;
    }

    public void createCard(Long id, String name) {
        Card cardPanel = new Card();
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
        Card cardPanel = cards.get(id);
        cardPanel.setZone(zone);
        refreshZone(cardPanel);
    }

    public void setCardOwner(Long id, long owner) {
        Card cardPanel = cards.get(id);
        cardPanel.setOwner(owner);
        refreshZone(cardPanel);
    }

    public void setCardProperty(Long card, String key, boolean value) {
        Card cardPanel = cards.get(card);
        if (value) {
            cardPanel.getProperties().add(key);
        } else {
            cardPanel.getProperties().remove(key);
        }
    }

    public void attachCardEffect(Long card, final Ability ability) {
//        AbilityLabel abilityLabel = new AbilityLabel(ability);
//        abilityLabel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                selectedAbility = ability;
//                gamePanel.setAction(selectedAbility, selectedTargets);
//            }
//        });
        Card cardPanel = cards.get(card);
//        cardPanel.getAbilityMenu().add(abilityLabel);
        cardPanel.getAbilities().add(ability);
    }

    public void detachCardEffect(long effectId) {
        for (Card card : cards.values()) {
            for (Ability ability : card.getAbilities()) {
                if (ability.getId() == effectId) {
                    card.getAbilities().remove(ability);
                    return;
                }
            }
//            for (Component component : card.getAbilityMenu().getComponents()) {
//                if (component instanceof AbilityLabel) {
//                    AbilityLabel abilityLabel = (AbilityLabel) component;
//                    if (abilityLabel.ability.getId() == effectId) {
//                        card.getAbilityMenu().remove(abilityLabel);
//                        return;
//                    }
//                }
//            }
        }
    }

    public void attack(long attacker, long target) {
//////////        AssetManager   manager = JmeSystem.newAssetManager();
//////////
//////////   
//////////
//////////  manager.registerLocator("http://wow.zamimg.com/hearthhead/sounds/",UrlLocator.class);
//////////
////////////   Listener   listener = new Listener();
//////////
////////////    ar.setListener(listener);
//////////
//////////   AudioNode src = new AudioNode(manager,"VO_EX1_116_Play_01.ogg", true);
//////////   gamePanel.attachChild(src);
//////////   src.play();
//////////        
////////////        UrlLocator locator = new UrlLocator();
////////////        locator.locate(null, new AudioKey("wow.zamimg.com/hearthhead/sounds/VO_EX1_116_Play_01.ogg")).openStream()
////////////        AudioKey key = new AudioKey("a");
////////////        key.
////////////        AudioNode audio = new AudioNode(audioData, audioKey)
////////////        AudioFactory.INSTANCE.playSound(cards.get(attacker).getCardName() + "attack");
    }

    private void refreshZone(Card cardPanel) {
        Container container = (Container) cardPanel.getParent();
        if (container != null) {
            container.removeChild(cardPanel);
        }
        Container zone = getZone(cardPanel);
        if (zone != null) {
            zone.addChild(cardPanel);
        }
    }

    private Container getZone(Card cardPanel) {
        int owner = (int) cardPanel.getOwner();
        if (owner == -1) {
            return null;
        }
        return zones[owner].get(cardPanel.getZone());
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    private Long getId(Card cardPanel) {
        for (Map.Entry<Long, Card> entry : cards.entrySet()) {
            if (entry.getValue() == cardPanel) {
                return entry.getKey();
            }
        }
        return null;
    }
}
