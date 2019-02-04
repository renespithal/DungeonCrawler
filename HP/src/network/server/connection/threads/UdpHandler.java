package network.server.connection.threads;

import java.io.IOException;
import java.net.*;

import network.server.Server;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jenny on 29.11.2015.
 */
public class UdpHandler implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(UdpHandler.class);

    private DatagramPacket packet;
    private DatagramSocket dSocket;

    private ServerSocket sSocket;

    private boolean connected;

    private Server server;

    public UdpHandler(ServerSocket socket, Server server) throws SocketException, UnknownHostException {
        this.dSocket = new DatagramSocket(null);
        dSocket.setReuseAddress(true);
        dSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 30303));
        packet = new DatagramPacket(new byte[1024], 1024);
        this.sSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        while(!isConnected()) {
            try {
                receiveUdp();
            } catch (IOException cause) {
            	logger.error("failed to receive udp", cause);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveUdp() throws IOException, JSONException {    /* listens on port 30303 for incoming udp messages */
        packet = new DatagramPacket(new byte[1024],1024);
        logger.info("waiting for message/packet");
        dSocket.receive(packet);
        logger.info("received packet from {} on port {}",packet.getAddress(),packet.getPort());
        RunServer runServer = new RunServer(sSocket,packet,30304, server);
        Thread t = new Thread(runServer);
        t.start();
    }

    /* Getter & Setter */
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
