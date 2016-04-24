package com.etherblood.firstruleset.main;

import com.etherblood.cardscontext.MatchContext;
import com.etherblood.cardsmatch.cardgame.IdConverter;
import com.etherblood.cardsmatchapi.HumanProxy;
import com.etherblood.cardsmatchapi.IllegalCommandException;
import com.etherblood.cardsmatchapi.PlayerDefinition;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;
import com.etherblood.entitysystem.data.EntityId;
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
        EntityId source;
        EntityId[] targets;
        try {
            source = idConverter.fromLong(action.getSource());
            targets = idConverter.fromLongs(action.getTargets());
        } catch(IllegalArgumentException e) {
            throw new IllegalCommandException("passed id's are invalid", e);
        }
        apply(source, targets);
    }

    @Override
    public List<MatchUpdate> getTotalUpdates() {
        return updates;
    }

    public void setIdConverter(IdConverter idConverter) {
        this.idConverter = idConverter;
    }

}
