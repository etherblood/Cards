package com.etherblood.cardsmatchapi;

import com.etherblood.logging.Logger;
import java.util.List;

/**
 *
 * @author Philipp
 */
public interface RulesDefinition<A, U> {
    String getName();
    List<String> getTemplateNames();
    MatchBuilder<A, U> createMatchBuilder(Logger logger);
//    Map<Class, UpdateBuilder> getUpdateBuilders();//TODO: remove
//    MatchContext init();
//    HumanProxy<A, U> attachHuman(MatchContext context, PlayerDefinition def);
//    BotProxy attachBot(MatchContext context, PlayerDefinition def);
//    SpectatorProxy<U> attachSpectator(MatchContext context);
////    void attachUpdateListener(MatchContext context, EntityId player, NetworkPlayer listener);
////    Bot createBot(MatchContext context, EntityId player);
//    void start(MatchContext context);
}
