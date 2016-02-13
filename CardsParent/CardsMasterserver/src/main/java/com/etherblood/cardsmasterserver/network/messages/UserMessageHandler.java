/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.cardsmasterserver.network.messages;

/**
 *
 * @author Philipp
 */
public interface UserMessageHandler<T> {
    void onMessage(T message) throws Exception;
}
