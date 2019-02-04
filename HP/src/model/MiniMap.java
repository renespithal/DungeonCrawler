package model;


import model.entity.Player;
import model.util.Field;
import model.util.Position;

import java.util.ArrayList;

/**
 * Created by Adrian on 01.12.2015.
 */
public class MiniMap {
    private Sight sight;
    private Field[][] field;
    private Field[][] sightField;
    private int width;
    private int height;
    private ServerModel serverModel;
    private ClientModel clientModel;
    private boolean serverModelisUsed;
    private ArrayList<Field> fieldArrayList = new ArrayList<>();

    public MiniMap(ServerModel serverModel, int x, int y) {
        this.serverModel = serverModel;
        this.field = new Field[x][y];
        this.width = x;
        this.height = y;
        setFields(x, y);
        serverModelisUsed = true;
        fieldArrayList.clear();
    }

    public MiniMap(ClientModel serverModel, int x, int y) {
        this.clientModel = serverModel;
        this.field = new Field[x][y];
        this.width = x;
        this.height = y;
        setFields(x, y);
        serverModelisUsed = false;
        fieldArrayList.clear();
    }


    public void setFields(int x, int y) {
        this.field = new Field[x+1][y+1];
        this.width = x +1;
        this.height = y +1 ;
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {
                field[j][i] = new Field(new Position(j, i), Field.cnt.EMPTY);
            }
        }
    }

    public void updateMiniMap(Sight sight) {
        fieldArrayList.clear();
        this.sight = sight;
        this.sightField = sight.getField();
        int x = sight.getPlayerPosX() - 8;
        int y = sight.getPlayerPosY() - 8;
        for (int j = 0; j < 17; j++) {
            for (int i = 0; i < 17; i++) {
                if (!sightField[j][i].getContent().equals(Field.cnt.EMPTY)) {
                    if (!sightField[j][i].getContent().equals(field[j+x][i+y].getContent())){
                        fieldArrayList.add(field[j+x][i+y]);
                    }
                    field[j + x][i + y].setContent(sightField[j][i].getContent());
                }

            }
        }

    }
    public void floorChangeMiniMap(Sight sight){
        fieldArrayList.clear();
        this.sight = sight;
        this.sightField = sight.getField();
        int x = sight.getPlayerPosX() - 8;
        int y = sight.getPlayerPosY() - 8;
        for (int j = 0; j < 17; j++) {
            for (int i = 0; i < 17; i++) {
                if(j+x >= 0 && i+y >= 0 && j+x < width && i+y < height){
                    fieldArrayList.add(field[j+x][i+y]);
                    field[j + x][i + y].setContent(sightField[j][i].getContent());
                }

                }

            }


    }

    public Sight getSight() {
        return sight;
    }

    public void setSight(Sight sight) {
        this.sight = sight;
    }

    public Field[][] getField() {
        return field;
    }

    public void setField(Field[][] field) {
        this.field = field;
    }

    public Field[][] getSightField() {
        return sightField;
    }

    public void setSightField(Field[][] sightField) {
        this.sightField = sightField;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ServerModel getServerModel() {
        return serverModel;
    }

    public void setServerModel(ServerModel serverModel) {
        this.serverModel = serverModel;
    }

    public ArrayList<Field> getFieldArrayList() {
        return fieldArrayList;
    }

    public void setFieldArrayList(ArrayList<Field> fieldArrayList) {
        this.fieldArrayList = fieldArrayList;
    }
}

