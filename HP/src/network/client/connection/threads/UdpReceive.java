package network.client.connection.threads;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.server.ServerInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Jenny on 23.11.2015.
 */
public class UdpReceive extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(UdpReceive.class);

    private DatagramSocket dSocket;
    private DatagramPacket packet;

    private int startPort;

    private InetAddress serverIP;
    private int tcpPort = -1;

    private ArrayList<JSONObject> serverMsg;
    private ObservableList<ServerInfo> serverList;

    private boolean serverSearching = true;

    public UdpReceive(int startPort) throws SocketException, UnknownHostException {
        this.serverList = FXCollections.observableArrayList();
        this.startPort = startPort;
        this.dSocket = new DatagramSocket(null);
        dSocket.setReuseAddress(true);
        dSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), startPort));
        packet = new DatagramPacket(new byte[1024], 1024);
        this.serverMsg = new ArrayList<>();
    }

    public void run() {         /* receives packet from server with tcp port, and establishes connection */
        while (isServerSearching()) {
            try {
                /*this.dSocket = new DatagramSocket(null);
                dSocket.setReuseAddress(true);
                dSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), startPort));
                packet = new DatagramPacket(new byte[1024], 1024);*/
                dSocket.receive(packet);
                setServerIP(packet.getAddress());
                String message = new String(packet.getData());
                JSONObject json = new JSONObject(message);
                json.put("ip", packet.getAddress().toString());
                getServerInfo(json);
                //setTcpPort(json.getInt("tcp_port"));
                //dSocket.close();
            } catch (SocketException e) {
                logger.info("Udp Socket closed");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            logger.info("received packet from {} on port {}", packet.getAddress(), packet.getPort());
        }
    }

    private void getServerInfo(JSONObject json) throws JSONException {
        String group = json.getString("group");
        String ip = json.getString("ip");
        int port = json.getInt("tcp_port");
        ServerInfo info = new ServerInfo(group, port, ip);
        logger.info("ServerInfo: " +"ip("+ info.getIp() + ")" + "group("+
                info.getGroup() + ")" + "status("+ info.getStatus() + ")" + "port("+ info.getPort() + ")");
        if(!serverList.isEmpty()) {
            if(!contains(info)) {
                serverList.add(info);
            }
        } else {
            serverList.add(info);
        }
    }

    private boolean contains(ServerInfo info) {
        boolean content = false;
        for (int i = 0; i < serverList.size(); i++) {
            if(info.getPort() == serverList.get(i).getPort() && info.getIp().equals(serverList.get(i).getIp())) {
                content = true;
                break;
            }
        }
        return content;
    }

    /* Getter & Setter*/
    public InetAddress getServerIP() {
        return serverIP;
    }

    public void setServerIP(InetAddress serverIP) {
        this.serverIP = serverIP;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public ArrayList<JSONObject> getServerMsg() {
        return serverMsg;
    }

    public void setServerMsg(ArrayList<JSONObject> serverMsg) {
        this.serverMsg = serverMsg;
    }

    public DatagramSocket getdSocket() {
        return dSocket;
    }

    public void setdSocket(DatagramSocket dSocket) {
        this.dSocket = dSocket;
    }

    public ObservableList<ServerInfo> getServerList() {
        return serverList;
    }

    public void setServerList(ObservableList<ServerInfo> serverList) {
        this.serverList = serverList;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }

    public boolean isServerSearching() {
        return serverSearching;
    }

    public void setServerSearching(boolean serverSearching) {
        this.serverSearching = serverSearching;
    }
}
