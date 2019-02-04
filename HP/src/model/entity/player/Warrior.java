package model.entity.player;

import model.ServerModel;
import model.util.Field;
import model.Floor;
import model.entity.Player;
import model.util.Position;

/**
 * Created by Jenny on 22.11.2015.
 */
public class Warrior extends Player {

    public Warrior() {
        super.setKlasse("Warrior");
        super.setAlive(true);
        super.setLevel(1);
        super.setExp(0);
        super.setHpmax(8);
        super.setHpcur(8);
        super.setStr(8);
        super.setDex(4);
        super.setWis(1);
        super.setPotion(null);
        super.setWeapon(null);
    }
    public void levelUp(){
        super.setHpmax(getHpmax()+2);
        super.setStr(getStr()+3);
        super.setDex(getDex()+1);
        super.setHpcur(getHpmax());

    }

//    @Override
//    public void wait() {
//        /* TODO: implement */
//    }

    private int playerXPos;
    private int playerYPos;

//
//    @Override
//    public int attackme(int str, boolean spearWeapon) {
//        int meleeDmg = str;
//        boolean spear = spearWeapon;
//        playerXPos = getPos().getX();
//        playerYPos = getPos().getY();
//        switch (entityDir){
//            case EAST:
//                if (spear = false){
//                    if(getContent()== Field.cnt.MONSTER){
//                        return meleeDmg;
//                    }
//                    else
//                        return 0;
//                }
//                else {
//                    if(getContent() == Field.cnt.MONSTER){
//                        return meleeDmg;
//
//                    }
//                    else return 0;
//                }
//
//            case WEST:
//
//            case NORTH:
//            case SOUTH:
//        }
//            return meleeDmg;
//
//    }

}
