package model.util;

/**
 * Created by Felix on 19.01.2016.
 */
public class AttackInfo {

    private boolean attacked = false;
    private String attackedBy="";
    private String attackTyp="";
    private int withStr = 0;


    public AttackInfo() {
    }


    public void reset(){
        attacked=false;
        this.attackedBy = "";
        this.withStr =0;
    }

    public void addAttackInfo(String attackedBy, String attackTyp, int withStr){
        attacked=true;
        this.attackedBy = attackedBy;
        this.attackTyp = attackTyp;
        this.withStr +=withStr;

    }

    public boolean isAttacked() {
        return attacked;
    }

    public int getWithStr() {
        return withStr;
    }

    public String getAttackTyp(){return attackTyp; }
}
