package model.entity.monster.enemiesHP;

import model.ServerModel;
import model.entity.monster.EnemyHP;
import model.util.Field;
import model.util.Position;

/**
 * Created by Felix on 11.02.2016.
 */
public class Lucius extends EnemyHP {

    public Lucius(Position pos, ServerModel serverModel) {
        super.setKlasse("lucius");
        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        super.setHpmax(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setStr(serverModel.getDifficultySettings().getGoblinStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.LUCIUS);
        super.setIdent(Field.cnt.LUCIUS);
        this.setNick(createMonsterNick());
    }

    public void relax(){

    }
    public Lucius() {

        super.setAlive(true);
        super.setIdent(Field.cnt.LUCIUS);
    }

}
