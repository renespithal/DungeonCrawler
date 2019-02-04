package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 12.12.2015.
 */
public class UserLeft implements Message {

    private final String type = "user left";
    private String nick;

    public UserLeft(String nick) {
        this.nick = nick;
    }

    /* Getter */

    public String getType() {
        return type;
    }

    public String getNick() {
        return nick;
    }
}
