package network.server;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.game.GameLobby;
import network.messages.Disconnected;
import network.messages.SpielInfo;
import network.server.communication.ServerCommunication;
import network.server.communication.coder.ServerDecoder;
import network.server.communication.coder.ServerEncoder;
import network.server.connection.ServerConnection;
import network.server.connection.threads.ServerHandler;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * Created by Jenny on 29.11.2015.
 */
public class Server {
	
	private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private ServerConnection connect;
    private ServerCommunication com;
    private ServerDecoder decoder;
    private ServerEncoder encoder;


    private SimpleStringProperty control;

    private ObservableList<ServerHandler> registeredClients;
    private ObservableList<GameLobby> gameList;
    private ObservableList<SpielInfo> gameInfoList;
    private boolean harry;

    public Server(boolean harry) {
        this.harry = harry;
        this.connect = new ServerConnection(this);
        this.com = new ServerCommunication(this);
        this.decoder = new ServerDecoder(this, harry);
        this.encoder = new ServerEncoder(this);
        this.gameList = FXCollections.observableArrayList();
        this.registeredClients = FXCollections.observableArrayList();
        this.gameInfoList = FXCollections.observableArrayList();

        this.control = new SimpleStringProperty();
    }

    public void close() throws InterruptedException {
        getCom().sendToAll(new JSONObject(new Disconnected("Server closed")));
        sleep(2000);
        for (int i = 0; i < getCom().getClientList().size(); i++) {
            try {
                getCom().getClientList().get(i).close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /* stops udp handler thread, so clients cant connect anymore */
    public void stopRegistration() {
        connect.getUdpHandler().setConnected(true);
        logger.info("registration closed");
    }

    /*Getter & Setter */
    public ServerConnection getConnect() {
        return connect;
    }

    public void setConnect(ServerConnection connect) {
        this.connect = connect;
    }

    public ServerCommunication getCom() {
        return com;
    }

    public void setCom(ServerCommunication com) {
        this.com = com;
    }

    public ServerDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(ServerDecoder decoder) {
        this.decoder = decoder;
    }

    public ServerEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(ServerEncoder encoder) {
        this.encoder = encoder;
    }

    public ObservableList<ServerHandler> getRegisteredClients() {
        return registeredClients;
    }

    public ObservableList<GameLobby> getGameList() {
        return gameList;
    }

    public void setGameList(ObservableList<GameLobby> gameList) {
        this.gameList = gameList;
    }

    public ObservableList<SpielInfo> getGameInfoList() {
        return gameInfoList;
    }

    public void setGameInfoList(ObservableList<SpielInfo> gameInfoList) {
        this.gameInfoList = gameInfoList;
    }

    public String getControl() {
        return control.get();
    }

    public SimpleStringProperty controlProperty() {
        return control;
    }

    public void setControl(String control) {
        this.control.set(control);
    }


}
