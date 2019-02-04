package model.item.potion;

import model.item.Potion;

public class SmallPotion extends Potion {

    public SmallPotion(){
        super.type="potion";
        super.name="Small Potion";
        this.hp = 15;
    }
}
