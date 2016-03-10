package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.match.Ability;
import com.etherblood.cardsjmeclient.match.CommandHandler;
import com.etherblood.cardsjmeclient.match.GameController;
import com.etherblood.cardsjmeclient.match.MatchContainer;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.match.misc.CardZone;
import com.etherblood.cardsnetworkshared.match.updates.AttachEffect;
import com.etherblood.cardsnetworkshared.match.updates.AttackUpdate;
import com.etherblood.cardsnetworkshared.match.updates.CreateEntity;
import com.etherblood.cardsnetworkshared.match.updates.DetachEffect;
import com.etherblood.cardsnetworkshared.match.updates.GameOver;
import com.etherblood.cardsnetworkshared.match.updates.JoinedMatchUpdate;
import com.etherblood.cardsnetworkshared.match.updates.SetAttack;
import com.etherblood.cardsnetworkshared.match.updates.SetCost;
import com.etherblood.cardsnetworkshared.match.updates.SetHealth;
import com.etherblood.cardsnetworkshared.match.updates.SetOwner;
import com.etherblood.cardsnetworkshared.match.updates.SetProperty;
import com.etherblood.cardsnetworkshared.match.updates.SetZone;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;

/**
 *
 * @author Philipp
 */
public class MatchScreen extends Container implements Screen {

    
    @Override
    public void bind(final Eventbus eventbus, final Node parent) {
        final GameController controller = new GameController(new MatchContainer());
        eventbus.subscribe(JoinedMatchUpdate.class, new EventListener<JoinedMatchUpdate>() {
            @Override
            public void onEvent(JoinedMatchUpdate event) {
                parent.attachChild(MatchScreen.this);
            }
        });
        setLocalTranslation(0, 1000, 0);

//        Card card = new Card();
//        addChild(card);
//        card.setCardName("testcard");
//        card.setCost(3);
//        card.setAttack(7);
//        card.setHealth(5);
        
        addChild(controller.getGamePanel());
        controller.setCommandHandler(new CommandHandler() {
            @Override
            public void triggerEffect(long effect, long... targets) {
                eventbus.sendEvent(new DefaultMessage(new TriggerEffectRequest(effect, targets)));
            }
        });
        
        eventbus.subscribe(CreateEntity.class, new EventListener<CreateEntity>() {
            @Override
            public void onEvent(CreateEntity update) {
                controller.createCard(update.getCard(), update.getName());
            }
        });
        eventbus.subscribe(SetAttack.class, new EventListener<SetAttack>() {
            @Override
            public void onEvent(SetAttack update) {
                controller.setCardAttack(update.getTarget(), update.getAttack());
            }
        });
        eventbus.subscribe(SetHealth.class, new EventListener<SetHealth>() {
            @Override
            public void onEvent(SetHealth update) {
                controller.setCardHealth(update.getTarget(), update.getHealth());
            }
        });
        eventbus.subscribe(SetCost.class, new EventListener<SetCost>() {
            @Override
            public void onEvent(SetCost update) {
                controller.setCardCost(update.getTarget(), update.getMana());
            }
        });
        eventbus.subscribe(SetOwner.class, new EventListener<SetOwner>() {
            @Override
            public void onEvent(SetOwner update) {
                controller.setCardOwner(update.getTarget(), update.getOwner());
            }
        });
        eventbus.subscribe(SetZone.class, new EventListener<SetZone>() {
            @Override
            public void onEvent(SetZone update) {
                controller.setCardZone(update.getTarget(), CardZone.values()[update.getZone()]);
            }
        });
        eventbus.subscribe(GameOver.class, new EventListener<GameOver>() {
            @Override
            public void onEvent(GameOver update) {
                parent.detachChild(MatchScreen.this);
                controller.reset();
//                ((JFrame) SwingUtilities.windowForComponent(controller.getGamePanel())).setTitle(update.getWinner().longValue() == 0L ? "You won!" : "You lost...");
            }
        });
        eventbus.subscribe(AttachEffect.class, new EventListener<AttachEffect>() {
            @Override
            public void onEvent(AttachEffect update) {
                controller.attachCardEffect(update.getCard(), new Ability(update.getEffect(), update.getName()));
            }
        });
        eventbus.subscribe(SetProperty.class, new EventListener<SetProperty>() {
            @Override
            public void onEvent(SetProperty update) {
                controller.setCardProperty(update.getCard(), update.getKey(), update.getValue() != 0);
            }
        });
        eventbus.subscribe(DetachEffect.class, new EventListener<DetachEffect>() {
            @Override
            public void onEvent(DetachEffect update) {
                controller.detachCardEffect(update.getEffect());
            }
        });
        eventbus.subscribe(AttackUpdate.class, new EventListener<AttackUpdate>() {
            @Override
            public void onEvent(AttackUpdate update) {
                controller.attack(update.getAttacker(), update.getDefender());
            }
        });
    }

}
