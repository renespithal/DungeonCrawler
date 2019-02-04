package model.entity.monster.enemiesHP;

import model.ServerModel;
import model.entity.monster.EnemyHP;
import model.util.Field;
import model.util.Position;

/**
 * Created by Felix on 11.02.2016.
 */
public class Draco extends EnemyHP {

    public Draco(Position pos, ServerModel serverModel) {
        super.setKlasse("draco");
        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        super.setHpmax(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setStr(serverModel.getDifficultySettings().getGoblinStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.DRACO);
        super.setIdent(Field.cnt.DRACO);
        this.setNick(createMonsterNick());
    }

    public void relax(){

    }
    public Draco() {

        super.setAlive(true);
        super.setIdent(Field.cnt.DRACO);
    }


}
