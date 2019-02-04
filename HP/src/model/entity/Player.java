package model.entity;

import model.ServerModel;
import model.furniture.Chest;
import model.furniture.Door;
import model.util.Field;
import model.Floor;
import model.util.Position;
import model.item.Item;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jenny on 22.11.2015.
 */
public class Player extends Entity {

    private String name;
    private Item weapon;
    private Item potion;
    private Floor floor;
    private boolean standingInDoor;
    private Field.cnt content;
    private String klasse;
    private int number;
    private double nextLevelExp = 5;
    private Entity target;

    public void addInformation(ServerModel serverModel, Floor floor, Position position, int number) {
        switch(number){
            case 1: super.setIdent(Field.cnt.PLAYER1);break;
            case 2: super.setIdent(Field.cnt.PLAYER2);break;
            case 3: super.setIdent(Field.cnt.PLAYER3);break;
            case 4: super.setIdent(Field.cnt.PLAYER4);break;
            case 5: super.setIdent(Field.cnt.PLAYER5);break;
            case 6: super.setIdent(Field.cnt.PLAYER6);break;
            case 7: super.setIdent(Field.cnt.PLAYER7);break;
            case 8: super.setIdent(Field.cnt.PLAYER8);break;
        }

        super.setServerModel(serverModel);
        this.floor=floor;
        super.setPos(position);
    }


    public String use(String dir) {
        Position nextPos = calcNextField(dir);
        String fieldIs = new String(super.getServerModel().getCurrentFloor().getFields()[nextPos.getX()][nextPos.getY()].getContent().toString());
        switch (super.getServerModel().getCurrentFloor().getFields()[nextPos.getX()][nextPos.getY()].getContent()) {
            case CHESTOPEN:
                break;
            case CHESTCLOSED:
                List<Chest> chests = super.getServerModel().getChests();
                chests = super.getServerModel().getChests();
                for (int i = 0; i < chests.size(); i++) {
                    Chest curChest = chests.get(i);
                    if (chests.get(i).getPosition().getX() == nextPos.getX() && chests.get(i).getPosition().getY() == nextPos.getY()) {
                        curChest.setClosed(false);
                        floor.getFieldAtPos(curChest.getPosition()).setContent(Field.cnt.CHESTOPEN);
                    }
                }

                for (int i = 0; i < chests.size(); i++) {
                    Chest curChest = chests.get(i);
                    //.equals didn't work for some stupid reason
                    if (chests.get(i).getPosition().getX() == nextPos.getX() && chests.get(i).getPosition().getY() == nextPos.getY()) {
                        Item item = curChest.take();
                        if (item != null) {
                            switch (item.getType()) {
                                case "potion":
                                    this.setPotion(item);
                                    break;
                                case "weapon":
                                    this.setWeapon(item);
                                    break;
                            }}
                    }
                }
                break;
            case DOORCLOSED:
                List<Door> doors = super.getServerModel().getDoors();

                for (int i = 0; i < doors.size(); i++) {
                    if (doors.get(i).getPosition().getX() == nextPos.getX() && doors.get(i).getPosition().getY() == nextPos.getY()) {
                        doors.get(i).setClosed(false);
                        floor.getFieldAtPos(nextPos).setContent(Field.cnt.DOOROPEN);
                    }
                }
                break;
            case DOOROPEN:
                doors = super.getServerModel().getDoors();
                for (int i = 0; i < doors.size(); i++) {
                    if (doors.get(i).getPosition().getX() == nextPos.getX() && doors.get(i).getPosition().getY() == nextPos.getY()) {
                        doors.get(i).setClosed(true);
                        floor.getFieldAtPos(nextPos).setContent(Field.cnt.DOORCLOSED);
                    }
                }
                break;
            case STAIRDOWN:
                try {
                    super.getServerModel().loadNextLevel();
                    super.getServerModel().signalFloorChange();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                if (potion != null&&dir=="g"){
                    if(potion.getName().equals("Big Potion")){
                        super.setHpcur(getHpmax());
                        potion = null;

                    }
                    else if  (potion.getName().equals("Small Potion")){
                        if (getHpcur() + (getHpmax()*0.5) >= getHpmax()){
                            setHpcur(getHpmax());
                            potion = null;
                        }
                        else{
                            setHpcur(getHpcur()+ (getHpmax()*0.5));
                            potion = null;
                        }
                    }
                }
                break;
        }


        return fieldIs;
    }




    public int attackStr(){

        int weapon=0;
        if(getWeapon()!=null){weapon=getWeapon().getStr();}

        return (weapon+getStr());
    }

    public int attackme(String dir) {
        Position nextField = calcNextField(dir);
        boolean hitable = false;
        int spear = 0;
        if (weapon!=null&&weapon.getName().equals("Spear")) {
            spear = 1;
        }
        for (int i = 0; i <= spear; i++) {
            if (i == 1) {
                nextField = calcNextField(dir, nextField);
            }
                switch (super.getServerModel().getCurrentFloor().getFieldAtPos(nextField).getContent()) {
                    case DRACO:hitable = true;break;
                    case BELLATRIX:hitable = true;break;
                    case LUCIUS:hitable = true;break;
                    case DEMENTOR:hitable = true;break;
                    case VOLDEMORT:hitable = true;break;
                    case SKELETT:
                        hitable = true;
                        i++;
                        break;
                    case GOBLIN:
                        hitable = true;
                        i++;
                        break;
                    case WOLF:
                        hitable = true;
                        i++;
                        break;
                    case BOSS:
                        hitable = true;
                        i++;
                        break;
                }
            }
            super.getServerModel().getCurrentFloor().getFieldAtPos(nextField).getAttackInfo().addAttackInfo(getNick(), "attackMe", attackStr());
            if (hitable) {
                this.target = super.getServerModel().getEntityAtPosition(nextField);
                int damage = super.getStr();
                if (this.getWeapon() != null) {
                    damage += this.getWeapon().getStr();
                }

                target.setHpcur((int) (target.getHpcur() - damage));

                if (target.getHpcur() <= 0) {
                    super.setExp(getExp() + floor.getLevel() + 1);
                    checkForLevelUp();
                }

            }

        return 0;
    }
    public void clientAttackme(String dir) {
        Position nextField = calcNextField(dir);
        boolean hitable = false;
        int spear = 0;
        if (weapon!=null&&weapon.getName().equals("Spear")) {
            spear = 1;
        }
        for (int i = 0; i <= spear; i++) {
            if (i == 1) {
                nextField = calcNextField(dir, nextField);
            }
            switch (super.getClientModel().getCurrentFloor().getFieldAtPos(nextField).getContent()) {
                case SKELETT:
                    hitable = true;
                    i++;
                    break;
                case GOBLIN:
                    hitable = true;
                    i++;
                    break;
                case WOLF:
                    hitable = true;
                    i++;
                    break;
                case BOSS:
                    hitable = true;
                    i++;
                    break;
            }
        }
        super.getClientModel().getCurrentFloor().getFieldAtPos(nextField).getAttackInfo().addAttackInfo(getNick(), "attackMe", attackStr());

    }

    public void relax() {
    }


    public int attackra(String dir) {
        Position nextField = calcNextField(dir);
        boolean hitable = false;

        String richtung = "attackRaDown";


        for (int i = 2; i < 6; i++) {
            switch (super.getServerModel().getCurrentFloor().getFieldAtPos(nextField).getContent()) {
                case DRACO:hitable = true;break;
                case BELLATRIX:hitable = true;break;
                case LUCIUS:hitable = true;break;
                case DEMENTOR:hitable = true;break;
                case VOLDEMORT:hitable = true;break;
                case SKELETT:
                    hitable = true;i=6;
                    break;
                case GOBLIN:
                    hitable = true;i=6;
                    break;
                case WOLF:
                    hitable = true;i=6;
                    break;
                case FLOOR:
                    nextField = furtherawayField(dir, i);break;
                case BOSS:
                    hitable = true;i=6;
                    break;
                case WALL:
                    i=6;
            }
        }
        switch (dir){

            case "w": richtung = "attackRaUp"; break;
            case "s": richtung = "attackRaDown"; break;
            case "a": richtung = "attackRaLeft"; break;
            case "d": richtung = "attackRaRight"; break;

        }
        getServerModel().getCurrentFloor().getFieldAtPos(nextField).getAttackInfo().addAttackInfo(getNick(), richtung, super.getDex());
        if (hitable) {
            this.target = super.getServerModel().getEntityAtPosition(nextField);
            int damage = super.getDex();
            target.setHpcur((int) (target.getHpcur() - damage));
            if(target.getHpcur()<= 0){
                super.setExp(getExp() + floor.getLevel()+1);
                checkForLevelUp();
            }
        }
        return 0;
    }
    public void clientAttackra(String dir){
        Position nextField = calcNextField(dir);
        boolean hitable = false;

        String richtung = "attackRaDown";


        for (int i = 2; i < 6; i++) {
            switch (super.getClientModel().getCurrentFloor().getFieldAtPos(nextField).getContent()) {
                case SKELETT:
                    hitable = true;i=6;
                    break;
                case GOBLIN:
                    hitable = true;i=6;
                    break;
                case WOLF:
                    hitable = true;i=6;
                    break;
                case FLOOR:
                    nextField = furtherawayField(dir, i);break;
                case BOSS:
                    hitable = true;i=6;
                    break;
                case WALL:
                    i=6;
            }
        }
        switch (dir){

            case "w": richtung = "attackRaUp"; break;
            case "s": richtung = "attackRaDown"; break;
            case "a": richtung = "attackRaLeft"; break;
            case "d": richtung = "attackRaRight"; break;

        }
        getClientModel().getCurrentFloor().getFieldAtPos(nextField).getAttackInfo().addAttackInfo(getNick(), richtung, super.getDex());
    }

    public List<Position> calcMagicFields(String dir, boolean isServerModel){
        List<Position> res = new LinkedList<>();
        Floor fl;
        if (isServerModel){
        fl = getServerModel().getCurrentFloor();}
        else {
            fl = getClientModel().getCurrentFloor();
        }

        Position x1,x2,x3,x4,x5,x6;
        x1 = new Position(0,0);x2 = new Position(0,0);x3 = new Position(0,0);
        x4 = new Position(0,0);x5 = new Position(0,0);x6 = new Position(0,0);

        boolean afterx1= false;
        boolean afterx2= false;
        boolean afterx3 = false;

        int yAdd = 1;
        int xAdd = 1;
        switch(dir){
            case "w": yAdd=-1;break;
            case "a": xAdd=-1;break;
        }

        if(dir.equals("s")|| dir.equals("w")) {
            x1 = new Position(getPos().getX() - 1, getPos().getY() + 1 * yAdd);
            x2 = new Position(getPos().getX(), getPos().getY() + 1 * yAdd);
            x3 = new Position(getPos().getX() + 1, getPos().getY() + 1 * yAdd);
            x4 = new Position(getPos().getX() - 1, getPos().getY() + 2 * yAdd);
            x5 = new Position(getPos().getX(), getPos().getY() + 2 * yAdd);
            x6 = new Position(getPos().getX() + 1, getPos().getY() + 2 * yAdd);
        }

        if(dir.equals("a")|| dir.equals("d")) {
            x1 = new Position(getPos().getX() +1*xAdd, getPos().getY() -1);
            x2 = new Position(getPos().getX() +1*xAdd, getPos().getY());
            x3 = new Position(getPos().getX() +1*xAdd, getPos().getY() + 1);
            x4 = new Position(getPos().getX() +2*xAdd, getPos().getY() -1 );
            x5 = new Position(getPos().getX() +2*xAdd, getPos().getY());
            x6 = new Position(getPos().getX() +2*xAdd, getPos().getY() + 1);
        }
/*        if(!fl.getFieldAtPos(x1).blocks()){
            afterx1=true;
            res.add(x1);
        }*/
        if(!fl.getFieldAtPos(x2).blocks()){
            afterx2=true;
            res.add(x2);
        }
        if(!fl.getFieldAtPos(x3).blocks()){
            afterx3=true;
            res.add(x3);
        }

        if(afterx1){res.add(x4);}
        if(afterx2){res.add(x5);}
        if(afterx3){res.add(x6);}

        return res;
    }

    public int attackma(String dir, String kind) {
       List<Position> hitFields = calcMagicFields(dir, true);

        for(int i=0; i<hitFields.size();i++){

            boolean hitable=false;
            boolean paint = false;
                    switch (super.getServerModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getContent()) {
                        case SKELETT:hitable = true;paint = true;break;
                        case GOBLIN:hitable = true;paint = true;break;
                        case BOSS:hitable = true;paint = true;break;
                        case WOLF:hitable = true;paint = true;break;
                        case DRACO:hitable = true;paint = true;break;
                        case BELLATRIX:hitable = true;paint = true;break;
                        case LUCIUS:hitable = true;paint = true;break;
                        case DEMENTOR:hitable = true;paint = true;break;
                        case VOLDEMORT:hitable = true;paint = true;break;
                        case FLOOR:paint = true;break;
                        case DOOROPEN:paint = true;break;
                    }

                    if (hitable) {
                        this.target = super.getServerModel().getEntityAtPosition(hitFields.get(i));
                        int damage = super.getStr();
                        damage += super.getWis();
                        target.setHpcur((int) (target.getHpcur() - damage));
                        if (target.getHpcur() <= 0) {
                            super.setExp(getExp() + floor.getLevel() + 1);
                            checkForLevelUp();
                        }
                    }
                    if (paint) {
                        super.getServerModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getAttackInfo().addAttackInfo(super.getNick(),kind , super.getStr() + getWis());
                    }
                }
        return 0;
    }
    public int clientAttackma(String dir) {
        List<Position> hitFields = calcMagicFields(dir,false);

        for(int i=0; i<hitFields.size();i++){

            boolean hitable=false;
            boolean paint = false;
            switch (super.getClientModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getContent()) {
                case SKELETT:hitable = true;paint = true;break;
                case GOBLIN:hitable = true;paint = true;break;
                case BOSS:hitable = true;paint = true;break;
                case WOLF:hitable = true;paint = true;break;
                case FLOOR:paint = true;break;
                case DOOROPEN:paint = true;break;
            }
            if (paint) {
                super.getClientModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getAttackInfo().addAttackInfo(super.getNick(), "attackMa", super.getStr() + getWis());
            }
        }
        return 0;
    }

    public int clientAttackIce(String dir) {
        List<Position> hitFields = calcMagicFields(dir, false);

        for (int i = 0; i < hitFields.size(); i++) {

            boolean hitable = false;
            boolean paint = false;
            switch (super.getClientModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getContent()) {
                case SKELETT:
                    hitable = true;
                    paint = true;
                    break;
                case GOBLIN:
                    hitable = true;
                    paint = true;
                    break;
                case BOSS:
                    hitable = true;
                    paint = true;
                    break;
                case WOLF:
                    hitable = true;
                    paint = true;
                    break;
                case FLOOR:
                    paint = true;
                    break;
                case DOOROPEN:
                    paint = true;
                    break;
            }
            if (paint) {
                super.getClientModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getAttackInfo().addAttackInfo(super.getNick(), "attackIce", super.getStr() + getWis());
            }
        }
        return 0;
    }

    public int clientAttackWind(String dir) {
        List<Position> hitFields = calcMagicFields(dir, false);

        for (int i = 0; i < hitFields.size(); i++) {

            boolean hitable = false;
            boolean paint = false;
            switch (super.getClientModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getContent()) {
                case SKELETT:
                    hitable = true;
                    paint = true;
                    break;
                case GOBLIN:
                    hitable = true;
                    paint = true;
                    break;
                case BOSS:
                    hitable = true;
                    paint = true;
                    break;
                case WOLF:
                    hitable = true;
                    paint = true;
                    break;
                case FLOOR:
                    paint = true;
                    break;
                case DOOROPEN:
                    paint = true;
                    break;
            }
            if (paint) {
                super.getClientModel().getCurrentFloor().getFieldAtPos(hitFields.get(i)).getAttackInfo().addAttackInfo(super.getNick(), "attackWind", super.getStr() + getWis());
            }
        }
        return 0;
    }

    public void checkForLevelUp(){
        if (getExp() >= nextLevelExp){
            setLevel(getLevel()+1);
            setExp(0);
            nextLevelExp = getLevel()*5;
            levelUp();

        }
    }

    public  void levelUp(){

    }


    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Field.cnt getContent() {
        return content;
    }

    public void setContent(Field.cnt content) {
        this.content = content;
    }

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getPotion() {
        return potion;
    }

    public void setPotion(Item potion) {
        this.potion = potion;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public String getKlasse() {
        return klasse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStandingInDoor() {
        return standingInDoor;
    }

    public void setStandingInDoor(boolean standingInDoor) {
        this.standingInDoor = standingInDoor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(double nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
}
