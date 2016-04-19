package com.etherblood.cardsmatchapi;

/**
 *
 * @author Philipp
 */
public class PlayerDefinition {
    private String name, heroTemplate;
    private String[] library;
//    private EntityId entity;
//    private boolean bot;
//    private Bot botInstance;
//    private IdConverter converter;
    
    public String getName() {
        return name;
    }

    public String getHeroTemplate() {
        return heroTemplate;
    }

    public String[] getLibrary() {
        return library;
    }

//    public void setEntity(EntityId entity) {
//        this.entity = entity;
//    }

//    @Override
//    public SystemsEventHandler getUpdateHandler() {
//        return updateHandler;
//    }

//    public EntityId getEntity() {
//        assert entity != null;
//        return entity;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeroTemplate(String hero) {
        this.heroTemplate = hero;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

//    public boolean isBot() {
//        return bot;
//    }
//
//    public void setBot(boolean bot) {
//        this.bot = bot;
//    }

//    public Bot getBotInstance() {
//        return botInstance;
//    }
//
//    public void setBotInstance(Bot botInstance) {
//        this.botInstance = botInstance;
//    }
//
//    public IdConverter getConverter() {
//        return converter;
//    }
//
//    public void setConverter(IdConverter converter) {
//        this.converter = converter;
//    }
}
