package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.MatchContext;
import com.etherblood.cardsmatch.cardgame.IdConverter;
import com.etherblood.cardsmatchapi.HumanProxy;
import com.etherblood.cardsmatchapi.PlayerDefinition;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class HumanProxyImpl extends AbstractPlayerProxy implements HumanProxy<TriggerEffectRequest, MatchUpdate>{
    private final List<MatchUpdate> updates = new ArrayList<>();
    private IdConverter idConverter;

    public HumanProxyImpl(MatchContext context, PlayerDefinition definition) {
        super(context, definition);
    }

    @Override
    public void applyAction(TriggerEffectRequest action) {
        synchronized(getContext()) {
            apply(idConverter.fromLong(action.getSource()), idConverter.fromLongs(action.getTargets()));
        }
    }

    @Override
    public List<MatchUpdate> getTotalUpdates() {
        return updates;
    }

    public void setIdConverter(IdConverter idConverter) {
        this.idConverter = idConverter;
    }

}
