package model.entity.player.playersHP;

import model.entity.Entity;
import model.entity.player.PlayerHP;
import model.util.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Felix on 13.02.2016.
 */
public class Dumbledore extends PlayerHP {

    public Dumbledore() {
        super.setKlasse("Dumbledore");
        super.setHpmax(4);
        super.setHpcur(4);
        super.setStr(2);
        super.setDex(4);
        super.setWis(12);

    }

    public int attackma(String dir, String kind) {
        Position cur = calcNextField(dir,super.getPos());

        List<Position> fields = new LinkedList<>();

        String dir1="w";
        String dir2="s";
        switch(dir){
            case "w": dir1="d"; dir2="a";break;
            case "s": dir1="d"; dir2="a";break;
        }

        for(int i=0; i<5; i++) {
            if (!super.getServerModel().getCurrentFloor().getFieldAtPos(cur).blocks()) {
                fields.add(cur);
                if (i == 0 || i == 2 || i == 4) {
                    Position backup = cur;
                    for (int j = 0; j < 2; j++) {
                        cur = backup;
                        String curDir = dir1;
                        if (j == 1) {
                            curDir=dir2;
                        }

                        for (int x = 0; x < 2; x++) {
                            cur = calcNextField(curDir, cur);
                                if (!super.getServerModel().getCurrentFloor().getFieldAtPos(cur).blocks()) {
                                    fields.add(cur);
                                }
                            else x=2;
                            }
                        }
                    cur = backup;
                }
                cur = calcNextField(dir, cur);
            }else i=5;
        }

        for(int i=0; i<fields.size(); i++){
            if (super.getServerModel().getEntityAtPosition(fields.get(i)) != null) {
                Entity target = super.getServerModel().getEntityAtPosition(fields.get(i));
                target.setHpcur(target.getHpcur() - super.getWis());
            }
            super.getServerModel().getCurrentFloor().getFieldAtPos(fields.get(i)).getAttackInfo().
                    addAttackInfo(super.getNick(), "attackMa", super.getWis());
        }

        return 0;
        }






}
