package network.client.communication.coder;

import model.util.Field;
import model.util.Position;
import network.messages.Spielfeld;

import java.util.ArrayList;


/**
 * Created by Adrian on 10.12.2015.
 */
public class FieldDecoder {
    Field[][] fields;
    int XSize;
    int YSize;
    Spielfeld[] spielfelder;
    Position position = new Position(0,0);

    public FieldDecoder(Spielfeld[] spielfelder){
        getXandY(spielfelder);
        decodeSpielfeld(spielfelder);
    }
    public void getXandY(Spielfeld[] spielfelder){
        this.spielfelder = spielfelder;
        XSize = 0;
        YSize = 0;
        for(Spielfeld spielfeld1 : spielfelder){
            if(spielfeld1.getX() > XSize ){
                XSize = spielfeld1.getX();
            }
            if(spielfeld1.getY() > YSize ){
                YSize = spielfeld1.getY();
            }

        }
        fields = new Field[XSize+1][YSize+1];

    }
    public void decodeSpielfeld(Spielfeld[] spielfelder){
        for(Spielfeld spielfeld1 : spielfelder){
            int x = spielfeld1.getX();
            int y = spielfeld1.getY();
            position.setX(x);
            position.setY(y);
            fields[x][y] = new Field(position,getCnt(spielfeld1.getValue()));
        }
    }
    public Field.cnt  getCnt (String value){
        switch (value){
            case "c":{
                return Field.cnt.CHESTCLOSED;
            }
            case "f":{
                return Field.cnt.FLOOR;
            }
            case "sd":{
                return Field.cnt.STAIRDOWN;
            }
            case "su":{
                return Field.cnt.STAIRUP;
            }
            case "dc":{
                return Field.cnt.DOORCLOSED;
            }
            case "do":{
                return Field.cnt.DOOROPEN;
            }
            case "m":{
                return Field.cnt.SKELETT;
            }
            case "p":{
                return Field.cnt.PLAYER1;
            }
            case "w":{
                return Field.cnt.WALL;
            }
            default:{
                return Field.cnt.EMPTY;
            }

        }



    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public int getXSize() {
        return XSize;
    }

    public void setXSize(int XSize) {
        this.XSize = XSize;
    }

    public int getYSize() {
        return YSize;
    }

    public void setYSize(int YSize) {
        this.YSize = YSize;
    }

    public Spielfeld[] getSpielfelder() {
        return spielfelder;
    }

    public void setSpielfelder(Spielfeld[] spielfelder) {
        this.spielfelder = spielfelder;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
