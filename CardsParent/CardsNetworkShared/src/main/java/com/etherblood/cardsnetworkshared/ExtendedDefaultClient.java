package com.etherblood.cardsnetworkshared;

import com.jme3.network.Network;
import com.jme3.network.base.ConnectorFactory;
import com.jme3.network.base.DefaultClient;
import com.jme3.network.base.TcpConnectorFactory;
import com.jme3.network.kernel.Connector;
import com.jme3.network.kernel.tcp.SocketConnector;
import com.jme3.network.kernel.udp.UdpConnector;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Philipp
 */
public class ExtendedDefaultClient extends DefaultClient {

    public static ExtendedDefaultClient connectToServer(String address, int port) throws UnknownHostException, IOException {
        InetAddress remoteAddress = InetAddress.getByName(address);
        UdpConnector fast = port == -1 ? null : new UdpConnector(remoteAddress, port);
        SocketConnector reliable = new SocketConnector(remoteAddress, port);
        return new ExtendedDefaultClient(Network.DEFAULT_GAME_NAME, Network.DEFAULT_VERSION, reliable, fast, new TcpConnectorFactory(remoteAddress));
    }

    public ExtendedDefaultClient(String gameName, int version, Connector reliable, Connector fast, ConnectorFactory connectorFactory) {
        super(gameName, version, reliable, fast, connectorFactory);
    }

    @Override
    protected void addStandardServices() {

    }

}
