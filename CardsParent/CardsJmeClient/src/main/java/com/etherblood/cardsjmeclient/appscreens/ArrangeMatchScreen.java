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
public class ArrangeMatchScreen extends Container implements Screen {

    @Override
    public void bind(final Eventbus eventbus, final Node parent) {
        eventbus.subscribe(LoginSuccess.class, new EventListener<LoginSuccess>() {
            @Override
            public void onEvent(LoginSuccess event) {
                parent.attachChild(ArrangeMatchScreen.this);
            }
        });
        eventbus.subscribe(GameOver.class, new EventListener<GameOver>() {
            @Override
            public void onEvent(GameOver update) {
                parent.attachChild(ArrangeMatchScreen.this);
//                ((JFrame) SwingUtilities.windowForComponent(controller.getGamePanel())).setTitle(update.getWinner().longValue() == 0L ? "You won!" : "You lost...");
            }
        });
        eventbus.subscribe(JoinedMatchUpdate.class, new EventListener<JoinedMatchUpdate>() {
            @Override
            public void onEvent(JoinedMatchUpdate event) {
                parent.detachChild(ArrangeMatchScreen.this);
            }
        });

        setLocalTranslation(300, 300, 0);

        // Add some elements
        addChild(new Label("Hello, World."));
        final Checkbox botbox = addChild(new Checkbox("bot"));
        botbox.setChecked(true);
//        myWindow.addChild(, constraints)
        Button clickMe = addChild(new Button("start"));
        clickMe.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new DefaultMessage(new MatchRequest(botbox.isChecked())));
                System.out.println("The world is mine.");
            }
        });
    }
}
