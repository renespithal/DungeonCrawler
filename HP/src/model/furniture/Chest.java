package model.furniture;

import model.item.Item;
import model.item.potion.BigPotion;
import model.item.potion.SmallPotion;
import model.item.weapon.Axe;
import model.item.weapon.Spear;
import model.item.weapon.Sword;
import model.util.Field;
import model.util.Position;

import java.util.Random;

/**
 * Created by Felix on 02.12.2015.
 */
public class Chest {
    
    private boolean closed;
    private Item item;
    private Position position;
    private String nick;

    public Item take() {
        Item temp = item;
        this.item=null;
        return temp;

    }

    public void move(){
    }

    public Item generateItem() {
        Item result = null;
        final Random random = new Random();
        int choose = 1 + random.nextInt(10);
        switch(choose){
            case 1: result = new Sword();break;
            case 2: result = new Sword();break;
            case 3: result = new Sword();break;
            case 4: result = new Axe();break;
            case 5: result = new Axe();break;
            case 6: result = new Spear();break;
            case 7: result = new SmallPotion();break;
            case 8: result = new SmallPotion();break;
            case 9: result = new SmallPotion();break;
            case 10: result = new BigPotion();break;
        }
        return result;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }


    public void setPosition(Position position) {
        this.position = position;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }
}
