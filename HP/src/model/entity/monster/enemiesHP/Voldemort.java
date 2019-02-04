package model.entity.monster.enemiesHP;

import model.ServerModel;
import model.entity.monster.EnemyHP;
import model.util.Field;
import model.util.Position;

/**
 * Created by Felix on 11.02.2016.
 */
public class Voldemort extends EnemyHP {

    public Voldemort(Position pos, ServerModel serverModel) {
        super.setKlasse("voldemort");
        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        super.setHpmax(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setStr(serverModel.getDifficultySettings().getGoblinStr());
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.VOLDEMORT);
        super.setIdent(Field.cnt.VOLDEMORT);
        this.setNick(createMonsterNick());
    }

    public void relax(){

    }
    public Voldemort() {

        super.setAlive(true);
        super.setIdent(Field.cnt.VOLDEMORT);
    }

}
