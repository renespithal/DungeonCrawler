package model.entity.player.playersHP;

import model.entity.Player;
import model.entity.player.PlayerHP;

/**
 * Created by Felix on 13.02.2016.
 */
public class Hagrid extends PlayerHP {

    public Hagrid (){
        super.setKlasse("Hagrid");
        super.setHpmax(12);
        super.setHpcur(12);
        super.setStr(6);
        super.setDex(1);
        super.setWis(1);

    }
}
