package model.entity.monster;

import model.ServerModel;
import model.entity.Monster;
import model.entity.Player;
import model.util.Field;
import model.util.Position;
import network.messages.SpielzugMessage;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Felix on 18.01.2016.
 */
public class Boss extends Monster {

    private Position startPos;
    private Position closestplayer;
    private direction prefdirec;
    private direction altdirec2;
    private direction altdirec1;
    private SpielzugMessage currentZug;
    private int attackCount=0;
    private boolean cango;
    private direction decided;

    public Boss(Position pos, ServerModel serverModel) {

        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        startPos=pos;
        super.setKlasse("boss");
        super.setHpmax(serverModel.getDifficultySettings().getBossHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getBossHPmax());
        super.setStr(serverModel.getDifficultySettings().getBossStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.BOSS);
        super.setIdent(Field.cnt.BOSS);
        this.setNick(createMonsterNick());
    }
    public Boss(){
        super.setAlive(true);
        super.setIdent(Field.cnt.BOSS);
    }

    @Override
    public void attack() {
        final Random rand = new Random();
        int random = 1+ rand.nextInt(3);

        if(random<3) {
            int health = (int) getServerModel().getPlayers().get(getcPlayerIndex()).getHpcur();
            health = health - super.getStr();
            getServerModel().getPlayers().get(getcPlayerIndex()).setHpcur(health);
            getServerModel().getCurrentFloor().getFieldAtPos(getServerModel().getPlayers().
                    get(getcPlayerIndex()).getPos()).getAttackInfo().addAttackInfo(getNick(), "attackFinalBoss", getStr());
            random = 1+ rand.nextInt(3);
        }
        else {
            List<Player> players = new LinkedList<>();
            for (int x = getPos().getX() - 1; x <= getPos().getX() + 1; x++) {
                for (int y = getPos().getY() - 1; y <= getPos().getY() + 1; y++) {
                    for (int i = 0; i < getServerModel().getPlayers().size(); i++) {
                        if (getServerModel().getPlayers().get(i).getPos().
                                equals(getServerModel().getCurrentFloor().getFieldAtPos(new Position(x, y)))) {
                            players.add(getServerModel().getPlayers().get(i));
                            getServerModel().getPlayers().get(i).setHpcur(getServerModel().getPlayers().get(i).getHpcur() - getStr());
                        }
                    }
//                    if(!getPos().equalsPos(new Position(x,y))) {
//                        getServerModel().getCurrentFloor().getFieldAtPos(new Position(x, y)).getAttackInfo().addAttackInfo(getNick(), "attackFinalBoss", getStr());
//                    }
                }
            }
            random= 1+ rand.nextInt(3);;
        }
    }
    @Override
    public void clientAttack(){
        getClientModel().getCurrentFloor().getFieldAtPos(getNextPos(getEntityDir()))
                .getAttackInfo().addAttackInfo(getNick(), "attackFinalBoss", getStr());
    }








    @Override
    public SpielzugMessage think(){
        SpielzugMessage res;
        closestplayer = findClosestPlayerPos();

        if (attackYes(this.getPos(), closestplayer)) {
            res = new SpielzugMessage(this.getNick(), "attackMe", direcConvert(attackDirec(this.getPos(), closestplayer)));
        }else{
            findPrefDirec(this.getPos(),closestplayer);
            moveDecision();
            if (cango&& !outOfSight(this.getPos(),closestplayer)) {
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
