package network.client;
import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.client.communication.ClientCommunication;
import network.client.connection.ClientConnection;
import network.messages.SpielInfo;
import network.messages.SpielerInfo;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.design.MainPresenter;
import view.designHP.presenter.MainPresenterHP;

/**
 * Created by Jenny on 29.11.2015.
 */
public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private ClientConnection connect;
    private ClientCommunication com;
    private MainPresenter mainPresenter;
    private MainPresenterHP mainPresenterHP;
    private boolean connected;

    private String nick;
    private String[] expansions;

    private SimpleBooleanProperty serverError = new SimpleBooleanProperty();

    private ObservableList<SpielInfo> gameList;
    private ObservableList<SpielerInfo> playerList;

    public Client(MainPresenter mainPresenter) throws IOException, JSONException, InterruptedException {
        this.serverErrorProperty().setValue(false);
        this.mainPresenter = mainPresenter;
        this.com = new ClientCommunication(this, mainPresenter);
        this.connect = new ClientConnection(this);
        this.gameList = FXCollections.observableArrayList();
        this.playerList = FXCollections.observableArrayList();

    }
    
    public Client(MainPresenterHP mainPresenterHP) throws IOException, JSONException, InterruptedException {
        this.serverErrorProperty().setValue(false);
        this.mainPresenter = mainPresenter;
        this.com = new ClientCommunication(this, mainPresenter, mainPresenterHP);
        this.connect = new ClientConnection(this);
        this.gameList = FXCollections.observableArrayList();
        this.playerList = FXCollections.observableArrayList();

    }

    public void close() throws IOException, JSONException {
        com.close();
    }

    /*Getter & Setter */
    public ClientConnection getConnect() {
        return connect;
    }

    public void setConnect(ClientConnection connect) {
        this.connect = connect;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public ClientCommunication getCom() {
        return com;
    }

    public void setCom(ClientCommunication com) {
        this.com = com;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String[] getExpansions() {
        return expansions;
    }

    public void setExpansions(String[] expansions) {
        this.expansions = expansions;
    }


    public ObservableList<SpielInfo> getGameList() {
        return gameList;
    }

    public void setGameList(ObservableList<SpielInfo> gameList) {
        this.gameList = gameList;
    }

    public ObservableList<SpielerInfo> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ObservableList<SpielerInfo> playerList) {
        this.playerList = playerList;
    }

    public boolean getServerError() {
        return serverError.get();
    }

    public BooleanProperty serverErrorProperty() {
        return serverError;
    }

    public void setServerError(boolean serverError) {
        this.serverError.set(serverError);
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }
    
    public void setMainPresenter(MainPresenterHP mainPresenterHP) {
        this.mainPresenterHP = mainPresenterHP;
    }

    /* main method
    public static void main (String[] args) throws IOException, InterruptedException, JSONException {
        Client c = new Client(this.mainPresenter);
        //c.getJoin().connect();
        c.getConnect().establish();
        logger.info("send login");
        String[] s = {"hoolooo", "irgendeinen blödsinn"};
        c.setNick("test");
        c.setExpansions(s);
        c.getCom().getSend().login();
        //sleep(5000);
        //c.getCom().close();
    }*/
}
