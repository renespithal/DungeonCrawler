package model;

import model.util.Field;
import model.util.Position;
import network.client.Client;
import network.server.Server;

import java.util.ArrayList;

/**
 * Created by Adrian on 15.12.2015.
 */
public class Sight {
    private Field[][] field = new Field[17][17];
    private SightToMapTranslate[][] translation = new SightToMapTranslate[17][17];
    private Field[][] mapField;
    private Floor floor;
    private Position position;
    private int playerPosX;
    private int playerPosY;
    private int mapPosX;
    private int mapPosY;
    private int posX;
    private int posY;
    private boolean singleplayer;
    private ServerModel serverModel;
    private ClientModel clientModel;

    ArrayList<Field> fieldList = new ArrayList<>();
    private ArrayList<Field> fieldArrayList = new ArrayList<>();


    public Sight(Floor floor, Position position, ServerModel serverModel) {
        this.serverModel = serverModel;
        singleplayer = true;
        initializeFields();
        updateSight(floor, position);

    }

    public Sight(Floor floor, Position position, ClientModel clientModel) {
        this.clientModel = clientModel;
        singleplayer = false;
        initializeFields();
        updateSight(floor, position);

    }

    public void updateSight(Floor floor, Position position) {
        fieldArrayList.clear();
        setFieldsEmpty();
        fieldList.clear();
        this.mapField = floor.getFields();
        this.position = position;
        this.playerPosX = position.getX();
        this.playerPosY = position.getY();
        this.floor = floor;
        getCurrentRoom();
    }

    public void initializeFields() {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                field[i][j] = new Field(new Position(i, j), Field.cnt.EMPTY);
            }
        }
    }

    public void setFieldsEmpty() {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (!field[i][j].getContent().equals(Field.cnt.EMPTY)){
                    field[i][j].setContent(Field.cnt.EMPTY);
                        fieldArrayList.add(field[i][j]);

                }
            }}
        }


    public void getCurrentRoom() {
        field[8][8].setContent(mapField[playerPosX][playerPosY].getContent());
        translation[8][8] = new SightToMapTranslate(new Position(8,8), new Position(playerPosX, playerPosY));
        getSurroundingFields(mapField[playerPosX][playerPosY], 8, 8);
    }

    public void getSurroundingFields(Field oneField, int x2, int y2) {
        if (!fieldList.contains(oneField)) {
            fieldList.add(oneField);
            getLeftField(oneField.getPosX(), oneField.getPosY(), x2, y2);
            getLeftUpField(oneField.getPosX(), oneField.getPosY(), x2, y2);
            getUpField(oneField.getPosX(), oneField.getPosY(), x2, y2);
            getRightUpField(oneField.getPosX(), oneField.getPosY(), x2, y2);
            getRightField(oneField.getPosX(), oneField.getPosY(), x2, y2);
            getRightDownField(oneField.getPosX(), oneField.getPosY(), x2, y2);
            getDownField(oneField.getPosX(), oneField.getPosY(), x2, y2);
            getLeftDownField(oneField.getPosX(), oneField.getPosY(), x2, y2);
        }
    }

    public void getLeftField(int x, int y, int x2, int y2) {
        int x3 = x - 1;
        int y3 = y;
        int x4 = x2 - 1;
        int y4 = y2;

        if (checkXandY(x3, y3, x4, y4)) {
            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
            fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
            || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }

    public void getLeftUpField(int x, int y, int x2, int y2) {
        int x3 = x - 1;
        int y3 = y - 1;
        int x4 = x2 - 1;
        int y4 = y2 - 1;

        if (checkXandY(x3, y3, x4, y4)) {

            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
                fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
                    || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }


    public void getUpField(int x, int y, int x2, int y2) {
        int x3 = x;
        int y3 = y - 1;
        int x4 = x2;
        int y4 = y2 - 1;

        if (checkXandY(x3, y3, x4, y4)) {

            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
                fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
                    || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }


    public void getRightUpField(int x, int y, int x2, int y2) {
        int x3 = x + 1;
        int y3 = y - 1;
        int x4 = x2 + 1;
        int y4 = y2 - 1;

        if (checkXandY(x3, y3, x4, y4)) {

            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
                fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
                    || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }


    public void getRightField(int x, int y, int x2, int y2) {
        int x3 = x + 1;
        int y3 = y;
        int x4 = x2 + 1;
        int y4 = y2;

        if (checkXandY(x3, y3, x4, y4)) {

            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
                fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
                    || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }


    public void getRightDownField(int x, int y, int x2, int y2) {
        int x3 = x + 1;
        int y3 = y + 1;
        int x4 = x2 + 1;
        int y4 = y2 + 1;

        if (checkXandY(x3, y3, x4, y4)) {

            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
                fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
                    || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }


    public void getDownField(int x, int y, int x2, int y2) {
        int x3 = x;
        int y3 = y + 1;
        int x4 = x2;
        int y4 = y2 + 1;

        if (checkXandY(x3, y3, x4, y4)) {

            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
                fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
                    || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }

    public void getLeftDownField(int x, int y, int x2, int y2) {
        int x3 = x - 1;
        int y3 = y + 1;
        int x4 = x2 - 1;
        int y4 = y2 + 1;

        if (checkXandY(x3, y3, x4, y4)) {

            field[x4][y4].setContent(mapField[x3][y3].getContent());
            if (!fieldArrayList.contains(field[x4][y4])){
                fieldArrayList.add(field[x4][y4]);}
            if (!(mapField[x3][y3].getContent().equals(Field.cnt.WALL) || mapField[x3][y3].getContent().equals(Field.cnt.DOORCLOSED)
                    || mapField[x3][y3].getContent().equals(Field.cnt.DOOROPEN))
                    && !standingInDoor(mapField[x3][y3])) {
                getSurroundingFields(mapField[x3][y3], x4, y4);
            }
        }

    }

    public boolean checkXandY(int x, int y, int x2, int y2) {
        if (x >= 0 && x <=floor.getXSize() && y >= 0 && y <= floor.getYSize()
                && x2 >= 0 && x2 < 17 && y2 >= 0 && y2 < 17) {
            return true;
        } else {
            return false;
        }

    }

    public boolean standingInDoor(Field oneField) {
        if (isEntity(oneField.getContent())) {
            if (mapField[oneField.getPosX() - 1][oneField.getPosY()].getContent().equals(Field.cnt.WALL)) {
                if (mapField[oneField.getPosX() + 1][oneField.getPosY()].getContent().equals(Field.cnt.WALL)) {
                    return true;
                } else {
                    return false;
                }

            } else if (mapField[oneField.getPosX()][oneField.getPosY() - 1].getContent().equals(Field.cnt.WALL)) {
                if (mapField[oneField.getPosX()][oneField.getPosY() + 1].getContent().equals(Field.cnt.WALL)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }


        }
        else {
            return false;
        }
    }


    public boolean isEntity(Field.cnt content) {
        switch (content) {
            case GOBLIN:
                return true;
            case PLAYER1:
                return true;
            case PLAYER2:
                return true;
            case PLAYER3:
                return true;
            case PLAYER4:
                return true;
            case PLAYER5:
                return true;
            case PLAYER6:
                return true;
            case PLAYER7:
                return true;
            case PLAYER8:
                return true;
            case WOLF:
                return true;
            case SKELETT:
                return true;
            case MONSTER:
                return true;
            default:
                return false;

        }

    }

    public Field[][] getField() {
        return field;
    }

    public void setField(Field[][] field) {
        this.field = field;
    }

    public Field[][] getMapField() {
        return mapField;
    }

    public void setMapField(Field[][] mapField) {
        this.mapField = mapField;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getPlayerPosX() {
        return playerPosX;
    }

    public void setPlayerPosX(int playerPosX) {
        this.playerPosX = playerPosX;
    }

    public int getPlayerPosY() {
        return playerPosY;
    }

    public void setPlayerPosY(int playerPosY) {
        this.playerPosY = playerPosY;
    }

    public int getMapPosX() {
        return mapPosX;
    }

    public void setMapPosX(int mapPosX) {
        this.mapPosX = mapPosX;
    }

    public int getMapPosY() {
        return mapPosY;
    }

    public void setMapPosY(int mapPosY) {
        this.mapPosY = mapPosY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public SightToMapTranslate[][] getTranslation() {
        return translation;
    }

    public ArrayList<Field> getFieldArrayList() {
        return fieldArrayList;
    }

    public void setFieldArrayList(ArrayList<Field> fieldArrayList) {
        this.fieldArrayList = fieldArrayList;
    }
}


