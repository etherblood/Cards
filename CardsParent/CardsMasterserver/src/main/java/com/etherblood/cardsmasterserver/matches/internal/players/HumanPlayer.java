package com.etherblood.cardsmasterserver.matches.internal.players;

import com.etherblood.cardsmasterserver.matches.internal.MatchContextWrapper;
import com.etherblood.cardsmatchapi.HumanProxy;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class HumanPlayer extends AbstractPlayer {
    private final HumanProxy proxy;
    private final long userId;
//    private IdConverter converter;
//    private final ArrayList<MatchUpdate> updateList = new ArrayList<>();
    private int receivedUpdates = 0;

    public HumanPlayer(long userId, HumanProxy proxy) {
        this.userId = userId;
        this.proxy = proxy;
    }

    public void triggerEffect(TriggerEffectRequest request) {
        proxy.applyAction(request);
    }
    
    @Override
    public void clearCache() {
        receivedUpdates = 0;
    }
    
    public List<MatchUpdate> getLatestUpdates() {
        List updates = proxy.getTotalUpdates();
        updates = updates.subList(receivedUpdates, updates.size());
        receivedUpdates = proxy.getTotalUpdates().size();
        return updates;
    }
    
    public void resetReceivedUpdates() {
        receivedUpdates = 0;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public void setMatch(MatchContextWrapper match) {
        super.setMatch(match);
    }

    @Override
    public HumanProxy getProxy() {
        return proxy;
    }
    
}
