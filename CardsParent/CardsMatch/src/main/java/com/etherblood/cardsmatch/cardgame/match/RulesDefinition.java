package com.etherblood.cardsmatch.cardgame.match;

import com.etherblood.cardsmatch.cardgame.UpdateBuilder;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public interface RulesDefinition {
    String getName();
    MatchContext start(List<PlayerDefinition> playerDefinitions);
    List<String> getTemplateNames();
    Map<Class, UpdateBuilder> getUpdateBuilders();
    void flush(MatchContext context);
}
