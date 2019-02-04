package network.messages;

import network.interfaces.Message;

/**
 * Created by sarah on 13.12.2015.
 */
public class ErrorMessage implements Message {

	private final String type = "error";
	private String message;

	public ErrorMessage(String msg) {
		this.message = msg;
	}

	/* getter and setter */
	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage( String message ) {
		this.message = message;
	}
}
