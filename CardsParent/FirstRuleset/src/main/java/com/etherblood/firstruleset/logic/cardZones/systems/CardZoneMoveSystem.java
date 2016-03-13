package com.etherblood.firstruleset.logic.cardZones.systems;

import com.etherblood.cardsmatch.cardgame.AbstractMatchSystem;
import com.etherblood.firstruleset.logic.cardZones.events.BoardAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.BoardDetachEvent;
import static com.etherblood.firstruleset.logic.cardZones.events.CardZone.BOARD;
import static com.etherblood.firstruleset.logic.cardZones.events.CardZone.GRAVEYARD;
import static com.etherblood.firstruleset.logic.cardZones.events.CardZone.HAND;
import static com.etherblood.firstruleset.logic.cardZones.events.CardZone.LIBRARY;
import com.etherblood.firstruleset.logic.cardZones.events.CardZoneMoveEvent;
import com.etherblood.firstruleset.logic.cardZones.events.GraveyardAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.GraveyardDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.HandAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.HandDetachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.LibraryAttachEvent;
import com.etherblood.firstruleset.logic.cardZones.events.LibraryDetachEvent;

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
