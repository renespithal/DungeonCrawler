package model.item;


public abstract class Weapon extends Item {

    int damage;

    public Weapon(){
        damage=0;
    }


    public int getDamage() {
        return damage;
    }
}
