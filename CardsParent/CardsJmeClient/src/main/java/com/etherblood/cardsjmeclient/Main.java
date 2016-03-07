package com.etherblood.cardsjmeclient;

import com.etherblood.cardsnetworkshared.SerializerInit;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private static final int PORT = 6145;

    public static void main(String[] args) throws Exception {
        SerializerInit.init();
//        String ipAddress = args.length != 0 ? args[0] : "localhost";
//        Client client = Network.connectToServer(ipAddress, PORT);

        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Initialize the globals access so that the default
        // components can find what they need.
        GuiGlobals.initialize(this);


//        Box b = new Box(1, 1, 1);
//        Geometry geom = new Geometry("Box", b);
//
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.Blue);
//        geom.setMaterial(mat);
//
//        rootNode.attachChild(geom);


        // Create a simple container for our elements
        Container myWindow = new Container();
        guiNode.attachChild(myWindow);

// Put it somewhere that we will see it.
// Note: Lemur GUI elements grow down from the upper left corner.
        myWindow.setLocalTranslation(300, 300, 0);

// Add some elements
        myWindow.addChild(new Label("Hello, World."));
        Button clickMe = myWindow.addChild(new Button("Click Me"));
        clickMe.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                System.out.println("The world is yours.");
            }
        });
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
