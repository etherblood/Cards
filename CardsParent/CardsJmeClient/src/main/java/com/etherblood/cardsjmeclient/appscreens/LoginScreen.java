package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.master.commands.CardCollectionRequest;
import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.etherblood.cardsnetworkshared.master.commands.UserRegistration;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class LoginScreen extends AbstractScreen {

    @PostConstruct
    public void init() {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Hello, World."));
        final TextField username = getContainer().addChild(new TextField("testuser"));
        final TextField password = getContainer().addChild(new TextField("password"));
        Button loginButton = getContainer().addChild(new Button("login"));
        loginButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                getEventbus().sendEvent(new EncryptedMessage(new DefaultMessage(new UserLogin(username.getText(), password.getText()))));
            }
        });
        Button registerButton = getContainer().addChild(new Button("register"));
        registerButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                getEventbus().sendEvent(new EncryptedMessage(new DefaultMessage(new UserRegistration(username.getText(), password.getText()))));
            }
        });
    }

    @Override
    public ScreenKeys getScreenKey() {
        return ScreenKeys.LOGIN;
    }
}
