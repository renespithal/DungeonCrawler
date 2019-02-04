package model.entity.player;

import model.entity.Player;

/**
 * Created by Jenny on 22.11.2015.
 */
public class Mage extends Player {

    public Mage(){
        super.setKlasse("Mage");
        super.setAlive(true);
        super.setLevel(1);
        super.setExp(0);
        super.setHpmax(4);
        super.setHpcur(4);
        super.setStr(3);
        super.setDex(4);
        super.setWis(10);
        super.setPotion(null);
        super.setWeapon(null);

    }

    public int attack(String dir){
       attackma(dir, "ice");
        return 0;
    }
    public void levelUp(){
        super.setHpmax(getHpmax()+2);
        super.setStr(getStr()+1);
        super.setDex(getDex()+1);
        super.setWis(getWis()+2);
        super.setHpcur(getHpmax());

    }




//    @Override
//    public void wait() {
//        /* TODO: implement */
//    }


}
