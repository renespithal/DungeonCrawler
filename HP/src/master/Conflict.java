package master;

import model.ServerModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Felix on 08.12.2015.
 */
public class Conflict {

    ServerModel serverModel;
    String type;
    List<String[]> involvedActors = new LinkedList<String[]>();

    public Conflict(ServerModel serverModel, String type){
        this.serverModel = serverModel;
        this.type=type;
    }

    public String solveConflict(){

        String winner=null;
        int winningLevel=100;
        for (int i=0; i<involvedActors.size();i++){
            int curLevel =0;
            switch(involvedActors.get(i)[2]){
                case "monser": curLevel = serverModel.getMonsters().get(Integer.parseInt(involvedActors.get(i)[1])).getLevel();break;
                case "player": curLevel = serverModel.getPlayers().get(Integer.parseInt(involvedActors.get(i)[1])).getLevel();break;
            }

            if(curLevel<winningLevel){
                winningLevel=curLevel;
                winner=involvedActors.get(i)[0];
            }
        }

        return winner;
    }

    public void addInvolvedActor(String []info) {
        involvedActors.add(info);
    }
    public List<String[]> getInvolvedActors() {
        return involvedActors;
    }
}
