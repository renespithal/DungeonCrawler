package model.entity.player;

import model.ServerModel;
import model.util.Field;
import model.Floor;
import model.entity.Player;
import model.util.Position;

/**
 * Created by Jenny on 22.11.2015.
 */
public class Student extends Player {

    public Student(){
        super.setKlasse("Student");
        super.setAlive(true);
        super.setLevel(1);
        super.setExp(0);
        super.setHpmax(6);
        super.setHpcur(6);
        super.setStr(5);
        super.setDex(5);
        super.setWis(5);
        super.setPotion(null);
        super.setWeapon(null);
    }
    public void levelUp(){
        super.setStr(getStr()+2);
        super.setDex(getDex()+2);
        super.setWis(getWis()+2);
        super.setHpcur(getHpmax());

    }


//    @Override
//    public void wait() {
//        /* TODO: implement */
//    }


}
