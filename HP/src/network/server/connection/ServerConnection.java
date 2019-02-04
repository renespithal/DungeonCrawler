package network.server.connection;

import java.io.IOException;
import java.net.ServerSocket;

import network.server.Server;
import network.server.connection.threads.UdpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jenny on 23.11.2015.
 */
public class ServerConnection {
	
	private static final Logger logger = LoggerFactory.getLogger(ServerConnection.class);

    private ServerSocket sSocket;
    private int tcpPort;

    private Server server;

    private UdpHandler udpHandler;

    public ServerConnection(Server server) {
        this.tcpPort = 4526;
        this.server = server;
    }

    /* connection methods */
    public void establish() throws IOException {    /* establish tcpPort */
        try {
            sSocket = new ServerSocket(getTcpPort());
            logger.info("ServerConnection has been established on port : {}",getTcpPort());
        } catch (Exception cause) {
        	logger.error("Failed to establish");
        }
    }

    public void close() {       /* close Socket */
        try {
            sSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {  /* opens threads for clients */
        establish();
        udpHandler = new UdpHandler(sSocket,getServer());
        Thread t = new Thread(udpHandler);
        t.start();
    }

    /* Getter & Setter */
    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public UdpHandler getUdpHandler() {
        return udpHandler;
    }

    public void setUdpHandler(UdpHandler udpHandler) {
        this.udpHandler = udpHandler;
    }
}
