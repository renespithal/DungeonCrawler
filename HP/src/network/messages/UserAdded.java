package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 12.12.2015.
 */
public class UserAdded implements Message {

    private final String type = "user added";
    private String nick;

    public UserAdded(String nick) {
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
