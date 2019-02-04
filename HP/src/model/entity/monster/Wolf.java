package model.entity.monster;

import javafx.geometry.Pos;
import model.ServerModel;
import model.util.Direction;
import model.util.Field;
import model.util.Position;

import model.entity.Player;

import model.entity.Monster;
import network.messages.SpielzugMessage;


/**
 * Created by Jenny on 22.11.2015.
 */

public class Wolf extends Monster {

    private Position closestplayer;
    private int cPlayerIndex;
    private ServerModel serverModel;
    private Position currentStairs;
    private SpielzugMessage currentZug;
    private Position victimPlayer;
    private direction prefdirec, altdirec1, altdirec2, decided;

    private boolean doorisclose;
    private boolean hasCheckedDoors = false;
    private Position closeDoor;
    private boolean hasChosenRevolver = false;
    direction reWolf1, reWolf2;
    private boolean canRevolve = true;
    private boolean revolver = true;

    public Wolf(Position pos, ServerModel serverModel) {
        super.setKlasse("wolf");
        super.setAlive(true);
        super.setPos(pos);
        super.setServerModel(serverModel);
        super.setHpmax(serverModel.getDifficultySettings().getWolfHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getWolfHPmax());
        super.setStr(serverModel.getDifficultySettings().getWolfStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.WOLF);
        this.setIdent(Field.cnt.WOLF);
        this.setNick(createMonsterNick());

        isDoorClose();
        hasCheckedDoors = false;
  //      chooseRevolver();
        hasChosenRevolver = false;

    }

    public Wolf() {
        super.setAlive(true);
        super.setIdent(Field.cnt.WOLF);
    }


    @Override
    public SpielzugMessage think() {

        if (!hasCheckedDoors) {
           isDoorClose();
        }
        if (!hasChosenRevolver) {
          //  chooseRevolver();
        }

        closestplayer = findClosestPlayerPos2(this.getPos());

        if (attackYes(this.getPos(), closestplayer)) {
            direction direc = attackDirec(this.getPos(), closestplayer);
            currentZug = new SpielzugMessage(this.getNick(), "attackMe", direcConvert(direc));

        } else {


            if (doorisclose && (posDiff(this.getPos(), closeDoor) > 1)) {
                goToDoor();
            } else if (posDiff(this.getPos(), closeDoor) == 1) {
                currentZug = new SpielzugMessage(this.getNick(), "wait", "s");
            } else {
                if (canRevolve) {
                   // revolve();
                    currentZug = new SpielzugMessage(this.getNick(), "wait", "s");
                } else {
                    currentZug = new SpielzugMessage(this.getNick(), "wait", "s");
                }
            }
        }

        return currentZug;

    }


    public void isDoorClose() {
        Position wolf = this.getPos();
        hasCheckedDoors = true;
        int x = this.getServerModel().getCurrentFloor().getXSize();
        int y = this.getServerModel().getCurrentFloor().getYSize();
        doorisclose = false;

        mainLoop:
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Position help = new Position(i, j);
                if ((this.getServerModel().getCurrentFloor().getFields()[i][j].getContent().equals(Field.cnt.DOORCLOSED) ||
                        this.getServerModel().getCurrentFloor().getFields()[i][j].getContent().equals(Field.cnt.DOOROPEN)) &&
                        posDiff(wolf, help) < 8) {

                    closeDoor = new Position(i, j);
                    doorisclose = true;


                    break mainLoop;
                }
            }
        }

        if (!doorisclose) {
            closeDoor = this.getPos();
        }
    }


    public void revolve() {

        if (revolver) {
            revolver = false;
            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(reWolf1));
        } else {
            revolver = true;

            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(reWolf2));
        }


    }

    public void chooseRevolver() {
        hasChosenRevolver = true;

        if (walkable(getNextPos(direction.WEST))) {

            reWolf1 = direction.WEST;


        } else if (walkable(getNextPos(direction.EAST))) {

            reWolf1 = direction.EAST;

        } else if (walkable(getNextPos(direction.NORTH))) {

            reWolf1 = direction.NORTH;
        } else if (walkable(getNextPos(direction.SOUTH))) {

            reWolf1 = direction.SOUTH;
        } else {
            canRevolve = false;
        }

        reWolf2 = reverse(reWolf1);

    }


    @Override
    public void attack() {
        int health = (int) getServerModel().getPlayers().get(getcPlayerIndex()).getHpcur();
        health = health - super.getStr();
        getServerModel().getCurrentFloor().getFieldAtPos(getServerModel().getPlayers().
                get(getcPlayerIndex()).getPos()).getAttackInfo().addAttackInfo(getNick(), "attackWolf", getStr());
        getServerModel().getPlayers().get(getcPlayerIndex()).setHpcur(health);
    }
    public void clientAttack(){
        getClientModel().getCurrentFloor().getFieldAtPos(getNextPos(getEntityDir()))
                .getAttackInfo().addAttackInfo(getNick(), "attackWolf", getStr());
    }


    public boolean aroundStairs(Position wolf) {
        boolean res = false;
        if (posDiff(wolf, currentStairs) <= 1) {
            res = true;

        }

        return res;

    }


    public direction reverse(direction dir) {
        direction res = direction.NORTH;
        switch (dir) {

            case NORTH:
                res = direction.SOUTH;
                break;
            case EAST:
                res = direction.WEST;
                break;
            case WEST:
                res = direction.EAST;
                break;
            case SOUTH:
                res = direction.NORTH;
                break;
        }
        return res;
    }


    public Position findStairs() {
        int x = this.getServerModel().getCurrentFloor().getXSize();
        int y = this.getServerModel().getCurrentFloor().getYSize();
        Position res = new Position(3, 3);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (this.getServerModel().getCurrentFloor().getFields()[i][j].getContent().equals(Field.cnt.STAIRDOWN) ||
                        this.getServerModel().getCurrentFloor().getFields()[i][j].getContent().equals(Field.cnt.BOSS)) {
                    res = new Position(i, j);

                }
            }
        }
        return res;
    }


    public void watchstairs() {
        if (isPlayerCloseToStairs()) {

            if (attackYes(this.getPos(), victimPlayer)) {
                currentZug = new SpielzugMessage(this.getNick(), "attackMe", direcConvert(attackDirec(this.getPos(), victimPlayer)));

            } else {
                findAttractDirec(this.getPos(), victimPlayer);
                if (walkable(getNextPos(prefdirec))) {
                    currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(prefdirec));
                } else if (walkable(getNextPos(altdirec1))) {
                    currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(altdirec1));

                } else if (walkable(getNextPos(altdirec2))) {
                    currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(altdirec2));
                } else {
                    currentZug = new SpielzugMessage(this.getNick(), "wait", "s");
                }
            }
        } else {
            goToStairs();

            //currentZug = new Spielzug(this.getNick(),"wait","s");
        }
    }


    public void goToDoor() {

        findAttractDirec(this.getPos(), closeDoor);
        //quaderAttract(quadrant(this.getPos(),closeDoor));
        if (walkable(getNextPos(prefdirec))) {
            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(prefdirec));
        } else if (walkable(getNextPos(altdirec1))) {
            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(altdirec1));

        } else if (walkable(getNextPos(altdirec2))) {
            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(altdirec2));
        } else {
            currentZug = new SpielzugMessage(this.getNick(), "wait", "s");
        }


    }

    public void goToStairs() {

        findAttractDirec(this.getPos(), currentStairs);
        if (walkable(getNextPos(prefdirec))) {
            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(prefdirec));
        } else if (walkable(getNextPos(altdirec1))) {
            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(altdirec1));

        } else if (walkable(getNextPos(altdirec2))) {
            currentZug = new SpielzugMessage(this.getNick(), "move", direcConvert(altdirec2));
        } else {
            currentZug = new SpielzugMessage(this.getNick(), "wait", "s");
        }


    }


    public boolean isPlayerCloseToStairs() {
        boolean res = false;
        for (Player p : this.getServerModel().getPlayers()) {
            if (posDiff(currentStairs, p.getPos()) <= 5) {
                res = true;
                victimPlayer = p.getPos();
            }

        }
        return res;
    }


    public void findAttractDirec(Position m, Position p) {
        double rand = Math.random();
        if (m.getX() == p.getX()) {
            if (m.getY() > p.getY()) {
                prefdirec = direction.NORTH;
            } else {
                prefdirec = direction.SOUTH;
            }
            if (rand > 0.5) {
                altdirec1 = direction.WEST;
                altdirec2 = direction.EAST;
            } else {
                altdirec2 = direction.WEST;
                altdirec1 = direction.EAST;
            }

        } else if (m.getY() == p.getY()) {
            if (m.getX() > p.getX()) {
                prefdirec = direction.WEST;
            } else {
                prefdirec = direction.EAST;
            }
            if (rand > 0.5) {
                altdirec1 = direction.SOUTH;
                altdirec2 = direction.NORTH;
            } else {
                altdirec2 = direction.SOUTH;
                altdirec1 = direction.NORTH;
            }

        } else {
            quaderAttract(quadrant(this.getPos(), p));
        }
    }

    public void quaderAttract(String quader) {
        switch (quader) {
            case "ol":
                prefdirec = direction.WEST;
                altdirec2 = direction.SOUTH;
                altdirec1 = direction.NORTH;
                break;
            case "or":
                prefdirec = direction.EAST;
                altdirec2 = direction.SOUTH;
                altdirec1 = direction.NORTH;
                break;
            case "ur":
                prefdirec = direction.EAST;
                altdirec2 = direction.NORTH;
                altdirec1 = direction.SOUTH;
                break;
            case "ul":
                prefdirec = direction.WEST;
                altdirec2 = direction.NORTH;
                altdirec1 = direction.SOUTH;
                break;
        }
    }
}
