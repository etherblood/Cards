package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.master.commands.MatchRequest;
import com.etherblood.cardsnetworkshared.master.updates.LoginSuccess;
import com.etherblood.cardsnetworkshared.match.updates.GameOver;
import com.etherblood.cardsnetworkshared.match.updates.JoinedMatchUpdate;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

/**
 *
 * @author Philipp
 */
public class ArrangeMatchScreen extends AbstractScreen {

    @Override
    public void bind(final Eventbus eventbus) {
        getContainer().setLocalTranslation(300, 300, 0);

        // Add some elements
        getContainer().addChild(new Label("Hello, World."));
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
    }
}
