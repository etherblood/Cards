package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.events.AppStartedEvent;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.master.commands.MatchRequest;
import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.etherblood.cardsnetworkshared.master.commands.UserRegistration;
import com.etherblood.cardsnetworkshared.master.updates.LoginSuccess;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;

/**
 *
 * @author Philipp
 */
public class LoginScreen extends AbstractScreen {

    @Override
    public void bind(final Eventbus eventbus) {
        getContainer().setLocalTranslation(300, 300, 0);

        // Add some elements
        getContainer().addChild(new Label("Hello, World."));
        final TextField username = getContainer().addChild(new TextField("testuser"));
        final TextField password = getContainer().addChild(new TextField("password"));
        Button loginButton = getContainer().addChild(new Button("login"));
        loginButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new EncryptedMessage(new DefaultMessage(new UserLogin(username.getText(), password.getText()))));
            }
        });
        Button registerButton = getContainer().addChild(new Button("register"));
        registerButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new EncryptedMessage(new DefaultMessage(new UserRegistration(username.getText(), password.getText()))));
            }
        });
    }
}
