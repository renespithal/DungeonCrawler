package network.client.communication.threads;

import javafx.beans.property.SimpleStringProperty;
import network.client.communication.ClientCommunication;
import network.client.communication.coder.ClientDecoder;
import network.server.communication.threads.ServerOutputHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.design.MainPresenter;
import view.designHP.presenter.MainPresenterHP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Jenny on 29.11.2015.
 */
public class InputHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(InputHandler.class);

    private BufferedReader input;
    private Socket socket;
    private MainPresenter mainPresenter;


    private ClientCommunication com;

    private ClientDecoder clientDecoder;

    public InputHandler(Socket socket, ClientCommunication com, MainPresenter mainPresenter, MainPresenterHP mainPresenterHP) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        this.com = com;
        this.mainPresenter = mainPresenter;
        this.clientDecoder = new ClientDecoder(com,"player0",this.mainPresenter,mainPresenterHP); /* TODO: Konstruktor anpassen */
        clientDecoder.setMainPresenter(this.mainPresenter);
    }

    @Override
    public void run() {
        while(!socket.isClosed()) {
            try {
                JSONObject json = receive();
                logger.info("received: " + json.toString());
                clientDecoder.handle(json);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public JSONObject receive() throws IOException, JSONException {
        return new JSONObject(input.readLine());
    }

    public void close() throws IOException, JSONException {
        socket.close();
    }

    /* Getter & Setter */
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getInput() {
        return input;
    }

    public void setInput(BufferedReader input) {
        this.input = input;
    }

    public ClientCommunication getCom() {
        return com;
    }

    public void setCom(ClientCommunication com) {
        this.com = com;
    }

    public ClientDecoder getClientDecoder() {
        return clientDecoder;
    }

    public void setClientDecoder(ClientDecoder clientDecoder) {
        this.clientDecoder = clientDecoder;
    }
}
