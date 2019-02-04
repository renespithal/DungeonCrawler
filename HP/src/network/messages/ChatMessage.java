package network.messages;

import network.interfaces.Message;

import java.time.LocalDateTime;

/**
 * Created by Jenny on 01.12.2015.
 */
public class ChatMessage implements Message {

    private final String type = "chat";
    private String sender;
    private String message;
    private final String timestamp = LocalDateTime.now().toString();

    /* optional */
    private int gameID = -1;     /* for special game */
    private String nick;    /* for special player */

    /* constructors created for clients */
    public ChatMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public ChatMessage(String message, String sender, String nick) {
        this.message = message;
        this.sender = sender;
        this.nick = nick;
    }

    /* constructors created for server */
    public ChatMessage(String message) {
        this.message = message;
    }

    public ChatMessage(String message, int gameID) {
        this.message = message;
        this.gameID = gameID;
    }

    public ChatMessage(String message, String nick, int gameID) {
        this.message = message;
        this.nick = nick;
        this.gameID = gameID;
    }

    /* Getter */
    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }
}
