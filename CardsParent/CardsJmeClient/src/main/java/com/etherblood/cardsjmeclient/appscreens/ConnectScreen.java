package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.Main;
import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.states.ConnectionState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;
import java.io.IOException;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class ConnectScreen extends AbstractScreen {
    @Autowire ConnectionState connectionState;

    @PostConstruct
    public void init() {
        getContainer().setLocalTranslation(300, 800, 0);
        getContainer().setPreferredSize(new Vector3f(200, 100, 0));
        // Add some elements
        getContainer().addChild(new Label("Connect"));
        final TextField ipAddressField = getContainer().addChild(new TextField(Main.ipAddress));
        final TextField portField = getContainer().addChild(new TextField("6145"));
        Button connectButton = getContainer().addChild(new Button("connect"));
        connectButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                try {
                    connectionState.connect(ipAddressField.getText(), Integer.parseUnsignedInt(portField.getText()));
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    @Override
    public ScreenKeys getScreenKey() {
        return ScreenKeys.CONNECT;
    }
}
