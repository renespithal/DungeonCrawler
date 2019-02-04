package model.entity.player;

import model.ServerModel;
import model.util.Field;
import model.Floor;
import model.util.Position;
import model.entity.Player;

/**
 * Created by Jenny on 22.11.2015.
 */
public class Ranger extends Player {

    public Ranger(){
        super.setKlasse("Ranger");
        super.setAlive(true);
        super.setLevel(1);
        super.setExp(0);
        super.setHpmax(7);
        super.setHpcur(7);
        super.setStr(3);
        super.setDex(9);
        super.setWis(2);
        super.setPotion(null);
        super.setWeapon(null);
    }
    public void levelUp(){
        super.setHpmax(getHpmax()+2);
        super.setStr(getStr()+1);
        super.setDex(getDex()+3);
        super.setHpcur(getHpmax());

    }


//    @Override
//    public void wait() {
//        /* TODO: implement */
//    }

    private int playerXPos;
    private int playerYPos;


}
