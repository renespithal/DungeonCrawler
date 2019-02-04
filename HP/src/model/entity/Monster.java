package model.entity;

import model.util.Direction;
import model.util.Field;
import model.util.Position;
import network.messages.SpielzugMessage;


/**
 * Created by Jenny on 22.11.2015.
 */
public abstract class Monster extends Entity {


    private int cPlayerIndex;   // to be able to identify the closest player in case of attack

    /* implement in subclasses */
    public abstract void attack();

    public abstract SpielzugMessage think();

    private String klasse;

    public abstract void clientAttack();

    public boolean outOfSight(Position m, Position p) {
        boolean res = false;
        if (Math.abs(m.getX() - p.getX()) > 6 || Math.abs(m.getY() - p.getY()) > 6) {
            res = true;
        }
        return res;
    }


    public Position findClosestPlayerPos2(Position monster) {
        Position here = getServerModel().getPlayers().get(0).getPos();
        for (Player p : getServerModel().getPlayers()) {
            if (posDiff(monster, p.getPos()) <= posDiff(monster, here)) {
                here = p.getPos();
                cPlayerIndex = super.getServerModel().getPlayers().indexOf(p);
            }
        }
        return here;
    }


     public Position findClosestPlayerPos() {
        Position here = super.getServerModel().getPlayers().get(0).getPos();
        for (Player p : super.getServerModel().getPlayers()) {
            if (posDiff(p.getPos(), super.getPos()) < posDiff(here, super.getPos())) {
                here = p.getPos();
                cPlayerIndex = super.getServerModel().getPlayers().indexOf(p);
            }
        }
        return here;
    }

    public boolean attackYes(Position m, Position p) {

        boolean result = false;
        if (getServerModel().getCurrentFloor().getFieldAtPos(calcNextField("w")).toString().startsWith("p")) {
            result = true;
        }
        if (getServerModel().getCurrentFloor().getFieldAtPos(calcNextField("a")).toString().startsWith("p")) {
            result = true;
        }
        if (getServerModel().getCurrentFloor().getFieldAtPos(calcNextField("s")).toString().startsWith("p")) {
            result = true;
        }
        if (getServerModel().getCurrentFloor().getFieldAtPos(calcNextField("d")).toString().startsWith("p")) {
            result = true;
        }
        return result;
/*
        boolean res = false;
        if (posDiff(p, m) == 1) {
            res = true;
        }
        return res;
        */
    }

    public int posDiff(Position a, Position b) {
        int z1 = a.getX() + a.getY();
        int z2 = b.getX() + b.getY();
        int res = Math.abs(z1 - z2);
        return res;
    }


    public int posXdiff(Position p, Position m) {
        int z;
        if (p.getX() >= m.getX()) {
            z = p.getX() - m.getX();
        } else {
            z = m.getX() - p.getX();
        }
        return z;
    }

    public int posYdiff(Position p, Position m) {
        int z;
        if (p.getY() >= m.getY()) {
            z = p.getY() - m.getY();
        } else {
            z = m.getY() - p.getY();
        }
        return z;
    }

    public String createMonsterNick() {

        String whatKind;
        if (this.getIdent().equals(Field.cnt.GOBLIN)) {
            whatKind = "Goblin";
        } else if (this.getIdent().equals(Field.cnt.SKELETT)) {
            whatKind = "Skelett";
        } else if (this.getIdent().equals(Field.cnt.BOSS)) {
            whatKind = "Boss";
        } else if (this.getIdent().equals(Field.cnt.VOLDEMORT)) {
            whatKind = "Voldemort";
        } else if (this.getIdent().equals(Field.cnt.LUCIUS)) {
            whatKind = "Lucius";
        } else if (this.getIdent().equals(Field.cnt.DRACO)) {
            whatKind = "Draco";
        } else if (this.getIdent().equals(Field.cnt.DEMENTOR)) {
            whatKind = "Dementor";
        } else if (this.getIdent().equals(Field.cnt.SNAPE)) {
            whatKind = "Snape";
        } else if (this.getIdent().equals(Field.cnt.BELLATRIX)) {
            whatKind = "Bellatrix";
        } else {
            whatKind = "Wolf";
        }
        int z = getServerModel().getMonsters().size();
        String nick = "monster_" + z + "_" + whatKind;
        return nick;
    }

    public direction attackDirec(Position monster, Position player) {
        direction res = direction.NORTH;

            if (monster.getY() < player.getY()) {
                res = direction.SOUTH;
            } else if (monster.getY() > player.getY()) {
                res = direction.NORTH;
            } else if (monster.getX() > player.getX()) {
                res = direction.WEST;
            } else if (monster.getX() < player.getX()) {
                res = direction.EAST;
            }

        return res;
    }

    public boolean walkable(Position pos) {
        boolean res = false;
       /* if(this.getIdent().equals(Field.cnt.WOLF)&& getServerModel().getCurrentFloor().
                getFieldAtPos(pos).getContent().equals(Field.cnt.DOORCLOSED)){
            res = true;}
        */
        if (getServerModel().getCurrentFloor().getFieldAtPos(pos).getContent().equals(Field.cnt.FLOOR) ||
                getServerModel().getCurrentFloor().getFieldAtPos(pos).getContent().equals(Field.cnt.DOOROPEN)) {
            res = true;
        }

        return res;
    }

    public String quadrant(Position monster, Position player) {
        String res = "def";
        if (monster.getX() >= player.getX() && monster.getY() >= player.getY()) {
            res = "ol";
        } else if (monster.getX() <= player.getX() && monster.getY() <= player.getY()) {
            res = "ur";
        } else if (monster.getX() > player.getX() && monster.getY() < player.getY()) {
            res = "or";
        } else if (monster.getX() < player.getX() && monster.getY() > player.getY()) {
            res = "lu";
        }
        return res;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public int getcPlayerIndex() {
        return cPlayerIndex;
    }

    public void setcPlayerIndex(int cPlayerIndex) {
        this.cPlayerIndex = cPlayerIndex;
    }
}
