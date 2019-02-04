package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 05.12.2015.
 */
public class LoginSuccessful implements Message {

    private final String type = "login successful";
    private String[] expansions;
    private String[] nick_array;
    private SpielInfo[] game_array;

    public LoginSuccessful(String[] expansions, String[] nick_array, SpielInfo[] game_array) {
        this.expansions = expansions;
        this.nick_array = nick_array;
        this.game_array = game_array;
    }

    /* Getter */
    public String getType() {
        return type;
    }

    public String[] getExpansions() {
        return expansions;
    }

    public String[] getNick_array() {
        return nick_array;
    }

    public SpielInfo[] getGame_array() {
        return game_array;
    }
    
    @Override
	public String toString() {
		return "LoginSuccessfulPacket [expansions=" + expansions + ", nick_array=" + nick_array + ", game_array=" + game_array + "]";
	}
}
