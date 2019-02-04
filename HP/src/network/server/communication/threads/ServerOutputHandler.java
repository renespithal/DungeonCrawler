package network.server.communication.threads;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jenny on 29.11.2015.
 */
public class ServerOutputHandler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ServerOutputHandler.class);
	
    private Socket socket;
    private JSONObject json;

    public ServerOutputHandler(Socket socket, JSONObject json) {
        this.socket = socket;
        this.json = json;
    }

    @Override
    public void run() {
        send(json);
    }

    public void send(JSONObject json) {
        try {
            OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            output.write(json.toString() + '\n');
            output.flush();
            logger.info("send output: {} to client",json);
        } catch (Exception cause) {
        	logger.error("unexpected IO Exception while sending...stopping processing", cause);
        }
    }

    public void close() throws IOException {
        socket.close();
    }
}
