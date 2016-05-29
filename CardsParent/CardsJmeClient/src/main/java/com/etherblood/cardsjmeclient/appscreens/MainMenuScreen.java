package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class MainMenuScreen extends AbstractScreen {

    @PostConstruct
    public void init() {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Main Menu"));
        Button lobbyButton = getContainer().addChild(new Button("Lobby"));
        lobbyButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                getEventbus().sendEvent(new ScreenRequestEvent(ScreenKeys.LOBBY));
            }
        });
        Button deckButton = getContainer().addChild(new Button("Deckbuilder"));
        deckButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                getEventbus().sendEvent(new ScreenRequestEvent(ScreenKeys.DECKBUILDER));
            }
        });
    }

    @Override
    public ScreenKeys getScreenKey() {
        return ScreenKeys.MAIN_MENU;
    }

}
