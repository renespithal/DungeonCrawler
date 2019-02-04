package model.furniture;

import model.util.Position;

/**
 * Created by Felix on 02.12.2015.
 */
public class Door {

    private boolean closed;
    private Position position;

    public Door(Position position){
        this.position = position;
        this.closed = true;
    }

    public Door (Position position, boolean closed){
        this.position = position;
        this.closed = closed;
    }

    public Position getPosition() {
        return position;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean getClosed() {
        return closed;
    }
}
