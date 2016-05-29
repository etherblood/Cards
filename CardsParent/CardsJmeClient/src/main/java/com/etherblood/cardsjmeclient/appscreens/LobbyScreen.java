package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.ScreenKeys;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.etherblood.cardsjmeclient.states.BotsState;
import com.etherblood.cardsjmeclient.states.CardCollectionState;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.master.commands.MatchRequest;
import com.etherblood.cardsnetworkshared.master.updates.BotTo;
import com.etherblood.cardsnetworkshared.master.updates.CardGroupTo;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.ListBox;
import com.simsilica.lemur.core.VersionedList;
import com.simsilica.lemur.list.DefaultCellRenderer;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class LobbyScreen extends AbstractScreen {

    @Autowire private CardCollectionState cardsState;
    @Autowire private BotsState botsState;
    private final VersionedList<CardGroupTo> playerLibraries = new VersionedList<>();
    private final VersionedList<BotTo> bots = new VersionedList<>();
    
    @PostConstruct
    public void bind() {
        getContainer().setLocalTranslation(300, 800, 0);

        // Add some elements
        getContainer().addChild(new Label("Lobby"));
        final ListBox<CardGroupTo> libraryList = new ListBox<>(playerLibraries
//                , new DefaultCellRenderer<CardGroupTo>() {
//            @Override
//            protected String valueToString(CardGroupTo value) {
//                return value.displayName;
//            }
//            
//        }, "glass"
        );
        getContainer().addChild(libraryList);
        final ListBox<BotTo> botList = new ListBox<>(bots
//                , new DefaultCellRenderer<BotTo>() {
//            @Override
//            protected String valueToString(BotTo value) {
//                return value.displayName;
//            }
//            
//        }, "glass"
        );
        getContainer().addChild(botList);
        final Checkbox botbox = getContainer().addChild(new Checkbox("bot"));
        botbox.setChecked(true);
//        myWindow.addChild(, constraints)
        Button clickMe = getContainer().addChild(new Button("start"));
        clickMe.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                MatchRequest request = new MatchRequest();
                Integer botSelection = libraryList.getSelectionModel().getSelection();
                request.setBot(botSelection == null? null: botList.getModel().get(botSelection));//TODO
                request.setLibraryId(libraryList.getModel().get(libraryList.getSelectionModel().getSelection()).id);
                getEventbus().sendEvent(new DefaultMessage(request));
            }
        });
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
        bots.clear();
        for (BotTo bot : botsState.getBots()) {
            bots.add(bot);
        }
    }

    @Override
    public ScreenKeys getScreenKey() {
        return ScreenKeys.LOBBY;
    }
}
