/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.entitysystem.data.EntityComponent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class EntityTemplate {
    private boolean collectible = true;
    private final ArrayList<EntityComponent> components = new ArrayList<>();
    private final ArrayList<String> childTemplates = new ArrayList<>();

    public void add(EntityComponent component) {
        components.add(component);
    }
    
    public void addAll(EntityComponent... components) {
        this.components.addAll(Arrays.asList(components));
    }
    
    public void addChild(String template) {
        childTemplates.add(template);
    }

    public ArrayList<EntityComponent> getComponents() {
        return components;
    }

    public ArrayList<String> getChildTemplates() {
        return childTemplates;
    }

//    public EntityId create(MatchState match, EntityIdFactory idFactory) {
//        EntityId cardId = idFactory.createEntity();
//        attachTo(match, cardId, null, idFactory);
//        return cardId;
//    }

    public String getName() {
        for (EntityComponent component : components) {
            if (component instanceof NameComponent) {
                return ((NameComponent) component).name;
            }
        }
        return null;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

//    public void attachTo(MatchState match, EntityId entity, EntityId owner, EntityId parent) {
//        for (EntityComponent component : components) {
//            match.data.set(entity, component);
//        }
//        if(owner != null) {
//            match.events.fireEvent(new SetOwnerEvent(entity, owner));
//        }
//        for (String child : childTemplates) {
//            EntityId c = match.idFactory.createEntity();
//            match.events.fireEvent(new AttachTemplateEvent(c, child, owner, entity));
//        }
//    }
}
