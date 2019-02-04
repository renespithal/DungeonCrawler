package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 12.12.2015.
 */
public class Disconnected implements Message {

    private final String type = "disconnected";
    private String message;

    public Disconnected(String message) {
        this.message = message;
    }

    /* Getter */

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
