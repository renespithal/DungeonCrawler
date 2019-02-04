package model.entity.monster.enemiesHP;

import model.ServerModel;
import model.entity.monster.EnemyHP;
import model.util.Field;
import model.util.Position;

/**
 * Created by Felix on 11.02.2016.
 */
public class Bellatrix extends EnemyHP {

    public Bellatrix(Position pos, ServerModel serverModel) {
        super.setKlasse("bellatrix");
        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        super.setHpmax(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setStr(serverModel.getDifficultySettings().getWolfStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.BELLATRIX);
        super.setIdent(Field.cnt.BELLATRIX);
        this.setNick(createMonsterNick());
    }

    public void relax(){

    }
    public Bellatrix() {

        super.setAlive(true);
        super.setIdent(Field.cnt.BELLATRIX);
    }


}
