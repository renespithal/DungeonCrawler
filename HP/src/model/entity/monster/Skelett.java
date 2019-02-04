package model.entity.monster;

import model.ServerModel;
import model.util.Field;
import model.util.Position;
import model.entity.Monster;
import network.messages.SpielzugMessage;


public class Skelett extends Monster {

    private Position closestplayer;
    private direction prefdirec, altdirec1,altdirec2;
    private direction decided;
    private boolean cango;


    public Skelett(Position pos, ServerModel serverModel) {
        super.setKlasse("skelett");
        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        super.setHpmax(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setStr(serverModel.getDifficultySettings().getSkelettStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.SKELETT);
        super.setIdent(Field.cnt.SKELETT);
        this.setNick(createMonsterNick());
    }

    public void relax(){

    }
    public Skelett() {

        super.setAlive(true);
        super.setIdent(Field.cnt.SKELETT);
    }

    @Override
    public SpielzugMessage think(){
        closestplayer = findClosestPlayerPos();
        SpielzugMessage res;


        if (attackYes(super.getPos(), closestplayer)) {
            res = new SpielzugMessage(this.getNick(),"wait","w");

        }else{
            findPrefDirec(this.getPos(),closestplayer);
            moveDecision();
            if (cango ) {
                if(walkable(getNextPos(decided))) {
                    res = new SpielzugMessage(this.getNick(), "move", direcConvert(decided));
                }else{
                    res = new SpielzugMessage(this.getNick(), "wait", "w");}
            }else{
                res = new SpielzugMessage(this.getNick(), "wait", "w");
            }
        }

        return res;
    }

    @Override
    public void attack() {

    }
    @Override
    public void clientAttack(){

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



    public void findPrefDirec(Position m, Position p) {

        if(posXdiff(m,p)>= posYdiff(m,p)) {

            if(m.getX() >= p.getX()){prefdirec=direction.WEST;}
            else if(m.getX() < p.getX()){prefdirec=direction.EAST;}


            if(m.getY() >= p.getY()) {altdirec1 = direction.SOUTH;
                                      altdirec2 = direction.NORTH;}
            else{altdirec1 = direction.NORTH;
                 altdirec2 = direction.SOUTH;}
        }else if(posXdiff(m,p)< posYdiff(m,p)){

            if(m.getY() >= p.getY()){prefdirec = direction.NORTH;}
            else if(m.getY() < p.getY()){prefdirec = direction.SOUTH;}

            if(m.getX() >= p.getX()) {altdirec1 = direction.WEST;
                                      altdirec2 = direction.EAST;}
            else{altdirec1 = direction.EAST;
                 altdirec2 = direction.WEST;}
        }

    }

}





