package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 12.12.2015.
 */
public class Disconnect implements Message {

    private final String type = "disconnect";

    public Disconnect(){}

    /* Getter */
    public String getType() {
        return type;
    }
}
