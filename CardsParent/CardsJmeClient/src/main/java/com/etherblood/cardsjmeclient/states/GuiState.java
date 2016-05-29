package com.etherblood.cardsjmeclient.states;

import com.jme3.scene.Node;

/**
 *
 * @author Philipp
 */
public class GuiState {
    private final Node guiNode;

    public GuiState(Node guiNode) {
        this.guiNode = guiNode;
    }

    public Node getGuiNode() {
        return guiNode;
    }
}
