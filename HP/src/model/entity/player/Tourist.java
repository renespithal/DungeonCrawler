package model.entity.player;

import model.ServerModel;
import model.util.Field;
import model.Floor;
import model.entity.Player;
import model.util.Position;

/**
 * Created by Jenny on 22.11.2015.
 */
public class Tourist extends Player {

    public Tourist(){
        super.setKlasse("Tourist");
        super.setAlive(true);
        super.setLevel(1);
        super.setExp(0);
        super.setHpmax(18);
        super.setHpcur(18);
        super.setStr(1);
        super.setDex(1);
        super.setWis(1);
        super.setPotion(null);
        super.setWeapon(null);
    }
    public void levelUp(){
        super.setHpmax(getHpmax()+6);
        super.setHpcur(getHpmax());

    }


//    @Override
//    public void wait() {
//        /* TODO: implement */
//    }


}
