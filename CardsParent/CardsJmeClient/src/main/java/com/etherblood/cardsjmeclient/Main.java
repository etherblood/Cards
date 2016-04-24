package com.etherblood.cardsjmeclient;

import com.etherblood.cardsnetworkshared.ExtendedDefaultClient;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.EventbusImpl;
import com.etherblood.cardsjmeclient.events.ExceptionEvent;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.etherblood.cardsjmeclient.match.cards.TemplatesReader;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import com.etherblood.cardsnetworkshared.EncryptedMessage;
import com.etherblood.cardsnetworkshared.SerializerInit;
import com.jme3.app.SimpleApplication;
import com.jme3.network.AbstractMessage;
import com.jme3.network.Client;
import com.jme3.network.ErrorListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private static final int PORT = 6145;
    private Client client;
//    private Card testCard;

    private final Eventbus eventbus = new EventbusImpl();

    public static void main(String[] args) throws Exception {
        SerializerInit.init();

        try {
            TemplatesReader.INSTANCE.read("");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        String ipAddress = args.length != 0 ? args[0] : "213.73.99.162";
        final Main app = new Main();
        app.client = ExtendedDefaultClient.connectToServer(ipAddress, PORT);//Network.connectToServer(ipAddress, PORT);

        final MessageListener<Client> defaultMessageListener = new MessageListener<Client>() {
            @Override
            public void messageReceived(Client source, Message m) {
                DefaultMessage message = (DefaultMessage) m;
                app.fireSyncedEvent(message.getData());
            }
        };
        final MessageListener<Client> encryptedMessageListener = new MessageListener<Client>() {
            @Override
            public void messageReceived(Client connection, Message message) {
                defaultMessageListener.messageReceived(connection, ((EncryptedMessage) message).getMessage());
            }
        };
        app.client.addMessageListener(defaultMessageListener, DefaultMessage.class);
        app.client.addMessageListener(encryptedMessageListener, EncryptedMessage.class);
        app.eventbus.subscribe(AbstractMessage.class, new EventListener<AbstractMessage>() {
            @Override
            public void onEvent(AbstractMessage event) {
                app.client.send(event);
            }
        });
        app.client.addErrorListener(new ErrorListener<Client>() {
            @Override
            public void handleError(Client s, Throwable thrwbl) {
                app.fireSyncedEvent(new ExceptionEvent(thrwbl));
            }
        });
        app.client.start();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() - 50;
        int height = (int) screenSize.getHeight() - 50;

        AppSettings appSettings = new AppSettings(true);
        appSettings.setFrameRate(200);
//        appSettings.setFullscreen(true);
        appSettings.setResolution(width, height);
        appSettings.setAudioRenderer(AppSettings.LWJGL_OPENAL);
        app.setShowSettings(false);
        app.setSettings(appSettings);
        app.start();
    }

    public void fireSyncedEvent(final Object event) {
        enqueue(new Runnable() {
            @Override
            public void run() {
                eventbus.sendEvent(event);
            }
        });
    }

    @Override
    public void simpleInitApp() {
//        getAssetManager().registerLocator("http://wow.zamimg.com/hearthhead/sounds/", UrlLocator.class);
//        getAudioRenderer().playSource(new AudioNode(getAssetManager(), "VO_EX1_116_Play_01.ogg", true));
//        getAudioRenderer().playSource(new AudioNode(getAssetManager(), "Pegasus_Stinger_Leeroy_Jenkins.ogg", true));

        // Initialize the globals access so that the default
        // components can find what they need.
        GuiGlobals.initialize(this);
        // Load the 'glass' style
        BaseStyles.loadGlassStyle();
        // Set 'glass' as the default style when not specified
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        ScreenManager<ScreenKeys> manager = new InitScreens().create(getGuiNode(), eventbus);
//        new LoginScreen().bind(eventbus, getGuiNode());
//        new ArrangeMatchScreen().bind(eventbus, getGuiNode());
//        new MatchScreen().bind(eventbus, getGuiNode());
//        LoginAppstate loginAppstate = new LoginAppstate(eventbus);
//        ArrangeMatchAppstate arrangeMatchAppstate = new ArrangeMatchAppstate(eventbus);
//////        testCard = new Card();
////////        testCard.setLocalScale(0.1f);
//////        Node testNode = new Node();
//////        testNode.setLocalScale(0.1f);
//////        testNode.setLocalTranslation(500, 500, -100);
//////        getRootNode().attachChild(testNode);
//////        testNode.attachChild(testCard);
//////        testCard.setCardName("Wisp");
//////        testCard.setAttack(1);
//////        testCard.setCost(1);
//////        testCard.setHealth(1);
//////        testCard.addMouseListener(new DefaultMouseListener() {
//////            @Override
//////            protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
//////                System.out.println("hurrdurr");
//////            }
//////        });
        fireSyncedEvent(new ScreenRequestEvent(ScreenKeys.LOGIN));
    }

    @Override
    public void simpleUpdate(float tpf) {
//        testCard.setLocalRotation(testCard.getLocalRotation().mult(new Quaternion().fromAngles(tpf, 0, 0)));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void destroy() {
        super.destroy();
        client.close();
    }
}
