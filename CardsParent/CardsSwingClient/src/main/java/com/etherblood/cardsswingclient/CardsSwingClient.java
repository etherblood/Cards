package com.etherblood.cardsswingclient;

import com.etherblood.cardsnetworkshared.match.commands.TriggerEffectRequest;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.match.updates.AttachEffect;
import com.etherblood.cardsnetworkshared.match.updates.AttackUpdate;
import com.etherblood.cardsnetworkshared.match.updates.CreateEntity;
import com.etherblood.cardsnetworkshared.match.updates.DetachEffect;
import com.etherblood.cardsnetworkshared.match.updates.GameOver;
import com.etherblood.cardsnetworkshared.match.updates.SetAttack;
import com.etherblood.cardsnetworkshared.match.updates.SetCost;
import com.etherblood.cardsnetworkshared.match.updates.SetHealth;
import com.etherblood.cardsnetworkshared.match.updates.SetOwner;
import com.etherblood.cardsnetworkshared.match.updates.SetProperty;
import com.etherblood.cardsnetworkshared.match.updates.SetZone;
import com.etherblood.cardsswingdisplay.Ability;
import com.etherblood.cardsswingdisplay.CardZone;
import com.etherblood.cardsswingdisplay.CommandHandler;
import com.etherblood.cardsswingdisplay.GameController;
import com.etherblood.cardsswingdisplay.MainJFrame;
import com.jme3.network.Client;
import com.jme3.network.ErrorListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Philipp
 */
public class CardsSwingClient {

    private static final int PORT = 6145;
    private final HashMap<Class, UpdateHandler> updateHandlers = new HashMap<>();
    private final GameController controller;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        SerializationRegister.registerClasses();
        MainJFrame frame = new MainJFrame();
        CardsSwingClient cardsSwingClient = new CardsSwingClient(frame.getController());
        Client client = Network.connectToServer("localhost", PORT);
        cardsSwingClient.bind(client);
        client.start();
        frame.setVisible(true);
    }

    public CardsSwingClient(final GameController controller) {
        this.controller = controller;
        updateHandlers.put(CreateEntity.class, new UpdateHandler<CreateEntity>() {
            @Override
            public void handle(CreateEntity update) {
                controller.createCard(update.getCard(), update.getName());
            }
        });
        updateHandlers.put(SetAttack.class, new UpdateHandler<SetAttack>() {
            @Override
            public void handle(SetAttack update) {
                controller.setCardAttack(update.getTarget(), update.getAttack());
            }
        });
        updateHandlers.put(SetHealth.class, new UpdateHandler<SetHealth>() {
            @Override
            public void handle(SetHealth update) {
                controller.setCardHealth(update.getTarget(), update.getHealth());
            }
        });
        updateHandlers.put(SetCost.class, new UpdateHandler<SetCost>() {
            @Override
            public void handle(SetCost update) {
                controller.setCardCost(update.getTarget(), update.getMana());
            }
        });
        updateHandlers.put(SetOwner.class, new UpdateHandler<SetOwner>() {
            @Override
            public void handle(SetOwner update) {
                controller.setCardOwner(update.getTarget(), update.getOwner());
            }
        });
        updateHandlers.put(SetZone.class, new UpdateHandler<SetZone>() {
            @Override
            public void handle(SetZone update) {
                controller.setCardZone(update.getTarget(), CardZone.values()[update.getZone()]);
            }
        });
        updateHandlers.put(GameOver.class, new UpdateHandler<GameOver>() {
            @Override
            public void handle(GameOver update) {
                ((JFrame) SwingUtilities.windowForComponent(controller.getGamePanel())).setTitle(update.getWinner().longValue() == 0L ? "You won!" : "You lost...");
            }
        });
        updateHandlers.put(AttachEffect.class, new UpdateHandler<AttachEffect>() {
            @Override
            public void handle(AttachEffect update) {
                controller.attachCardEffect(update.getCard(), new Ability(update.getEffect(), update.getName()));
            }
        });
        updateHandlers.put(SetProperty.class, new UpdateHandler<SetProperty>() {
            @Override
            public void handle(SetProperty update) {
                controller.setCardProperty(update.getCard(), update.getKey(), update.getValue() != 0);
            }
        });
        updateHandlers.put(DetachEffect.class, new UpdateHandler<DetachEffect>() {
            @Override
            public void handle(DetachEffect update) {
                controller.detachCardEffect(update.getEffect());
            }
        });
        updateHandlers.put(AttackUpdate.class, new UpdateHandler<AttackUpdate>() {
            @Override
            public void handle(AttackUpdate update) {
                controller.attack(update.getAttacker(), update.getDefender());
            }
        });
    }

    public void bind(final Client client) {
        controller.setCommandHandler(new CommandHandler() {
            @Override
            public void triggerEffect(long effect, long... targets) {
                client.send(new DefaultMessage(new TriggerEffectRequest(effect, targets)));
            }
        });
        client.addMessageListener(new MessageListener<Client>() {
            @Override
            public void messageReceived(Client source, Message m) {
                final Object data = ((DefaultMessage) m).getData();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateHandlers.get(data.getClass()).handle(data);
                        controller.getGamePanel().validate();
                        controller.getGamePanel().repaint();
                    }
                });
            }
        }, DefaultMessage.class);
        client.addErrorListener(new ErrorListener<Client>() {
            @Override
            public void handleError(Client source, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }
}
