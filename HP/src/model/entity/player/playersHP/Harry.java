package model.entity.player.playersHP;

import model.entity.player.PlayerHP;

/**
 * Created by Felix on 13.02.2016.
 */
public class Harry extends PlayerHP {

    public Harry(){
        super.setKlasse("Harry");
        super.setHpmax(8);
        super.setHpcur(8);
        super.setStr(2);
        super.setDex(5);
        super.setWis(5);



    }

    public void attackMa(String dir, String type){
        attackme(dir);
    }

}
