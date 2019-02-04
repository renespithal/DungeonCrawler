package model.entity.monster;

import model.entity.Monster;
import model.util.Position;
import network.messages.SpielzugMessage;

/**
 * Created by Felix on 11.02.2016.
 */
public class EnemyHP extends Monster {


    private Position closestplayer;
    private direction prefdirec, altdirec1,altdirec2;
    private direction decided;
    private boolean cango;



    @Override
    public SpielzugMessage think(){
        closestplayer = findClosestPlayerPos();
        SpielzugMessage res;


        if (attackYes(super.getPos(), closestplayer)) {
            direction direc = attackDirec(this.getPos(), closestplayer);
            res = new SpielzugMessage(this.getNick(), "attackMe", direcConvert(direc));

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
        int health = (int) getServerModel().getPlayers().get(getcPlayerIndex()).getHpcur();
        health = health - super.getStr();
        getServerModel().getCurrentFloor().getFieldAtPos(getServerModel().getPlayers().
                get(getcPlayerIndex()).getPos()).getAttackInfo().addAttackInfo(getNick(), "attackMa", getStr());
        getServerModel().getPlayers().get(getcPlayerIndex()).setHpcur(health);

    }

    @Override
    public void clientAttack(){
        getClientModel().getCurrentFloor().getFieldAtPos(getNextPos(getEntityDir()))
                .getAttackInfo().addAttackInfo(getNick(), "attackMa", getStr());
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
