package network.client.communication;

import network.client.Client;
import network.client.communication.threads.InputHandler;
import network.client.communication.threads.OutputHandler;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.design.MainPresenter;
import view.designHP.presenter.MainPresenterHP;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Jenny on 29.11.2015.
 */
public class ClientCommunication {

    private static final Logger logger = LoggerFactory.getLogger(ClientCommunication.class);

    private OutputHandler send;
    private InputHandler rcv;

    private Socket socket;
    private ClientCommunication com;

    private Client client;
    private MainPresenter mainPresenter;
    private MainPresenterHP mainPresenterHP;

    public ClientCommunication(Client client, MainPresenter mainPresenter, MainPresenterHP mainPresenterHP) throws IOException {
        this.mainPresenter = mainPresenter;
        this.mainPresenterHP = mainPresenterHP;
        this.com = this;
        this.client = client;
    }

    public ClientCommunication(Client client, MainPresenter mainPresenter) throws IOException {
        this.mainPresenter = mainPresenter;
        this.com = this;
        this.client = client;
    }

    public void startCommunication(Socket socket) throws IOException {
        this.socket = socket;
        send();
        rcv();
    }

    public void close() throws IOException, JSONException {
        send.prepareClose();
    }

    /* create Threads */
    public void send() throws IOException {
        send = new OutputHandler(socket,client);
        Thread t = new Thread(send);
        t.start();
    }

    public void rcv() throws IOException {
        rcv = new InputHandler(socket, com,this.mainPresenter, mainPresenterHP);
        Thread t = new Thread(rcv);
        t.start();
    }

    /* Getter & Setter */

    public InputHandler getRcv() {
        return rcv;
    }

    public void setRcv(InputHandler rcv) {
        this.rcv = rcv;
    }

    public OutputHandler getSend() {
        return send;
    }

    public void setSend(OutputHandler send) {
        this.send = send;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
