package model.furniture;

import model.ServerModel;
import model.entity.Entity;
import model.item.Item;
import model.item.potion.BigPotion;
import model.item.potion.SmallPotion;
import model.item.weapon.Axe;
import model.item.weapon.Spear;
import model.item.weapon.Sword;
import model.util.Field;
import model.util.Position;
import network.messages.SpielzugMessage;

import java.util.Random;

/**
 * Created by Felix on 18.01.2016.
 */
public class WalkingChest extends Chest{

    private SpielzugMessage currentZug;
    private ServerModel model;
    private boolean onDoor = false;

    public WalkingChest(Position position, int i, ServerModel model){
        this.model=model;
        super.setPosition(position);
        super.setClosed(true);
        super.setItem(generateItem());
        super.setNick("chest_"+i);
    }


    public void move(){
        if(playerNear()&&super.isClosed()) {
            Entity.direction dir = randomDir();
            if (model.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.FLOOR) ||
                    model.getCurrentFloor().getFieldAtPos(getNextPos(dir)).getContent().equals(Field.cnt.DOOROPEN)) {
                if (onDoor) {
                    model.getCurrentFloor().getFieldAtPos(super.getPosition()).setContent(Field.cnt.DOOROPEN);
                    onDoor = false;
                } else {

                    model.getCurrentFloor().getFieldAtPos(super.getPosition()).setContent(Field.cnt.FLOOR);
                }

                switch (dir) {
                    case NORTH:
                        super.getPosition().setY(super.getPosition().getY() - 1);
                        break;
                    case WEST:
                        super.getPosition().setX(super.getPosition().getX() - 1);
                        break;
                    case SOUTH:
                        super.getPosition().setY(super.getPosition().getY() + 1);
                        break;
                    case EAST:
                        super.getPosition().setX(super.getPosition().getX() + 1);
                        break;
                }
                if (model.getCurrentFloor().getFieldAtPos(super.getPosition()).getContent().equals(Field.cnt.DOOROPEN)) {
                    onDoor = true;
                }

                if (super.isClosed()) {
                    model.getCurrentFloor().getFieldAtPos(super.getPosition()).setContent(Field.cnt.CHESTCLOSED);
                } else {
                    model.getCurrentFloor().getFieldAtPos(super.getPosition()).setContent(Field.cnt.CHESTOPEN);
                }
                //    }
            } else {
            }
        }
    }

    private Entity.direction randomDir() {
            Entity.direction res = Entity.direction.EAST;
            final Random random = new Random();
            int dir = 1 + random.nextInt(4);
            switch(dir){
                case 1: res= Entity.direction.NORTH;break;
                case 2: res= Entity.direction.EAST;break;
                case 3: res= Entity.direction.SOUTH;break;
                case 4: res= Entity.direction.WEST;break;
            }
        return res;
        }


    public SpielzugMessage think(){
        currentZug = new SpielzugMessage(super.getNick(), "wait", "s");


        return currentZug;
    }

    private boolean playerNear() {
        boolean res = false;
        for(int x=super.getPosition().getX()-2; x<super.getPosition().getX()+3; x++){
            for(int y=super.getPosition().getY()-2; y<super.getPosition().getY()+3; y++){
                if(x>0&&x<model.getCurrentFloor().getXSize()&&y>0&&y<model.getCurrentFloor().getYSize()) {
                    if (model.getCurrentFloor().getFieldAtPos(new Position(x, y)).getContent().toString().startsWith("PLAYER")) {
                        res = true;
                    }
                }
            }
        }
        return res;

    }

    public Position getNextPos(Entity.direction dir){
        Position respos = new Position(0,0);
        respos.setX(super.getPosition().getX());
        respos.setY(super.getPosition().getY());
        switch (dir) {
            case NORTH:
                respos.setY(super.getPosition().getY() - 1);
                break;
            case EAST:
                respos.setX(super.getPosition().getX() + 1);
                break;
            case WEST:
                respos.setX(super.getPosition().getX() - 1);
                break;
            case SOUTH:
                respos.setY(super.getPosition().getY() + 1);
                break;
        }
        return respos;
    }
}
