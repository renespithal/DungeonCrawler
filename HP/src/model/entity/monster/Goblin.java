package model.entity.monster;

import model.ServerModel;
import model.entity.Monster;
import model.util.Field;
import model.util.Position;
import network.messages.SpielzugMessage;


/**
 * Created by Jenny on 22.11.2015.
 */
public class Goblin extends Monster {

    private Position closestplayer;
    private direction prefdirec, altdirec1,altdirec2;
    private boolean brave;
    private direction decided;
    private boolean cango;
    private boolean oncebrave;

    public Goblin(Position pos, ServerModel serverModel) {
        super.setKlasse("goblin");
        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        super.setHpmax(serverModel.getDifficultySettings().getGoblinHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getGoblinHPmax());
        super.setStr(serverModel.getDifficultySettings().getGoblinStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.GOBLIN);
        super.setIdent(Field.cnt.GOBLIN);
        this.setNick(createMonsterNick());
    }
    public Goblin(){
        super.setAlive(true);
        super.setIdent(Field.cnt.GOBLIN);
    }





    @Override
    public SpielzugMessage think() {
        closestplayer = findClosestPlayerPos2(this.getPos());
        SpielzugMessage res;
        if (outOfSight(this.getPos(),closestplayer)) {
            res = new SpielzugMessage(this.getNick(), "wait", "c");
        } else {


            if (attackYes(this.getPos(), closestplayer)) {
                direction attacDirection = attackDirec(this.getPos(), closestplayer);
                res = new SpielzugMessage(this.getNick(), "attackMe", direcConvert(attacDirection));
            } else {

                brave = isFriendClose();
                if (brave|| oncebrave) {

                    findAttractDirec(this.getPos(), closestplayer);
                    moveDecision();
                    if (cango) {
                        if (walkable(getNextPos(decided))) {
                            res = new SpielzugMessage(this.getNick(), "move", direcConvert(decided));
                        } else {
                            res = new SpielzugMessage(this.getNick(), "wait", "s");
                        }

                    } else {
                        res = new SpielzugMessage(this.getNick(), "wait", "s");

                    }
                } else {
                    findDeterDirec(this.getPos(), closestplayer);
                    moveDecision();
                    if (cango) {
                        if (walkable(getNextPos(decided))) {

                            res = new SpielzugMessage(this.getNick(), "move", direcConvert(decided));
                        } else {
                            res = new SpielzugMessage(this.getNick(), "wait", "s");
                        }
                    } else {
                        res = new SpielzugMessage(this.getNick(), "wait", "s");

                    }
                }

            }



        }
        return res;
    }

    @Override
    public void attack() {
        int health= (int) getServerModel().getPlayers().get(getcPlayerIndex()).getHpcur();
        health= health - super.getStr();
        getServerModel().getCurrentFloor().getFieldAtPos(getServerModel().getPlayers().
                get(getcPlayerIndex()).getPos()).getAttackInfo().addAttackInfo(getNick(), "attackGoblin", getStr());
        getServerModel().getPlayers().get(getcPlayerIndex()).setHpcur(health);
    }
    public void clientAttack(){
        getClientModel().getCurrentFloor().getFieldAtPos(getNextPos(getEntityDir()))
                .getAttackInfo().addAttackInfo(getNick(), "attackGoblin", getStr());
    }




    public boolean isFriendClose() {
        boolean res = false;
        for (Monster m: getServerModel().getMonsters()) {
            if (posDiff(this.getPos(),m.getPos())!= 0 && m.ident == Field.cnt.GOBLIN && (posDiff(m.getPos(), this.getPos()) < 5)) {

                res = true;
                oncebrave = true;
            }

        }
        return res;
    }



    public void moveDecision() {
        cango = false;
        if (walkable(getNextPos(prefdirec))) {
            decided =prefdirec;
            cango= true;
        } else if (walkable(getNextPos(altdirec1))) {
            decided = altdirec1;
            cango= true;
        }else if(walkable(getNextPos(altdirec2))) {
            decided = altdirec2;
            cango= true;
        }

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

        }else{quaderAttract(quadrant(this.getPos(),closestplayer));}
    }

    public void findDeterDirec(Position m, Position p){
        double rand = Math.random();
            prefdirec = direction.SOUTH;
            altdirec1 = direction.NORTH;
            altdirec2 = direction.WEST;

        if(m.getY() == p.getY()) {
            if (m.getX() > p.getX()) {
                prefdirec = direction.EAST;
            } else {
                prefdirec = direction.WEST;
            }
            if (rand > 0.5) {
                altdirec1 = direction.SOUTH;
                altdirec2 = direction.NORTH;
            } else {
                altdirec2 = direction.SOUTH;
                altdirec1 = direction.NORTH;
            }
        }
         else if(m.getX() == p.getX()) {
            if(m.getY() > p.getY()){
                prefdirec = direction.SOUTH;
            }else{
                prefdirec = direction.NORTH; }
            if (rand > 0.5) {
                altdirec1 = direction.WEST;
                altdirec2 = direction.EAST;
            }else{
                altdirec2 = direction.WEST;
                altdirec1 = direction.EAST;
         }
        }else{quaderDeter(quadrant(m,p));}




    }

    public void quaderDeter(String quader){
        switch(quader){
            case "ol":  prefdirec = direction.EAST;
                        altdirec1 = direction.SOUTH;
                        altdirec2 = direction.SOUTH;
                break;
            case "or":  prefdirec = direction.WEST;
                        altdirec1 = direction.SOUTH;
                        altdirec2 = direction.SOUTH;
                break;
            case "ur":  prefdirec = direction.WEST;
                        altdirec1 = direction.NORTH;
                        altdirec2 = direction.NORTH;
                break;
            case "ul":  prefdirec = direction.EAST;
                        altdirec1 = direction.NORTH;
                        altdirec2 = direction.NORTH;
                break;
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
