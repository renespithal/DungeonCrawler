package network.client.communication.threads;

import model.entity.Player;
import model.entity.player.*;
import network.client.Client;
import network.client.communication.coder.ClientEncoder;
import network.messages.SpielerInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

/**
 * Created by Jenny on 29.11.2015.
 */
public class OutputHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(OutputHandler.class);

    private Socket socket;

    private BufferedReader input;
    private OutputStreamWriter output;

    private ClientEncoder encoder;
    private Client client;

    private boolean connected = true;
    private boolean check = true; // boolean ob nachricht gerade auf bestätigung wartet true = warten, false = fertig
    private int error; // error = 0 nachricht noch nicht validiert, error = 1 nachricht korrekt, error -1 nachrich fehlerhaft

    public OutputHandler(Socket socket, Client client) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(System.in));
        this.output = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
        this.encoder = new ClientEncoder();
        this.client = client;
        this.error = 0;
    }

    @Override
    public void run() {
        while(isConnected()) {
            try {
                sendJSON(encoder.chatMessage(readMessage(), client.getNick()));
                setCheck(false);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void prepareClose() throws IOException, JSONException {
        setConnected(false);
        sendJSON(encoder.disconnect());
    }

    public void close() throws IOException {
        socket.close();
    }

    public void sendJSON(String msg) throws IOException, JSONException {
        output.write(msg + '\n');
        output.flush();
        //wait for server answer if message was correct or not
      /*  for (int i = 0; i < 30; i++) {
            logger.info("hängt in sendJSON wartet auf bestätigung");
            try {
                sleep(2000);
            } catch (InterruptedException e) {

            }
        }*/
        /*
        while(isCheck()) {

            logger.info("hängt in sendJSON wartet auf bestätigung");
            try {
                sleep(1000);
            } catch (InterruptedException e) {

            }
        }*/
        if(error == 1) {
            logger.info("message is valid");
        } else if(error == -1) {
            logger.info("message is not valid");
        }
    }

    public String readMessage() throws IOException {
        return input.readLine().trim();
    }

    /* messages */
    public void login() throws IOException, JSONException {
        sendJSON(encoder.loginMessage(client.getNick(), client.getExpansions()));
        logger.info("login beendet");
    }

    public void disconnect() throws IOException, JSONException {
        sendJSON(encoder.disconnect());

    }

    public void chat(String msg) throws IOException, JSONException {
        sendJSON(encoder.chatMessage(msg, client.getNick()));
    }

    public void createGame(String charClass, int depth, int difficulty) throws IOException, JSONException {
        client.getPlayerList().add(new SpielerInfo(client.getNick(), charClass,0,0)); /*TODO: Wo stehen wir am anfang*/
        sendJSON(encoder.createGame(charClass, depth, difficulty));
        logger.info("create game msg  send");
    }

    public void joinGame(int gameID, String charClass) throws IOException, JSONException {
        sendJSON(encoder.joinGame(gameID, charClass));
        client.getPlayerList().add(new SpielerInfo(client.getNick(), charClass,0,0)); /*TODO: Wo stehen wir am anfang*/
    }

    public void leaveGame() throws IOException, JSONException {
        sendJSON(encoder.leaveGame());
    }

    public void startGame(int gameID) throws IOException, JSONException {
        sendJSON(encoder.startGame(gameID));
    }

    /* Getter & Setter */
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public ClientEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(ClientEncoder encoder) {
        this.encoder = encoder;
    }
}
