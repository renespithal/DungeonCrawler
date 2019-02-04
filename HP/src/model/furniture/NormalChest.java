package model.furniture;

import model.util.Position;

/**
 * Created by Felix on 18.01.2016.
 */
public class NormalChest extends Chest {

    public NormalChest(Position position){
        super.setPosition(position);
        super.setClosed(true);
        super.setItem(generateItem());
        super.setNick("NormalChest");
    }

}
