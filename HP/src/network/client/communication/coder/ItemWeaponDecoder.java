package network.client.communication.coder;

import model.item.Item;
import model.item.potion.BigPotion;
import model.item.potion.SmallPotion;
import model.item.weapon.Axe;
import model.item.weapon.Spear;
import model.item.weapon.Sword;

/**
 * Created by Felix on 07.01.2016.
 */
public class ItemWeaponDecoder {
    public ItemWeaponDecoder(){
    }
   public Item decodeItemWeapon(String string){
        switch (string){
            case "smallPotition":{
                SmallPotion smallPotion = new SmallPotion();
                return smallPotion;

            }
            case "bigPotition":{
                BigPotion bigPotion = new BigPotion();
                return bigPotion;
            }
            case "Axe":{
                Axe axe = new Axe();
                return axe;
            }
            case "Spear":{
                Spear spear = new Spear();
                return spear;
            }
            case "Sword":{
                Sword sword = new Sword();
                return sword;
            }
            default:{
                SmallPotion smallPotion = new SmallPotion();
                return smallPotion;
            }
        }
    }
}
