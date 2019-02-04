package network.server.connection.threads;

import network.server.Server;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

/**
 * Created by Jenny on 29.11.2015.
 */
public class RunServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RunServer.class);

    private DatagramSocket dSocket;
    private DatagramPacket packet;

    private DatagramPacket rcvPacket;
    private int port;

    private ServerSocket sSocket;
    private boolean connected;

    private Server server;

    public RunServer(ServerSocket sSocket, DatagramPacket rcvPacket, int port, Server server) throws SocketException {
        this.sSocket = sSocket;
        this.rcvPacket = rcvPacket;
        this.port =  port;
        this.server = server;
    }

    @Override
    public void run() {
            accept();
    }

    public void accept() {      /* responses to client, accepts Socket, starts new ServerHandler */
        try{
            udpSend(connectionMessage(), rcvPacket.getAddress(),port);
            logger.info("Ready! Waiting for Clients...");
            Socket socket = sSocket.accept();
            logger.info("Socket accepted");
            /* Creates a ServerHandler per ClientConnection*/
            ServerHandler handler = new ServerHandler(socket,server);
            handler.setHandlerID(server.getCom().getClientList().size());
            server.getCom().getClientList().add(handler);
            Thread t = new Thread(handler);
            t.start();
        } catch (IOException e) {
            logger.error("failed to accept client");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void udpSend(String message, InetAddress ip, int port) throws IOException {                 /* broadcasts udp message */
        byte[] dataSend = message.getBytes();
        dSocket = new DatagramSocket();
        packet = new DatagramPacket(dataSend, dataSend.length, ip, port);
        dSocket.send(packet);
        logger.info("send packet to " + packet.getAddress()
                + " on port " + packet.getPort());
    }

    private String connectionMessage() throws JSONException {       /* creates message for client with tcp port */
        JSONObject json = new JSONObject();
        json.put("type","udp serverConnection");
        json.put("group","GebildeteGnome");
        json.put("tcp_port", server.getConnect().getTcpPort());
        return json.toString();
    }

    private void close() throws IOException {
        sSocket.close();
    }

    /* Getter & Setter */

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
