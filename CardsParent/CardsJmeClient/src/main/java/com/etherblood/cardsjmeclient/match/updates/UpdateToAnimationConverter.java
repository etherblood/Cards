package com.etherblood.cardsjmeclient.match.updates;

import com.etherblood.cardsjmeclient.match.animations.base.Animation;
import com.etherblood.cardsnetworkshared.match.misc.MatchUpdate;

/**
 *
 * @author Philipp
 */
public interface UpdateToAnimationConverter {
    Animation createAnimation(MatchUpdate update);
}
