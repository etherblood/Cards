package com.etherblood.cardsjmeclient;

import com.etherblood.cardscontext.CardsContext;
import com.etherblood.cardscontext.CardsContextBuilder;
import com.etherblood.cardsjmeclient.appscreens.ConnectScreen;
import com.etherblood.cardsjmeclient.appscreens.DeckbuilderScreen;
import com.etherblood.cardsjmeclient.appscreens.ErrorScreen;
import com.etherblood.cardsjmeclient.appscreens.LobbyScreen;
import com.etherblood.cardsjmeclient.appscreens.LoginScreen;
import com.etherblood.cardsjmeclient.appscreens.MainMenuScreen;
import com.etherblood.cardsjmeclient.appscreens.MatchScreen;
import com.etherblood.cardsjmeclient.events.EventListener;
import com.etherblood.cardsjmeclient.events.Eventbus;
import com.etherblood.cardsjmeclient.events.EventbusImpl;
import com.etherblood.cardsjmeclient.events.ScreenRequestEvent;
import com.etherblood.cardsjmeclient.events.SynchronizedEvent;
import com.etherblood.cardsjmeclient.match.cards.TemplatesReader;
import com.etherblood.cardsjmeclient.states.BotsState;
import com.etherblood.cardsjmeclient.states.CardCollectionState;
import com.etherblood.cardsjmeclient.states.ConnectionState;
import com.etherblood.cardsjmeclient.states.GuiState;
import com.etherblood.cardsnetworkshared.SerializerInit;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static String ipAddress;
    private CardsContext context;
//    private Card testCard;

    public static void main(String[] args) throws Exception {
        SerializerInit.init();

        try {
            TemplatesReader.INSTANCE.read("");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        
        ipAddress = args.length != 0 ? args[0] : "destrostudios.com";
        final Main app = new Main();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() - 50;
        int height = (int) screenSize.getHeight() - 50;

        AppSettings appSettings = new AppSettings(true);
        appSettings.setFrameRate(200);
        appSettings.setResizable(true);
//        appSettings.setFullscreen(true);
        appSettings.setResolution(width, height);
        appSettings.setAudioRenderer(AppSettings.LWJGL_OPENAL);
        app.setShowSettings(false);
        app.setSettings(appSettings);
        app.start();
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

        Eventbus eventbus = new EventbusImpl();
        
        CardsContextBuilder builder = new CardsContextBuilder();
        builder.addBean(new ScreenManager());
        builder.addBean(new InitScreens());
        builder.addBean(eventbus);
        builder.addBean(new GuiState(guiNode));
        builder.addBean(new CardCollectionState());
        builder.addBean(new ConnectionState());
        builder.addBean(new BotsState());
        
        builder.addBean(new ConnectScreen());
        builder.addBean(new LoginScreen());
        builder.addBean(new MatchScreen());
        builder.addBean(new LobbyScreen());
        builder.addBean(new ErrorScreen());
        builder.addBean(new MainMenuScreen());
        builder.addBean(new DeckbuilderScreen());
        
        context = builder.build();
        
        eventbus.subscribe(SynchronizedEvent.class, new EventListener<SynchronizedEvent>() {
            @Override
            public void onEvent(SynchronizedEvent event) {
                enqueue(event);
            }
        });
        eventbus.sendEvent(new ScreenRequestEvent(ScreenKeys.CONNECT));
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
        context.destroy();
        super.destroy();
    }
}
