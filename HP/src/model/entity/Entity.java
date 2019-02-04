package model.entity;

import model.ClientModel;
import model.ServerModel;
import model.util.Field;
import model.util.Position;

/**
 * Created by Jenny on 19.11.2015.
 */
public abstract class Entity {

    private String nick;
    private ServerModel serverModel;
    private ClientModel clientModel;
    private int level;
    private double exp;
    private Position pos;
    private double hpcur;
    private double hpmax;
    private int str;
    private int dex;
    private int wis;
    private boolean alive = true;

    public enum direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    private boolean onDoor = false;
    private boolean onClosedDoor;

    public Field.cnt ident;


    public direction entityDir;


    public Position getNextPos(direction dir) {
        Position respos = new Position(0, 0);
        respos.setX(getPos().getX());
        respos.setY(getPos().getY());
        switch (dir) {
            case NORTH:
                respos.setY(getPos().getY() - 1);
                break;
            case EAST:
                respos.setX(getPos().getX() + 1);
                break;
            case WEST:
                respos.setX(getPos().getX() - 1);
                break;
            case SOUTH:
                respos.setY(getPos().getY() + 1);
                break;
        }
        return respos;
    }


    public void move(direction dir) {
        if (alive) {

            if (serverModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.FLOOR) ||
                    serverModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.DOOROPEN)) {
                if (onDoor) {
                    serverModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(Field.cnt.DOOROPEN);
                    onDoor = false;
                } else {

                    serverModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(Field.cnt.FLOOR);
                }

                switch (dir) {
                    case NORTH:
                        getPos().setY(getPos().getY() - 1);
                        break;
                    case WEST:
                        getPos().setX(getPos().getX() - 1);
                        break;
                    case SOUTH:
                        getPos().setY(getPos().getY() + 1);
                        break;
                    case EAST:
                        getPos().setX(getPos().getX() + 1);
                        break;
                }
                if (serverModel.getCurrentFloor().getFieldAtPos(this.pos).getContent().equals(Field.cnt.DOOROPEN)) {
                    onDoor = true;
                }

                serverModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(this.getIdent());
                //    }
            } else {
            }
        }
    }


    public void useClient(direction dir) {
        if (clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.DOORCLOSED)) {

            clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).setContent(Field.cnt.DOOROPEN);

        } else if (clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.DOOROPEN)) {

            clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).setContent(Field.cnt.DOORCLOSED);

        } else if (clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.CHESTCLOSED)) {

            clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).setContent(Field.cnt.CHESTOPEN);

        }

    }

    public void moveClient(direction dir) {



        if (alive) {
            if (clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.FLOOR) ||
                    clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.DOOROPEN)) {
                if (onDoor) {
                    clientModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(Field.cnt.DOOROPEN);
                    onDoor = false;
                } else {

                    clientModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(Field.cnt.FLOOR);
                }

                switch (dir) {
                    case NORTH:
                        getPos().setY(getPos().getY() - 1);
                        break;
                    case WEST:
                        getPos().setX(getPos().getX() - 1);
                        break;
                    case SOUTH:
                        getPos().setY(getPos().getY() + 1);
                        break;
                    case EAST:
                        getPos().setX(getPos().getX() + 1);
                        break;
                }
                if (clientModel.getCurrentFloor().getFieldAtPos(this.pos).getContent().equals(Field.cnt.DOOROPEN)) {
                    onDoor = true;
                }

                clientModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(getIdent());
                //    }
            } else {
            }
        }
    }


    public void moveClientMonster(direction dir) {


        if (alive) {
            if (clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.FLOOR) ||
                    clientModel.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.DOOROPEN)) {
                if (onDoor) {
                    clientModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(Field.cnt.DOOROPEN);
                    onDoor = false;
                } else {

                    clientModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(Field.cnt.FLOOR);
                }

                switch (dir) {
                    case NORTH:
                        getPos().setY(getPos().getY() - 1);
                        break;
                    case WEST:
                        getPos().setX(getPos().getX() - 1);
                        break;
                    case SOUTH:
                        getPos().setY(getPos().getY() + 1);
                        break;
                    case EAST:
                        getPos().setX(getPos().getX() + 1);
                        break;
                }
                if (clientModel.getCurrentFloor().getFieldAtPos(this.pos).getContent().equals(Field.cnt.DOOROPEN)) {
                    onDoor = true;
                }

                //  System.out.println(ident.toString());
                clientModel.getCurrentFloor().getFieldAtPos(this.pos).setContent(getIdent());
                //    }
            } else {
            }
        }
    }





    public Position calcNextField(String dir) {
        int newX = this.pos.getX();
        int newY = this.pos.getY();
        switch (dir) {
            case "w":
                newY -= 1;
                break;
            case "s":
                newY += 1;
                break;
            case "a":
                newX -= 1;
                break;
            case "d":
                newX += 1;
                break;
        }
        return new Position(newX, newY);
    }

    public Position calcNextField(String dir, Position pos) {
        int newX = pos.getX();
        int newY = pos.getY();
        switch (dir) {
            case "w":
                newY -= 1;
                break;
            case "s":
                newY += 1;
                break;
            case "a":
                newX -= 1;
                break;
            case "d":
                newX += 1;
                break;
        }
        return new Position(newX, newY);
    }

    public Position furtherawayField(String dir, int i) {
        int newX = pos.getX();
        int newY = pos.getY();
        switch (dir) {
            case "w":
                newY -= i;
                break;
            case "s":
                newY += i;
                break;
            case "a":
                newX -= i;
                break;
            case "d":
                newX += i;
                break;
        }
        return new Position(newX, newY);
    }

    public Position surroundingField(String dir, int i, int j) {
        int newX = pos.getX();
        int newY = pos.getY();
        switch (dir) {
            case "w":
                newY -= i;
                newX += j;
                break;
            case "s":
                newY += i;
                newX += j;
                break;
            case "a":
                newX -= i;
                newY += j;
                break;
            case "d":
                newX += i;
                newY += j;
                break;
        }
        return new Position(newX, newY);
    }


    public String direcConvert(direction dir) {
        String res = "";
        switch (dir) {
            case NORTH:
                res = "w";
                break;
            case EAST:
                res = "d";
                break;
            case SOUTH:
                res = "s";
                break;
            case WEST:
                res = "a";
                break;
        }
        return res;
    }

    public boolean isWolf() {
        boolean res = false;
        if (this.getIdent().equals(Field.cnt.WOLF)) {
            res = true;
        }
        return res;
    }


    /* Getter & Setter */
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public double getHpcur() {
        return hpcur;
    }

    public void setHpcur(double hpcur) {
        this.hpcur = hpcur;
        if (hpcur <= 0) {
            alive = false;
            if (serverModel != null){
            if (onDoor) {
                serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.DOOROPEN);
            } else {
                serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.FLOOR);
            }}
            pos = new Position(0, 0);
        }
    }

    public double getHpmax() {
        return hpmax;
    }

    public void setHpmax(double hpmax) {
        this.hpmax = hpmax;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getWis() {
        return wis;
    }

    public void setWis(int wis) {
        this.wis = wis;
    }

    public Field.cnt getIdent() {
        return ident;
    }

    public void setIdent(Field.cnt ident) {
        this.ident = ident;
    }

    public ServerModel getServerModel() {
        return serverModel;
    }

    public void setServerModel(ServerModel serverModel) {
        this.serverModel = serverModel;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public boolean isOnClosedDoor() {
        return onClosedDoor;
    }

    public void setOnClosedDoor(boolean onClosedDoor) {
        this.onClosedDoor = onClosedDoor;
    }

    public boolean isOnDoor() {
        return onDoor;
    }

    public void setOnDoor(boolean onDoor) {
        this.onDoor = onDoor;
    }

    public direction getEntityDir() {
        return entityDir;
    }

    public void setEntityDir(direction entityDir) {
        this.entityDir = entityDir;
    }
}
