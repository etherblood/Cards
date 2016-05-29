package com.etherblood.cardsjmeclient.states;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.master.commands.AvailableBotsRequest;
import com.etherblood.cardsnetworkshared.master.commands.CardCollectionRequest;
import com.etherblood.cardsnetworkshared.master.updates.AvailableBotsUpdate;
import com.etherblood.cardsnetworkshared.master.updates.BotTo;
import com.etherblood.cardsnetworkshared.master.updates.CardCollectionUpdate;
import com.etherblood.cardsnetworkshared.master.updates.LoginSuccess;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class BotsState {
    private final List<BotTo> bots = new ArrayList<>();
    
    @Autowire private Eventbus eventbus;
    
    @PostConstruct
    public void init() {
        eventbus.subscribe(LoginSuccess.class, new EventListener<LoginSuccess>() {
            @Override
            public void onEvent(LoginSuccess event) {
                eventbus.sendEvent(new DefaultMessage(new AvailableBotsRequest()));
            }
        });
        eventbus.subscribe(AvailableBotsUpdate.class, new EventListener<AvailableBotsUpdate>() {
            @Override
            public void onEvent(AvailableBotsUpdate event) {
                bots.clear();
                bots.addAll(event.bots);
            }
        });
    }

    public List<BotTo> getBots() {
        return bots;
    }
}
