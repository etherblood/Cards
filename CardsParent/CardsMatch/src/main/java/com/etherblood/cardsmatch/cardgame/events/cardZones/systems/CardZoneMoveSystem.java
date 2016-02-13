package com.etherblood.cardsmatch.cardgame.events.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardDetachEvent;
import static com.etherblood.cardsmatch.cardgame.events.cardZones.CardZone.BOARD;
import static com.etherblood.cardsmatch.cardgame.events.cardZones.CardZone.GRAVEYARD;
import static com.etherblood.cardsmatch.cardgame.events.cardZones.CardZone.HAND;
import static com.etherblood.cardsmatch.cardgame.events.cardZones.CardZone.LIBRARY;
import com.etherblood.cardsmatch.cardgame.events.cardZones.CardZoneMoveEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.GraveyardDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.HandDetachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryAttachEvent;
import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryDetachEvent;

/**
 *
 * @author Philipp
 */
public class CardZoneMoveSystem extends AbstractMatchSystem<CardZoneMoveEvent> {

    @Override
    public CardZoneMoveEvent handle(CardZoneMoveEvent event) {
        switch(event.from) {
            case BOARD:
                enqueueEvent(new BoardDetachEvent(event.target));
                break;
            case HAND:
                enqueueEvent(new HandDetachEvent(event.target));
                break;
            case LIBRARY:
                enqueueEvent(new LibraryDetachEvent(event.target));
                break;
            case GRAVEYARD:
                enqueueEvent(new GraveyardDetachEvent(event.target));
                break;
            default:
                throw new IllegalStateException(event.from.name());
            
        }
        
        switch(event.to) {
            case BOARD:
                enqueueEvent(new BoardAttachEvent(event.target));
                break;
            case HAND:
                enqueueEvent(new HandAttachEvent(event.target));
                break;
            case LIBRARY:
                enqueueEvent(new LibraryAttachEvent(event.target));
                break;
            case GRAVEYARD:
                enqueueEvent(new GraveyardAttachEvent(event.target));
                break;
            default:
                throw new IllegalStateException(event.to.name());
            
        }
        return event;
    }

}
