package com.etherblood.cardsjmeclient.appscreens;

import com.etherblood.cardscontext.Autowire;
import com.etherblood.cardsjmeclient.ScreenKeys;
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
import com.etherblood.cardsnetworkshared.match.updates.SetAttack;
import com.etherblood.cardsnetworkshared.match.updates.SetCost;
import com.etherblood.cardsnetworkshared.match.updates.SetHealth;
import com.etherblood.cardsnetworkshared.match.updates.SetOwner;
import com.etherblood.cardsnetworkshared.match.updates.SetProperty;
import com.etherblood.cardsnetworkshared.match.updates.SetZone;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class MatchScreen extends AbstractScreen {
    private final GameController controller = new GameController(new MatchContainer());
    
    @PostConstruct
    public void init() {
        getContainer().setLocalTranslation(0, 1000, 0);
        
        getContainer().addChild(controller.getGamePanel());
        controller.setCommandHandler(new CommandHandler() {
            @Override
            public void triggerEffect(long effect, long... targets) {
                getEventbus().sendEvent(new DefaultMessage(new TriggerEffectRequest(effect, targets)));
            }
        });
        
        createEventListeners();
    }

    private void createEventListeners() {
        getEventListeners().put(CreateEntity.class, new EventListener<CreateEntity>() {
            @Override
            public void onEvent(CreateEntity update) {
                controller.createCard(update.getCard(), update.getName());
            }
        });
        getEventListeners().put(SetAttack.class, new EventListener<SetAttack>() {
            @Override
            public void onEvent(SetAttack update) {
                controller.setCardAttack(update.getTarget(), update.getAttack());
            }
        });
        getEventListeners().put(SetHealth.class, new EventListener<SetHealth>() {
            @Override
            public void onEvent(SetHealth update) {
                controller.setCardHealth(update.getTarget(), update.getHealth());
            }
        });
        getEventListeners().put(SetCost.class, new EventListener<SetCost>() {
            @Override
            public void onEvent(SetCost update) {
                controller.setCardCost(update.getTarget(), update.getMana());
            }
        });
        getEventListeners().put(SetOwner.class, new EventListener<SetOwner>() {
            @Override
            public void onEvent(SetOwner update) {
                controller.setCardOwner(update.getTarget(), update.getOwner());
            }
        });
        getEventListeners().put(SetZone.class, new EventListener<SetZone>() {
            @Override
            public void onEvent(SetZone update) {
                controller.setCardZone(update.getTarget(), CardZone.values()[update.getZone()]);
            }
        });
        getEventListeners().put(AttachEffect.class, new EventListener<AttachEffect>() {
            @Override
            public void onEvent(AttachEffect update) {
                controller.attachCardEffect(update.getCard(), new Ability(update.getEffect(), update.getName()));
            }
        });
        getEventListeners().put(SetProperty.class, new EventListener<SetProperty>() {
            @Override
            public void onEvent(SetProperty update) {
                controller.setCardProperty(update.getCard(), update.getKey(), update.getValue() != 0);
            }
        });
        getEventListeners().put(DetachEffect.class, new EventListener<DetachEffect>() {
            @Override
            public void onEvent(DetachEffect update) {
                controller.detachCardEffect(update.getEffect());
            }
        });
        getEventListeners().put(AttackUpdate.class, new EventListener<AttackUpdate>() {
            @Override
            public void onEvent(AttackUpdate update) {
                controller.attack(update.getAttacker(), update.getDefender());
            }
        });
    }

    @Override
    public void onDetach() {
        controller.reset();
    }

    @Override
    public ScreenKeys getScreenKey() {
        return ScreenKeys.MATCH;
    }

}
