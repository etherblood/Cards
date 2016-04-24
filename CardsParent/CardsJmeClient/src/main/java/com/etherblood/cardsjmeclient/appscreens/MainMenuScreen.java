package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;

/**
 *
 * @author Philipp
 */
public class MainMenuScreen extends AbstractScreen {

    @Override
    public void bind(Eventbus eventbus) {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Main Menu"));
        Button lobbyButton = getContainer().addChild(new Button("Lobby"));
        lobbyButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new ScreenRequestEvent(ScreenKeys.LOBBY));
            }
        });
        Button deckButton = getContainer().addChild(new Button("Deckbuilder"));
        deckButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new ScreenRequestEvent(ScreenKeys.DECKBUILDER));
            }
        });
    }

}
