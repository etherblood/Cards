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
public class DeckbuilderScreen extends AbstractScreen {

    @Override
    public void bind(Eventbus eventbus) {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Deckbuilder"));
        Button mainMenuButton = getContainer().addChild(new Button("Main Menu"));
        mainMenuButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new ScreenRequestEvent(ScreenKeys.MAIN_MENU));
            }
        });
    }

}
