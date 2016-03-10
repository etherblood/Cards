/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmatch.cardgame.bot;

import com.etherblood.cardsmatch.cardgame.bot.commands.Command;

/**
 *
 * @author Philipp
 */
public interface Bot {

    Command think();

    void clearCache();
    
}
