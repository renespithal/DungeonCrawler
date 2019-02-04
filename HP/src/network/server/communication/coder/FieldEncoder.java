package network.server.communication.coder;

import model.Floor;
import model.util.Field;
import network.messages.Spielfeld;

/**
 * Created by Adrian on 08.12.2015.
 */
public class FieldEncoder {
    Spielfeld[] spielfelder;

    public FieldEncoder(Floor floor){
        fieldEncoder(floor);

    }
    public void fieldEncoder(Floor floor){
    spielfelder = new Spielfeld[floor.getXSize()*floor.getYSize()];
    int position = 0;
    Field[][] field = floor.getFields();
    for(int i =0;i < floor.getXSize();i++ ){
        for(int j = 0; j < floor.getYSize();j++){
            String valueString = getStringValue(field[i][j].getContent());
            spielfelder[position] = new Spielfeld(i,j,floor.getLevel(),valueString);
            position++;
        }
    }

}
    public String getStringValue(Field.cnt content) {
        switch (content) {
            case CHESTCLOSED: {
                return "c";
            }
            case FLOOR: {
                return "f";
            }
            case STAIRUP: {
                return "su";
            }
            case STAIRDOWN: {
                return "sd";
            }
            case WALL:{
                return "w";
            }

            case DOORCLOSED: {
                return "dc";
            }
            case DOOROPEN: {
                return "do";
            }
            case MONSTER: {
                return "m";
            }
            case PLAYER1: {
                return "p";
            }
            case PLAYER2: {
                return "p";
            }
            case PLAYER3: {
                return "p";
            }
            case PLAYER4: {
                return "p";
            }
            case PLAYER5: {
                return "p";
            }
            case PLAYER6: {
                return "p";
            }
            case PLAYER7: {
                return "p";
            }
            case PLAYER8: {
                return "p";
            }
            default:
                return "z";

        }


    }

    public Spielfeld[] getSpielfelder() {
        return spielfelder;
    }
}
