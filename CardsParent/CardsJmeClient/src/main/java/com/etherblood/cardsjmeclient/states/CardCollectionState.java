package com.etherblood.cardsjmeclient.states;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.master.commands.CardCollectionRequest;
import com.etherblood.cardsnetworkshared.master.updates.CardCollectionUpdate;
import com.etherblood.cardsnetworkshared.master.updates.CardGroupTo;
import com.etherblood.cardsnetworkshared.master.updates.LoginSuccess;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class CardCollectionState {
    private final List<CardGroupTo> cardGroups = new ArrayList<>();
    private final List<CardGroupTo> botCardGroups = new ArrayList<>();
    @Autowire private Eventbus eventbus;
    
    @PostConstruct
    public void init() {
        eventbus.subscribe(LoginSuccess.class, new EventListener<LoginSuccess>() {
            @Override
            public void onEvent(LoginSuccess event) {
                eventbus.sendEvent(new DefaultMessage(new CardCollectionRequest()));
            }
        });
        eventbus.subscribe(CardCollectionUpdate.class, new EventListener<CardCollectionUpdate>() {
            @Override
            public void onEvent(CardCollectionUpdate event) {
                cardGroups.clear();
                cardGroups.addAll(event.libraries);
            }
        });
    }
    
    public List<CardGroupTo> getCardGroups() {
        return cardGroups;
    }

    public List<CardGroupTo> getBotCardGroups() {
        return botCardGroups;
    }
}
