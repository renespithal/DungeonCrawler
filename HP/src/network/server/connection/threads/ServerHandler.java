package network.server.connection.threads;

import network.server.Server;
import network.server.communication.threads.ServerInputHandler;
import network.server.communication.threads.ServerOutputHandler;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;


/**
 * Created by Jenny on 25.11.2015.
 */
public class ServerHandler implements Runnable {
    /* a ServerHandler handles a connection to one client */

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    private Socket socket;

    private ServerInputHandler inputHandler;
    private ServerOutputHandler outputHandler;

    private Server server;

    /* Client Information */
    private String nick = "unset";
    private String group;
    private String version;
    private String[] expansion;

    private int handlerID;

    public ServerHandler(Socket socket,Server server) throws IOException {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            handleInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleInput() throws IOException {
        inputHandler = new ServerInputHandler(socket,server,this);
        Thread t = new Thread(inputHandler);
        t.start();
    }

    public void handleOutput(JSONObject json) {
        outputHandler = new ServerOutputHandler(socket,json);
        Thread t = new Thread(outputHandler);
        t.start();
    }

    public void close() throws IOException {
        inputHandler.close();
        outputHandler.close();
    }

    /* Getter & Setter */
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public String getRemoteAddress() {
    	return socket.getRemoteSocketAddress().toString();
    }
    
    public int getRemotePort() {
    	return socket.getPort();
    }

    public ServerInputHandler getInputHandler() {
        return inputHandler;
    }

    public void setInputHandler(ServerInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public ServerOutputHandler getOutputHandler() {
        return outputHandler;
    }

    public void setOutputHandler(ServerOutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getExpansion() {
        return expansion;
    }

    public void setExpansion(String[] expansion) {
        this.expansion = expansion;
    }

    public int getHandlerID() {
        return handlerID;
    }

    public void setHandlerID(int handlerID) {
        this.handlerID = handlerID;
    }
    
    public void loginFinished() {
    	server.getRegisteredClients().add(this);
    }
}
