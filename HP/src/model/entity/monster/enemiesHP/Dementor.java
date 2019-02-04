package model.entity.monster.enemiesHP;

import model.ServerModel;
import model.entity.monster.EnemyHP;
import model.util.Field;
import model.util.Position;

/**
 * Created by Adrian on 16.02.2016.
 */
public class Dementor extends EnemyHP {

    public Dementor(Position pos, ServerModel serverModel) {
        super.setKlasse("dementor");
        super.setAlive(true);
        super.setServerModel(serverModel);
        super.setPos(pos);
        super.setHpmax(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setHpcur(serverModel.getDifficultySettings().getSkelettHPmax());
        super.setStr(1);
        serverModel.getCurrentFloor().getFieldAtPos(pos).setContent(Field.cnt.BELLATRIX);
        super.setIdent(Field.cnt.DEMENTOR);
        this.setNick(createMonsterNick());
    }

    public void relax() {

    }

    public Dementor() {
        super.setAlive(true);
        super.setIdent(Field.cnt.DEMENTOR);
    }



}
