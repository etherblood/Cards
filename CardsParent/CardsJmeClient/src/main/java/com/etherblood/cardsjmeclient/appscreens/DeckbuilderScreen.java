package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.etherblood.cardsjmeclient.states.CardCollectionState;
import com.etherblood.cardsnetworkshared.master.updates.CardGroupTo;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.core.VersionedList;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class DeckbuilderScreen extends AbstractScreen {
    @Autowire private CardCollectionState cardsState;
    private final VersionedList<CardGroupTo> playerLibraries = new VersionedList<>();

    @PostConstruct
    public void init() {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Deckbuilder"));
        Button mainMenuButton = getContainer().addChild(new Button("Main Menu"));
        mainMenuButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                getEventbus().sendEvent(new ScreenRequestEvent(ScreenKeys.MAIN_MENU));
            }
        });
    }
    
    @Override
    public void onAttach() {
        super.onAttach();
        playerLibraries.clear();
        for (CardGroupTo cardGroup : cardsState.getCardGroups()) {
            playerLibraries.add(cardGroup);
        }
    }

    @Override
    public ScreenKeys getScreenKey() {
        return ScreenKeys.DECKBUILDER;
    }

}
