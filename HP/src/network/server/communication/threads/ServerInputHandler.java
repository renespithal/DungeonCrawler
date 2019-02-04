package network.server.communication.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import network.server.Server;
import network.server.connection.threads.ServerHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * Created by Jenny on 29.11.2015.
 */
public class ServerInputHandler implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ServerInputHandler.class);

    private Socket socket;
    private Server server;
    private BufferedReader input;

    private boolean login = false;
    private ServerHandler handler;

    private int gameID;

    public  ServerInputHandler(Socket socket, Server server, ServerHandler handler) throws IOException {
        this.socket = socket;
        this.server = server;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        this.handler = handler;
    }

    @Override
    public void run() {
        while(!socket.isClosed()) {
            try {
                JSONObject json = receive();
                logger.info("input received: {}",json);
                if(!isLogin()) {
                    login(json);
                    json.put("clientID", handler.getHandlerID());
                    server.getDecoder().handle(json);
                } else {
                    json.put("clientID", handler.getHandlerID());
                    if(json.has("gameID")) {
                        gameID = json.getInt("gameID");
                    } else {
                        json.put("gameID",gameID);
                    }
                    server.getDecoder().handle(json);
                }
            } catch (IOException cause) {
            	logger.error("unexpected IO Exception while reading...stopping processing", cause);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject receive() throws IOException, JSONException {
        return new JSONObject(input.readLine());
    }

    public void login(JSONObject json) throws JSONException, InterruptedException {
        if(json.get("type").toString().equals("login")) {
            setLogin(true);
            setInformation(json);
            //server.getCom().getClientList().add(handler);
            String[] expansions = {"a","b","c"}; /*TODO:  */
            String[] nickArr = {"sarah", "felix", "jenny"};
            server.getCom().sendToNick(handler.getNick(), new JSONObject(server.getEncoder().loginSuccessfulMessage(expansions, nickArr, null))); /* TODO aus json arrays auslesen und übergeben */
            logger.info("login successfull geschickt");

        }
    }

    private void setInformation(JSONObject json) throws JSONException {
        handler.setNick(json.get("nick").toString());
        handler.setGroup(json.get("group").toString());
        handler.setVersion(json.get("version").toString());
        JSONArray arrJ = json.getJSONArray("expansions");
        String[] arrS = new String[arrJ.length()];
        for(int i = 0; i < arrJ.length(); i++) {
            arrS[i] = arrJ.get(i).toString();
        }
        handler.setExpansion(arrS);
        handler.setHandlerID(server.getCom().getClientList().size());
        
        handler.loginFinished();
    }

    public void close() throws IOException {
        socket.close();
    }

    /* Getter & Setter */
    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
