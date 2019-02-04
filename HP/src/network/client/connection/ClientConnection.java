package network.client.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.client.Client;
import network.client.connection.threads.UdpReceive;

import network.client.connection.threads.UdpSend;
import network.game.GameLobby;
import network.server.Server;
import network.server.ServerInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jenny on 23.11.2015.
 */
public class ClientConnection {

	private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    private Socket tcpSocket;
    private UdpReceive receive;
    private UdpSend send;

    private boolean tcpConnected;
    private Client client;

    //private ObservableList<ServerInfo> serverList;

    private int serverPort;
    private int clientPort;

    public ClientConnection(Client client) throws IOException, InterruptedException, JSONException {
        //this.serverList = FXCollections.observableArrayList();
        this.client = client;
        connect();
        //establish();
    }

    public void tcpConnect() throws IOException {           /* opens tcp connection */
        tcpSocket = new Socket(getReceive().getServerIP(), getReceive().getTcpPort());
        setTcpConnected(true);
    }

    public void connect() throws IOException, InterruptedException, JSONException { /* builds up tcp connection to server using udp broadcast */
        udpReceive(30304);
        udpSend(30303);
        /*while(receive.getServerMsg().isEmpty()) {
            System.out.println("warte");
        }*/
        //getServerInfo();
    }

    public void getServerInfo() throws JSONException {
        for (int i = 0; i < receive.getServerMsg().size();  i++) {
            String group = receive.getServerMsg().get(i).getString("group");
            String ip = receive.getServerMsg().get(i).getString("ip");
            int port = receive.getServerMsg().get(i).getInt("tcp_port");
            ServerInfo info = new ServerInfo(group, port, ip);
            logger.info("ServerInfo: " +"ip("+ info.getIp() + ")" + "group("+
                    info.getGroup() + ")" + "status("+ info.getStatus() + ")" + "port("+ info.getPort() + ")");
            //serverList.add(info);
        }

    }

    public void establish() throws IOException, SocketException {
        getSend().setServerSearching(false);
        getSend().getdSocket().close();
        getReceive().setServerSearching(false);
        getReceive().getdSocket().close();
        logger.info("establish() method");
        //while(true) {
            /* waits until packet arrived from server */
            if(getReceive().getTcpPort() != -1) {
                tcpConnect();
               // break;
            }
        //}
        this.client.getCom().startCommunication(tcpSocket);
        logger.info("end establish()");
    }

    public void close() throws IOException {
        tcpSocket.close();
    }

    public void directConnect(int port, InetAddress ip) throws IOException {        /* for direct tcp connection with server */
        setTcpSocket(new Socket(ip, port));
    }

    /* private methods */
    private void udpReceive(int port) throws SocketException, UnknownHostException {              /* starts a threads that is receiving udp messages*/
        receive = new UdpReceive(port);
        receive.start();
    }

    private void udpSend(int port) throws SocketException, UnknownHostException {
        send = new UdpSend(port);
        send.start();
    }



    /* Getter & Setter */
    public Socket getTcpSocket() {
        return tcpSocket;
    }

    public void setTcpSocket(Socket tcpSocket) {
        this.tcpSocket = tcpSocket;
    }

    public UdpReceive getReceive() {
        return receive;
    }

    public void setReceive(UdpReceive receive) {
        this.receive = receive;
    }

    public boolean isTcpConnected() {
        return tcpConnected;
    }

    public void setTcpConnected(boolean tcpConnected) {
        this.tcpConnected = tcpConnected;
    }

    /*public ObservableList<ServerInfo> getServerList() {
        return serverList;
    }

    public void setServerList(ObservableList<ServerInfo> serverList) {
        this.serverList = serverList;
    }*/

    public static Logger getLogger() {
        return logger;
    }

    public UdpSend getSend() {
        return send;
    }

    public void setSend(UdpSend send) {
        this.send = send;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
}
