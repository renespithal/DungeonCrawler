package network.messages;


/**
 * Created by Adrian on 08.12.2015.
 */
public class Spielfeld {
    private int x;
    private int y;
    private int z;
    private String value;

    public Spielfeld(int x, int y, int z,String value){
        this.x = x;
        this.y = y;
        this.z = z;
        this.value = value;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getValue() {
        return value;
    }
}