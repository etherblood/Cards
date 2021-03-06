package com.etherblood.cardsnetworkshared;

import java.io.IOException;
import com.jme3.network.Network;
import com.jme3.network.base.DefaultServer;
import com.jme3.network.kernel.tcp.SelectorKernel;
import com.jme3.network.kernel.udp.UdpKernel;

/**
 *
 * @author Carl
 */
public class ExtendedDefaultServer extends DefaultServer{

    public ExtendedDefaultServer(int port) throws IOException{
        super(Network.DEFAULT_GAME_NAME, Network.DEFAULT_VERSION, new SelectorKernel(port), new UdpKernel(port));
    }

    @Override
    protected void addStandardServices(){
        //Prevent ServerSerializerRegistrationsService
    }
}