package network.messages;

/**
 * Created by sarah on 13.12.2015.
 */
public class Spielerposition {
	
	private int x;
	private int y;

	public Spielerposition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/* getter and setter */
	public int getX() {
		return x;
	}
	
	public void setX( int x ) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY( int y ) {
		this.y = y;
	}
}
