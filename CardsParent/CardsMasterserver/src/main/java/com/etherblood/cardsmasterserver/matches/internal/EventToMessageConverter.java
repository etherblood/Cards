package com.etherblood.cardsmasterserver.matches.internal;

import com.etherblood.cardsmasterserver.matches.internal.players.HumanPlayer;
import com.etherblood.cardsmatch.cardgame.client.SystemsEventHandler;
import com.etherblood.eventsystem.GameEvent;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class EventToMessageConverter implements SystemsEventHandler {
    private List<HumanPlayer> players;
    private final Map<Class, UpdateBuilder> updateBuilders;
    
    public MatchLogger matchLogger;

    public EventToMessageConverter(Map<Class, UpdateBuilder> updateBuilders) {
        this.updateBuilders = updateBuilders;
    }

    public void setPlayers(List<HumanPlayer> players) {
        this.players = players;
    }
    
    @Override
    public void onEvent(Class systemClass, GameEvent gameEvent) {
        if(matchLogger != null) {
            matchLogger.onEvent(systemClass, gameEvent);
        }
//        System.out.println(systemClass.getSimpleName() + " | " + gameEvent.getClass().getSimpleName());
        for (HumanPlayer player : players) {
            UpdateBuilder updateBuilder = updateBuilders.get(systemClass);
            if (updateBuilder != null) {
                player.send(updateBuilder.build(player.getMatch().getData(), player.getConverter(), gameEvent));
            }
        }
    }

}
