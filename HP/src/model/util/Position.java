package model.util;

public class Position {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Position translate( int dx, int dy ) {

		return new Position(x + dx, y + dy);
	}

	public boolean equalsPos(Position pos){
		boolean result =false;
		if(this.x==pos.getX()&& this.y==pos.getY()){
			result=true;
		}
		return result;
	}

	public Position translate( Direction direction ) {

		switch (direction) {

		case LEFT:
			return translate(-1, 0);
			
		case RIGHT:
			return translate(+1, 0);
		
		case UP:
			return translate(0, -1);
			
		case DOWN:
			return translate(0, +1);
		}
		
		throw new IllegalArgumentException();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
