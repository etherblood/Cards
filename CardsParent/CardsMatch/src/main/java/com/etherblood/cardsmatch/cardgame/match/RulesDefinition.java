package com.etherblood.cardsmatch.cardgame.match;

import java.util.List;

/**
 *
 * @author Philipp
 */
public interface RulesDefinition {
    String getName();
    MatchContext start(List<PlayerDefinition> playerDefinitions);
    List<String> getTemplateNames();
}
