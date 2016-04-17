package com.etherblood.cardsmatch.cardgame.match;

import com.etherblood.cardscontext.MatchContext;
import com.etherblood.cardsmatch.cardgame.UpdateBuilder;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public interface RulesDefinition {
    String getName();
    MatchContext init(List<PlayerDefinition> playerDefinitions);
    List<String> getTemplateNames();
    Map<Class, UpdateBuilder> getUpdateBuilders();//TODO: remove
    void start(MatchContext context);
}
