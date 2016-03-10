package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.events.AppStartedEvent;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.master.commands.UserLogin;
import com.etherblood.cardsnetworkshared.master.updates.LoginSuccess;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;

/**
 *
 * @author Philipp
 */
public class LoginScreen extends Container implements Screen {

    @Override
    public void bind(final Eventbus eventbus, final Node parent) {
        eventbus.subscribe(AppStartedEvent.class, new EventListener<AppStartedEvent>() {
            @Override
            public void onEvent(AppStartedEvent event) {
                parent.attachChild(LoginScreen.this);
            }
        });
        eventbus.subscribe(LoginSuccess.class, new EventListener<LoginSuccess>() {
            @Override
            public void onEvent(LoginSuccess event) {
                System.out.println("login success: " + event.getUsername());
                LoginScreen.this.removeFromParent();
            }
        });

        setLocalTranslation(300, 300, 0);

        // Add some elements
        addChild(new Label("Hello, World."));
        final TextField username = addChild(new TextField("testuser"));
        final TextField password = addChild(new TextField("password"));
        Button clickMe = addChild(new Button("login"));
        clickMe.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                eventbus.sendEvent(new EncryptedMessage(new DefaultMessage(new UserLogin(username.getText(), password.getText()))));
                System.out.println("The world is yours.");
            }
        });
    }
}
