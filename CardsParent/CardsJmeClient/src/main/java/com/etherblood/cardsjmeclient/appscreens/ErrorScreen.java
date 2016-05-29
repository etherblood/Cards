package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.ScreenKeys;
import com.simsilica.lemur.Label;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class ErrorScreen extends AbstractScreen {

    @PostConstruct
    public void init() {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Error"));
    }

    @Override
    public ScreenKeys getScreenKey() {
        return ScreenKeys.ERROR;
    }

}
