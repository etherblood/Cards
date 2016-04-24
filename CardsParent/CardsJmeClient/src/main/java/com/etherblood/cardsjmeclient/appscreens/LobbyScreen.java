package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.master.commands.MatchRequest;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;

/**
 *
 * @author Philipp
 */
public class LobbyScreen extends AbstractScreen {

    @Override
    public void bind(final Eventbus eventbus) {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Lobby"));
        final Checkbox botbox = getContainer().addChild(new Checkbox("bot"));
        botbox.setChecked(true);
//        myWindow.addChild(, constraints)
        Button clickMe = getContainer().addChild(new Button("start"));
        clickMe.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new DefaultMessage(new MatchRequest(botbox.isChecked())));
            }
        });
        Button mainMenuButton = getContainer().addChild(new Button("Main Menu"));
        mainMenuButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new ScreenRequestEvent(ScreenKeys.MAIN_MENU));
            }
        });
    }
}
