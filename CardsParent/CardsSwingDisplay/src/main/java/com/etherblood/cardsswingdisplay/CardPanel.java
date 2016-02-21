package com.etherblood.cardsswingdisplay;

import com.etherblood.cardsswingdisplay.images.ImageFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Philipp
 */
public class CardPanel extends JPanel {
    private final JPopupMenu abilityMenu = new JPopupMenu();
    private final JLabel nameLabel = new JLabel("name");
    private final JLabel manaLabel = new JLabel("");
    private final JLabel attackLabel = new JLabel("");
    private final JLabel healthLabel = new JLabel("");
    private final JLabel artLabel = new JLabel();
    private CardZone zone = CardZone.None;
    private long owner = -1L;

    public CardPanel() {
        super(new BorderLayout());
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(lineBorder, new EmptyBorder(0, 3, 0, 3));
        setBorder(border);
        setPreferredSize(new Dimension(150, 225));
        JPanel topPanel = new JPanel(new BorderLayout());
        
        add(topPanel, BorderLayout.NORTH);
        topPanel.setInheritsPopupMenu(true);
        topPanel.setOpaque(false);
        topPanel.add(nameLabel, BorderLayout.WEST);
        topPanel.add(manaLabel, BorderLayout.EAST);
        
        add(artLabel, BorderLayout.CENTER);
        artLabel.setInheritsPopupMenu(true);
        artLabel.setBackground(Color.BLACK);
        artLabel.setOpaque(true);
        artLabel.setBorder(lineBorder);
        artLabel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshArt();
            }
        });
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setInheritsPopupMenu(true);
        
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setOpaque(false);
        bottomPanel.add(attackLabel, BorderLayout.WEST);
        bottomPanel.add(healthLabel, BorderLayout.EAST);
        
        nameLabel.setInheritsPopupMenu(true);
        manaLabel.setInheritsPopupMenu(true);
        attackLabel.setInheritsPopupMenu(true);
        healthLabel.setInheritsPopupMenu(true);
        
        setComponentPopupMenu(abilityMenu);
        
        setSelected(false);
    }
    
    public final void setSelected(boolean value) {
//        if(value && zone == Board) {
//            AudioFactory.INSTANCE.playSound(getCardName() + "intro");
//        }
        setBackground(value? Color.WHITE: Color.GRAY);
    }
    
    public void setCardName(String name) {
        nameLabel.setText(name);
        refreshArt();
    }
    
    private void refreshArt() {
        artLabel.setIcon(ImageFactory.INSTANCE.get(getCardName(), artLabel.getWidth(), artLabel.getHeight()));
    }

    public String getCardName() {
        return nameLabel.getText();
    }
    
    public void setCost(int cost) {
        manaLabel.setText("" + cost);
    }
    
    public void setAttack(int attack) {
        attackLabel.setText("" + attack);
    }
    
    public void setHealth(int health) {
        healthLabel.setText("" + health);
    }

    public CardZone getZone() {
        return zone;
    }

    public void setZone(CardZone zone) {
        switch(zone) {
            case Board:
                AudioFactory.INSTANCE.playSound(getCardName() + "intro");
                AudioFactory.INSTANCE.playSound(getCardName() + "summon");
                break;
            case Graveyard:
                AudioFactory.INSTANCE.playSound(getCardName() + "death");
                break;
        }
        this.zone = zone;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public JPopupMenu getAbilityMenu() {
        return abilityMenu;
    }
}
