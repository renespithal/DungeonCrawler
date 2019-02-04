package model.item;

public abstract class Item {
    protected String type;
    protected String name;
    protected int hp;
    protected int str;
    protected int dex;
    protected int wis;
    protected int ra;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getStr() {
        return str;
    }

    public int getRa() {
        return ra;
    }
}
