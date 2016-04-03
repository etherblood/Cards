package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.events.Eventbus;
import com.simsilica.lemur.Label;

/**
 *
 * @author Philipp
 */
public class ErrorScreen extends AbstractScreen {

    @Override
    public void bind(Eventbus eventbus) {
        getContainer().setLocalTranslation(300, 300, 0);

        // Add some elements
        getContainer().addChild(new Label("Error"));
    }

}
