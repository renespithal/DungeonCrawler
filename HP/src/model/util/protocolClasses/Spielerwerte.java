package model.util.protocolClasses;

/**
 * Created by Adrian on 08.12.2015.
 */
public class Spielerwerte {
    int level;
    int exp;
    Spielerposition player_positions;
    int hpcur;
    int hpmax;
    int str;
    int dex;
    int wis;

    public Spielerwerte(int level,int exp,Spielerposition player_positions, int hpcur,int hpmax,int str, int dex, int wis){
        this.level = level;
        this.exp = exp;
        this.player_positions = player_positions;
        this.hpcur = hpcur;
        this.hpmax = hpmax;
        this.str = str;
        this.dex = dex;
        this.wis = wis;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public Spielerposition getPlayer_positions() {
        return player_positions;
    }

    public int getHpcur() {
        return hpcur;
    }

    public int getHpmax() {
        return hpmax;
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public int getWis() {
        return wis;
    }
}
