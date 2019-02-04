package model.entity.player;

import model.ServerModel;
import model.util.Field;
import model.Floor;
import model.entity.Player;
import model.util.Position;

/**
 * Created by Jenny on 22.11.2015.
 */
public class Professor extends Player {

    public Professor() {
        super.setKlasse("Professor");
        super.setAlive(true);
        super.setLevel(1);
        super.setExp(0);
        super.setHpmax(6);
        super.setHpcur(6);
        super.setStr(3);
        super.setDex(3);
        super.setWis(9);
        super.setPotion(null);
        super.setWeapon(null);
    }
    public void levelUp(){
        super.setHpmax(getHpmax()+1);
        super.setStr(getStr()+1);
        super.setDex(getDex()+1);
        super.setWis(getWis()+3);
        super.setHpcur(getHpmax());

    }


//    @Override
//    public void wait() {
//        /* TODO: implement */
//    }

}
