package network.server.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.entity.Player;
import network.server.Server;
import network.server.connection.threads.ServerHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jenny on 02.12.2015.
 */
public class ServerCommunication {

    private static final Logger logger = LoggerFactory.getLogger(ServerCommunication.class);

    private ArrayList<ServerHandler> clientList;
    private Server server;

    public ServerCommunication(Server server) {
        this.clientList = new ArrayList<>();
        this.server = server;
    }

    public void sendToAll(JSONObject json) {
        for(int i = 0; i < clientList.size(); i++) {
            clientList.get(i).handleOutput(json);
        }
    }

    public void sendToAllExcept(JSONObject json, String except) {
        for(int i = 0; i < clientList.size(); i++) {
            if(!clientList.get(i).getNick().equals(except)) {
                clientList.get(i).handleOutput(json);
            }
        }

    }

    public void sendToGame(JSONObject json) throws JSONException {
        Object x = json.get("gameID");
        int y = (int) x;

     /*   List<ServerHandler> h = clientList.stream().filter(c -> c.getNick().equals(x)).collect(Collectors.toList());
        for (int i = 0; i < h.size(); i++) {
            h.get(i).handleOutput(json);
        }*/
        for (ServerHandler serverHandler : clientList){
            for (Player player : server.getGameList().get(y).getPlayerList()){
                if (serverHandler.getNick().equals(player.getNick())){
                    serverHandler.getOutputHandler().send(json);
                }
            }

        }
    }


    public void sendToNick(String nick, JSONObject json) {
        ServerHandler h = clientList.stream().filter(c -> c.getNick().equals(nick)).findFirst().get();
        h.handleOutput(json);
       //List<String> nicknames  = clientList.stream().map(ServerHandler::getNick).collect(Collectors.toList());
    }

    public void sendToClientID(int id, JSONObject json) {
        ServerHandler h = clientList.stream().filter(c -> c.getHandlerID() == id).findFirst().get();
        h.handleOutput(json);
        //List<String> nicknames  = clientList.stream().map(ServerHandler::getNick).collect(Collectors.toList());
    }

    public void closeALL() throws IOException {
        for(int i = 0; i < clientList.size(); i++) {
            clientList.get(i).close();
        }
    }

    public void closeDisconnectedClient() {

    }

    /* Getter & Setter */
    public ArrayList<ServerHandler> getClientList() {
        return clientList;
    }

    public void setClientList(ArrayList<ServerHandler> clientList) {
        this.clientList = clientList;
    }
}
