package network.client.connection.threads;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

/**
 * Created by Jenny on 16.01.2016.
 */
public class UdpSend extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(UdpReceive.class);

    private boolean serverSearching;

    private InetAddress ia;

    private DatagramSocket dSocket;
    private DatagramPacket packet;

    private int port;

    public UdpSend(int port) throws SocketException, UnknownHostException {
        this.serverSearching = true;
        this.ia = InetAddress.getByName("255.255.255.255");
        this.dSocket = new DatagramSocket();
        this.port = port;
    }

    public void run() {
        while(isServerSearching()) {
            try {
                udpSend(connectionMessage());
                sleep(500);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* methods */
    private void udpSend(String message) throws IOException {                /* broadcasts udp message */
        byte[] dataSend = message.getBytes();
        dSocket = new DatagramSocket();
        packet = new DatagramPacket(dataSend, dataSend.length, ia, getPort());
        dSocket.send(packet);
        logger.info("send packet to {} on port {}",packet.getAddress(),packet.getPort());
    }

    private String connectionMessage() throws JSONException {               /* sends message with group name */
        JSONObject json = new JSONObject();
        json.put("type","udp client");
        json.put("group","GebildeteGnome");
        return json.toString();
    }

    /* Getter & Setter */

    public static Logger getLogger() {
        return logger;
    }

    public boolean isServerSearching() {
        return serverSearching;
    }

    public void setServerSearching(boolean serverSearching) {
        this.serverSearching = serverSearching;
    }

    public InetAddress getIa() {
        return ia;
    }

    public void setIa(InetAddress ia) {
        this.ia = ia;
    }

    public DatagramSocket getdSocket() {
        return dSocket;
    }

    public void setdSocket(DatagramSocket dSocket) {
        this.dSocket = dSocket;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
