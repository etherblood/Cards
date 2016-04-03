package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmatch.cardgame.IdConverter;
import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsmatch.cardgame.NetworkPlayer;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.entitysystem.data.EntityId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class HumanPlayer extends AbstractPlayer implements NetworkPlayer<MatchUpdate>{
    private final long userId;
    private IdConverter converter;
    private final ArrayList<MatchUpdate> updateList = new ArrayList<>();
    private int receivedUpdates = 0;

    public HumanPlayer(long userId, EntityId player) {
        super(player);
        this.userId = userId;
        assert player != null;
    }

    public void triggerEffect(long source, long... targets) {
        triggerEffect(converter.fromLong(source), converter.fromLongs(targets));
    }
    
    @Override
    public void send(MatchUpdate update) {
        updateList.add(update);
//        connection.send(new CardsMessage(update));
    }
    
    @Override
    public void clearCache() {
        for (int i = updateList.size() - 1; i >= receivedUpdates; i--) {
            updateList.remove(i);
        }
    }
    
    public List<MatchUpdate> getLatestUpdates() {
        ArrayList<MatchUpdate> result = new ArrayList<>();
        while (receivedUpdates < updateList.size()) {            
            result.add(updateList.get(receivedUpdates++));
        }
        return result;
    }
    
    public void resetReceivedUpdates() {
        receivedUpdates = 0;
    }

    public long getUserId() {
        return userId;
    }

    public IdConverter getConverter() {
        return converter;
    }

    public void setConverter(IdConverter converter) {
        this.converter = converter;
        converter.setPlayer(this);
    }

    @Override
    public void setMatch(MatchContextWrapper match) {
        super.setMatch(match);
    }
    
}
