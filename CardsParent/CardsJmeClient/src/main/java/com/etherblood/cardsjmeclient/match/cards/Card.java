package com.etherblood.cardsjmeclient.match.cards;

import com.etherblood.cardsjmeclient.match.Ability;
import com.etherblood.cardsjmeclient.match.cards.images.ImageFactory;
import com.etherblood.cardsjmeclient.match.cards.images.MyIconComponent;
import com.etherblood.cardsnetworkshared.match.misc.CardZone;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.component.BorderLayout;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.core.GuiComponent;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class Card extends Container {
    private final Label nameLabel = new Label("name");
    private final Label manaLabel = new Label("");
    private final Label attackLabel = new Label("");
    private final Label healthLabel = new Label("");
    private final Panel artPanel = new Panel();
    private CardZone zone = CardZone.None;
    private Long owner = null;
    private ArrayList<Ability> abilities = new ArrayList<>();

    public Card() {
        setSize(new Vector3f(150, 225, 0));
//        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
//        CompoundBorder border = BorderFactory.createCompoundBorder(lineBorder, new EmptyBorder(0, 3, 0, 3));
//        setBorder(border);
        Container topPanel = new Container(new BorderLayout());
        
        addChild(topPanel);
//        topPanel.setInheritsPopupMenu(true);
//        topPanel.setOpaque(false);
        topPanel.addChild(nameLabel, BorderLayout.Position.West);
        topPanel.addChild(manaLabel, BorderLayout.Position.East);
        
        addChild(artPanel);
//        artPanel.setBackground(Color.BLACK);
//        artPanel..addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                refreshArt();
//            }
//        });
        
        Container bottomPanel = new Container(new BorderLayout());
        
        addChild(bottomPanel);
        bottomPanel.addChild(attackLabel, BorderLayout.Position.West);
        bottomPanel.addChild(healthLabel, BorderLayout.Position.East);
        
//        setComponentPopupMenu(abilityMenu);
        
        setSelected(false);
    }
    
    public final void setSelected(boolean value) {
        setBackground(new QuadBackgroundComponent(value? ColorRGBA.Blue: ColorRGBA.Black));
//        setBackground(value? Color.WHITE: Color.GRAY);
    }
    
    @Override
    public void setName(String name) {
        super.setName(name);
        System.out.println(getClass().getName());
        System.out.println("WARNING: Card.setName() was used, did you mean to use Card.setCardName() instead?");
    }
    
    public void setCardName(String name) {
        nameLabel.setText(name);
        refreshArt();
    }
    
    private void refreshArt() {
        GuiComponent icon;
        Texture texture = ImageFactory.getInstance().get(getCardName(), 150, 200);
        icon = new MyIconComponent(texture);
//        icon.setIconScale(Math.min(200f / icon.getImageTexture().getImage().getHeight(), 150f / icon.getImageTexture().getImage().getWidth()));
//        artPanel.setSize(new Vector3f(150, 225, 0));
//        artPanel.setBackground(new QuadBackgroundComponent(ColorRGBA.Black));
        artPanel.setBackground(icon);
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
//                AudioFactory.INSTANCE.playSound(getCardName() + "intro");
//                AudioFactory.INSTANCE.playSound(getCardName() + "summon");
                break;
            case Graveyard:
//                AudioFactory.INSTANCE.playSound(getCardName() + "death");
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

//    public JPopupMenu getAbilityMenu() {
//        return abilityMenu;
//    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }
}
