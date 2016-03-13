package com.etherblood.cardsmatch.cardgame.match;

import com.etherblood.cardsmatch.cardgame.bot.Bot;
import com.etherblood.entitysystem.data.EntityId;

/**
 *
 * @author Philipp
 */
public class PlayerDefinition {
    private String name, heroTemplate;
    private String[] library;
    private EntityId entity;
    private boolean bot;
    private Bot botInstance;
    
    public String getName() {
        return name;
    }

    public String getHeroTemplate() {
        return heroTemplate;
    }

    public String[] getLibrary() {
        return library;
    }

    public void setEntity(EntityId entity) {
        this.entity = entity;
    }

//    @Override
//    public SystemsEventHandler getUpdateHandler() {
//        return updateHandler;
//    }

    public EntityId getEntity() {
        assert entity != null;
        return entity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeroTemplate(String hero) {
        this.heroTemplate = hero;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public Bot getBotInstance() {
        return botInstance;
    }

    public void setBotInstance(Bot botInstance) {
        this.botInstance = botInstance;
    }
}
