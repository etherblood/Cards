package com.etherblood.cardsnetworkshared.master.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class SetLibraryRequest {
    private String[] library;

    public SetLibraryRequest() {
    }

    public SetLibraryRequest(String[] library) {
        this.library = library;
    }

    public String[] getLibrary() {
        return library;
    }
}
