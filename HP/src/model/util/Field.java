package model.util;

import model.util.Position;

/**
 * Created by FelixMathy on 23.11.2015.
 */

public class Field {

        private int posX;
        private int posY;
        private AttackInfo attackInfo = new AttackInfo();


    public enum cnt {
            WALL,
            FLOOR,
            CHESTOPEN,
            CHESTCLOSED,
            STAIRUP,
            STAIRDOWN,
            EMPTY,
            DOORCLOSED,
            DOOROPEN,

            MONSTER,
            GOBLIN,
            SKELETT,
            WOLF,
            BOSS,

            DRACO,
            BELLATRIX,
            LUCIUS,
            SNAPE,
            VOLDEMORT,
        DEMENTOR,


            ATTACKME,
            ATTACKRA,
            ATTACKMA,

            PLAYER1,
            PLAYER2,
            PLAYER3,
            PLAYER4,
            PLAYER5,
            PLAYER6,
            PLAYER7,
            PLAYER8
                    }
        private cnt content;


    public boolean blocks(){
        boolean res = false;
        switch(content){
            case WALL: res = true;break;
            case DOORCLOSED: res = true;break;
            case CHESTCLOSED: res = true;break;
            case CHESTOPEN: res = true;break;
        }
        return res;
    }

    public Field(Position position, cnt content) {
        this.content = content;
        this.posX= position.getX();
        this.posY = position.getY();

    }

    public Field(Position position) {
        content = cnt.FLOOR;
        this.posX= position.getX();
        this.posY = position.getY();

    }

    public AttackInfo getAttackInfo() {
        return attackInfo;
    }

    public String toString(){
        String result = "";
        switch(this.getContent()){
            case WALL: result = "w";break;
            case DOORCLOSED: result = "dc"; break;
            case DOOROPEN: result = "do"; break;
            case GOBLIN: result = "m"; break;
            case SKELETT: result = "m"; break;
            case WOLF: result = "m"; break;
            case CHESTCLOSED: result = "c"; break;
            case CHESTOPEN: result = "c"; break;
            case STAIRDOWN: result = "sd"; break;
            case STAIRUP: result = "su"; break;
            case FLOOR: result = "f"; break;
            case ATTACKMA: result = "ama"; break;
            case ATTACKRA: result = "ara"; break;
            case ATTACKME: result = "ame"; break;

            case PLAYER1: result = "p"; break;
            case PLAYER2: result = "p"; break;
            case PLAYER3: result = "p"; break;
            case PLAYER4: result = "p"; break;
            case PLAYER5: result = "p"; break;
            case PLAYER6: result = "p"; break;
            case PLAYER7: result = "p"; break;
            case PLAYER8: result = "p"; break;
        }

        return result;
    }


    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public cnt getContent() {
        return content;
    }

    public void setContent(cnt content) {
        this.content = content;
    }

}
