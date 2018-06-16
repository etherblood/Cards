package com.etherblood.cardsmasterserver.cards;

import com.etherblood.cardsmasterserver.cards.model.Card;
import com.etherblood.cardsmasterserver.cards.model.CardGroup;
import com.etherblood.cardsmasterserver.cards.model.CardTemplate;
import com.etherblood.cardsmasterserver.cards.model.CollectionType;
import com.etherblood.cardsmasterserver.matches.WonMatchEvent;
import com.etherblood.cardsmasterserver.network.connections.UserConnectionService;
import com.etherblood.cardsmasterserver.network.messages.MessageHandler;
import com.etherblood.cardsmasterserver.users.UserService;
import com.etherblood.cardsmasterserver.users.events.PreUserDeleteEvent;
import com.etherblood.cardsmasterserver.users.events.UserRegisteredEvent;
import com.etherblood.cardsnetworkshared.master.commands.AvailableBotsRequest;
import com.etherblood.cardsnetworkshared.master.commands.CardCollectionRequest;
import com.etherblood.cardsnetworkshared.master.updates.AvailableBotsUpdate;
import com.etherblood.cardsnetworkshared.master.updates.BotTo;
import com.etherblood.cardsnetworkshared.master.updates.CardCollectionUpdate;
import com.etherblood.cardsnetworkshared.master.updates.CardGroupTo;
import com.etherblood.cardsnetworkshared.master.updates.CardsTo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public class CardService {

    @Autowired
    private CardRepository cardsRepo;
    @Autowired
    private CardGroupRepository cardGroupsRepo;
    @Autowired
    private CardTemplateRepository cardTemplateRepo;
    @Autowired
    private UserConnectionService connectionService;
    @Autowired
    private UserService userService;

    @MessageHandler
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public CardCollectionUpdate onCardCollectionRequest(CardCollectionRequest request) {
        List<CardGroup> groups = cardGroupsRepo.getGroupsForOwner(connectionService.getCurrentUser().getId());
        CardCollectionUpdate update = new CardCollectionUpdate();
        for (CardGroup group : groups) {
            switch(group.getType()) {
                case Library:
                    update.libraries.add(makeTransferObject(group));
                    break;
                case Collection:
                    update.collection = makeTransferObject(group);
                    break;
            }
        }
        return update;
    }
    
    @MessageHandler
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public AvailableBotsUpdate onAvailableBotsRequest(AvailableBotsRequest event) {
        AvailableBotsUpdate update = new AvailableBotsUpdate();
        update.bots = new ArrayList<>();
        List<CardGroup> botGroups = cardGroupsRepo.getBotLibraries();
        if(botGroups.isEmpty()) {
            for (int j = 0; j < 5; j++) {
                CardGroup collection = new CardGroup();
                collection.setDisplayName("Random" + j);
                collection.setType(CollectionType.Library);
                collection.setCards(new HashSet<>());
                cardGroupsRepo.persist(collection);
                for (CardTemplate template : cardTemplateRepo.randomTemplates(20)) {
                    addCard(collection, template.getName(), 1);
                }
                botGroups.add(collection);
            }
        }
        for (CardGroup botGroup : botGroups) {
            BotTo bot = new BotTo();
            bot.library = makeTransferObject(botGroup);
            bot.displayName = bot.library.displayName + " - Bot";
            update.bots.add(bot);
        }
        return update;
    }
    
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public CardGroup findCardGroup(long groupId) {
        return cardGroupsRepo.findCardGroup(groupId);
    }

    private CardGroupTo makeTransferObject(CardGroup group) {
        CardGroupTo groupTo = new CardGroupTo();
        groupTo.id = group.getId();
        groupTo.displayName = group.getDisplayName();
        for (Card card : group.getCards()) {
            groupTo.cards.add(makeTransferObject(card));
        }
        return groupTo;
    }

    private CardsTo makeTransferObject(Card card) {
        CardsTo cardsTo = new CardsTo();
        cardsTo.templateId = card.getTemplate().getId();
        cardsTo.amount = card.getAmount();
        return cardsTo;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM', 'ROLE_ADMIN', 'ROLE_USER')")
    public void giftCard(long userId, String templateName, int amount) {
        CardGroup userCollection = cardGroupsRepo.getFirstUserCollectionByType(userId, CollectionType.Collection);
        CardTemplate template = cardTemplateRepo.findByName(templateName);
        cardsRepo.addCardToGroup(userCollection.getId(), template.getId(), amount);
    }

    @Transactional
    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void onUserRegistration(UserRegisteredEvent event) {
        CardGroup collection = new CardGroup();
        collection.setDisplayName("Collection");
        collection.setOwner(event.getUser());
        collection.setType(CollectionType.Collection);
        collection.setCards(new HashSet<>());
        cardGroupsRepo.persist(collection);
        addCard(collection, "Wisp", 3);
        addCard(collection, "River Crocolisk", 4);
        addCard(collection, "Faerie Dragon", 3);
        addCard(collection, "Mirth", 2);
        addCard(collection, "Leeroy Jenkins", 1);
        addCard(collection, "Black Dragon", 1);
        
        CardGroup library = new CardGroup();
        library.setDisplayName("Library");
        library.setOwner(event.getUser());
        library.setType(CollectionType.Library);
        library.setCards(new HashSet<>());
        cardGroupsRepo.persist(library);
        addCard(library, "Wisp", 3);
        addCard(library, "River Crocolisk", 4);
        addCard(library, "Faerie Dragon", 3);
        addCard(library, "Mirth", 2);
        addCard(library, "Leeroy Jenkins", 1);
        addCard(library, "Black Dragon", 1);
    }
    
    private void addCard(CardGroup collection, String templateName, int amount) {
        cardsRepo.addCardToGroup(collection.getId(), cardTemplateRepo.findByName(templateName).getId(), amount);
    }
    
    @Transactional
    @EventListener
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void onUserWonMatch(WonMatchEvent event) {
        CardTemplate randomTemplate = cardTemplateRepo.randomTemplates(1).get(0);
        giftCard(event.getPlayer().getUserId(), randomTemplate.getName(), 1);
//        giftCard(userService.getUser(event.getPlayer().getUserId()), templateService.getCollectibles()[new Random().nextInt(templateService.getCollectibles().length)], 1);
//        giftCard(userService.getUser(event.getPlayer().getUserId()), "Dunno", 1);
    }

    @Transactional
    @EventListener
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void onUserDeletion(PreUserDeleteEvent event) {
        List<CardGroup> userCollections = cardGroupsRepo.getAllUserCardGroups(event.getUser().getId());
        for (CardGroup collection : userCollections) {
            cardGroupsRepo.remove(collection);
        }
    }

//    @Transactional
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public CardCollection getActiveUserLibrary(long userId) {
//        CardCollection library = cardsRepo.getFirstUserCollectionByType(connectionService.getCurrentUser().getId(), CollectionType.Collection);
////        CardCollection library = cardsRepo.getFirstUserCollectionByType(userId, CollectionType.Library);//TODO get active lib instead of first
////        if (!isSubCollection(cardsRepo.getFirstUserCollectionByType(userId, CollectionType.Collection), library)) {
////            throw new IllegalStateException("Library of user with id " + userId + " is invalid");
////        }
//        return library;
//    }

    private boolean isSubCollection(CardGroup main, CardGroup sub) {
        Map<Long, Card> mainCards = new HashMap<>();
        for (Card card : main.getCards()) {
            mainCards.put(card.getTemplate().getId(), card);
        }
        for (Card card : sub.getCards()) {
            Card mainCard = mainCards.get(card.getTemplate().getId());
            if(mainCard == null || mainCard.getAmount() < card.getAmount()) {
                return false;
            }
        }
        return true;
    }

    public void tryRegisterCard(String templateName) {
        CardTemplate template = cardTemplateRepo.findByName(templateName);
        if(template == null) {
            template = new CardTemplate();
            template.setName(templateName);
            cardTemplateRepo.persist(template);
        }
    }
}
