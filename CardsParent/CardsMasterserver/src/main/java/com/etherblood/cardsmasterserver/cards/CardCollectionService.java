package com.etherblood.cardsmasterserver.cards;

import com.etherblood.cardsmasterserver.cards.model.CardCollection;
import com.etherblood.cardsmasterserver.cards.model.CollectionType;
import com.etherblood.cardsmasterserver.matches.WonMatchEvent;
import com.etherblood.cardsmasterserver.network.connections.UserConnectionService;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.users.UserService;
import com.etherblood.cardsmasterserver.users.events.PreUserDeleteEvent;
import com.etherblood.cardsmasterserver.users.events.UserRegisteredEvent;
import com.etherblood.cardsmasterserver.users.model.UserAccount;
import com.etherblood.cardsnetworkshared.master.commands.CardCollectionRequest;
import com.etherblood.cardsnetworkshared.master.commands.SetLibraryRequest;
import com.etherblood.cardsnetworkshared.master.updates.CardCollectionUpdate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Philipp
 */
@Service
public class CardCollectionService {

    @Autowired
    private CardCollectionRepository cardsRepo;
    @Autowired
    private UserConnectionService connectionService;
    @Autowired
    private UserService userService;

    @MessageHandler
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public CardCollectionUpdate onCardCollectionRequest(CardCollectionRequest request) {
        CardCollection userCollection = cardsRepo.getFirstUserCollectionByType(connectionService.getCurrentUser().getId(), CollectionType.Collection);
        List<String> cards = userCollection.getCards();
        return new CardCollectionUpdate(cards.toArray(new String[cards.size()]));
    }

    @MessageHandler
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void onSetLibraryRequest(SetLibraryRequest request) {
//        UserAccount currentUser = connectionService.getCurrentUser();
//        CardCollection library = cardsRepo.getFirstUserCollectionByType(currentUser.getId(), CollectionType.Library);
//        library.setCards(Arrays.asList(request.getLibrary()));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM', 'ROLE_ADMIN', 'ROLE_USER')")
    public void giftCard(UserAccount user, String cardname, int amount) {
        CardCollection userCollection = cardsRepo.getFirstUserCollectionByType(user.getId(), CollectionType.Collection);
        for (int i = 0; i < amount; i++) {
            userCollection.getCards().add(cardname);
        }
    }

    @Transactional
    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void onUserRegistration(UserRegisteredEvent event) {
        CardCollection collection = new CardCollection();
        collection.setOwner(event.getUser());
        collection.setType(CollectionType.Collection);
        collection.setCards(new ArrayList<String>());
        for (int i = 0; i < 2; i++) {
            collection.getCards().add("Wisp");
            collection.getCards().add("River Crocolisk");
            collection.getCards().add("Black Dragon");
            collection.getCards().add("Faerie Dragon");
            collection.getCards().add("Mirth");
            collection.getCards().add("Leeroy Jenkins");
            collection.getCards().add("Elf");
            collection.getCards().add("Orc");
            collection.getCards().add("Test");
        }
//        collection.getCards().add("Leeroy Jenkins");
        cardsRepo.persist(collection);

        CardCollection library = new CardCollection();
        library.setOwner(event.getUser());
        library.setType(CollectionType.Library);
        library.setCards(new ArrayList<>(collection.getCards()));
        cardsRepo.persist(library);
    }
    
    @Transactional
    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void onUserWonMatch(WonMatchEvent event) {
//        giftCard(userService.getUser(event.getPlayer().getUserId()), templateService.getCollectibles()[new Random().nextInt(templateService.getCollectibles().length)], 1);
//        giftCard(userService.getUser(event.getPlayer().getUserId()), "Dunno", 1);
    }

    @Transactional
    @EventListener
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void onUserDeletion(PreUserDeleteEvent event) {
        List<CardCollection> userCollections = cardsRepo.getAllUserCollections(event.getUser().getId());
        for (CardCollection collection : userCollections) {
            cardsRepo.remove(collection);
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public CardCollection getActiveUserLibrary(long userId) {
        CardCollection library = cardsRepo.getFirstUserCollectionByType(connectionService.getCurrentUser().getId(), CollectionType.Collection);
//        CardCollection library = cardsRepo.getFirstUserCollectionByType(userId, CollectionType.Library);//TODO get active lib instead of first
//        if (!isSubCollection(cardsRepo.getFirstUserCollectionByType(userId, CollectionType.Collection), library)) {
//            throw new IllegalStateException("Library of user with id " + userId + " is invalid");
//        }
        return library;
    }

    private boolean isSubCollection(CardCollection main, CardCollection sub) {
        ArrayList<String> remaining = new ArrayList<>(main.getCards());
        for (String card : sub.getCards()) {
            if (!remaining.remove(card)) {
                return false;
            }
        }
        return true;
    }
}
