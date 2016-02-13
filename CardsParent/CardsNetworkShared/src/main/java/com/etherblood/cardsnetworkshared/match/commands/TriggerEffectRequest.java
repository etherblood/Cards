package com.etherblood.cardsnetworkshared.match.commands;

import com.jme3.network.serializing.Serializable;
import com.etherblood.cardsnetworkshared.match.misc.MatchCommand;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
@Serializable
public class TriggerEffectRequest extends MatchCommand {
    private long source;
    private long[] targets;

    public TriggerEffectRequest() {
    }

    public TriggerEffectRequest(long source, long[] targets) {
        this.source = source;
        this.targets = targets;
    }

    public long getSource() {
        return source;
    }

    public long[] getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" + "source=" + source + ", targets=" + Arrays.toString(targets) + '}';
    }
}
