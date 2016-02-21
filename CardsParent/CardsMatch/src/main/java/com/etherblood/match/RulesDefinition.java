package com.etherblood.match;

import java.util.List;

/**
 *
 * @author Philipp
 */
public interface RulesDefinition {
    MatchContext start(List<PlayerDefinition> playerDefinitions);
}
