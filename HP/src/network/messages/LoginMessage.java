package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 01.12.2015.
 */
public class LoginMessage implements Message {

    private final String type = "login";
    private String nick;
    private final String group = "Gebildete Gnome";
    private String version = "1.4";
    private String[] expansions;

    public LoginMessage(String nick, String[] expansions) {
        this.nick = nick;
        this.expansions = expansions;
    }

    /* Getter */
    public String getType() {
        return type;
    }

    public String getNick() {
        return nick;
    }

    public String getGroup() {
        return group;
    }

    public String getVersion() {
        return version;
    }

    public String[] getExpansions() {
        return expansions;
    }
}
