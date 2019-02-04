package network.messages;

import network.interfaces.Message;

/**
 * Created by sarah on 13.12.2015.
 */
public class OkMessage implements Message {

	private final String type = "ok";
	
	public OkMessage() {
	}

	/* getter and setter */
	public String getType() {
		return type;
	}
}
