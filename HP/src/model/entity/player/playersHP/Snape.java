package model.entity.player.playersHP;

import model.entity.player.PlayerHP;

/**
 * Created by Felix on 13.02.2016.
 */
public class Snape extends PlayerHP {

    public Snape () {


        super.setKlasse("Snape");
        super.setHpmax(6);
        super.setHpcur(6);
        super.setStr(2);
        super.setDex(2);
        super.setWis(10);

    }
}
