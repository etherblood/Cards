package com.etherblood.cardsmatch.cardgame.components;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Philipp
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ComponentAlias {
    String name();
}
