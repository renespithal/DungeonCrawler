package model.entity.player;

import model.entity.Player;

/**
 * Created by Felix on 13.02.2016.
 */
public class PlayerHP extends Player {

    public PlayerHP(){

        super.setAlive(true);
        super.setLevel(1);
        super.setExp(0);
        super.setPotion(null);
        super.setWeapon(null);

    }
/*
    public int attackma(String dir, String kind) {
        attackma(dir, kind);
        return 0;
    }
*/
    public void levelUp(){
        super.setHpmax(getHpmax()+2);
        super.setStr(getStr()+1);
        super.setDex(getDex()+1);
        super.setWis(getWis()+2);
        super.setHpcur(getHpmax());
    }



}
