package master;


import model.ServerModel;
import model.entity.Entity;
import network.messages.SpielzugMessage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GameCalculator {

    ServerModel serverModel;
    List<ActingInformation> damagedFields = new LinkedList<>();
    List<ActingInformation> filteredSpielzüge;

    public GameCalculator(ServerModel serverModel){
        this.serverModel = serverModel;
    }

    public void calculateRound(List<ActingInformation> filteredSpielzüge) throws IOException {
        damagedFields.clear();
        this.filteredSpielzüge=filteredSpielzüge;
        for(int i=0; i<filteredSpielzüge.size(); i++) {
            Entity.direction dir = Entity.direction.NORTH;
            switch(filteredSpielzüge.get(i).getSpielzug().getDirection()){
                case "a": dir = Entity.direction.WEST;break;
                case "s": dir = Entity.direction.SOUTH;break;
                case "d": dir = Entity.direction.EAST;break;
            }
            switch(filteredSpielzüge.get(i).getSpielzug().getAction()){
                case "move":    if(filteredSpielzüge.get(i).getEntityType().equals("monster")) {

                                    serverModel.getMonsters().get(filteredSpielzüge.get(i).getIndexInList()).
                                            move(dir);
                                }
                                else {
                                    serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList()).
                                            move(dir);
                                }break;
                case "use":      if(filteredSpielzüge.get(i).getEntityType().equals("monster")) {

                                }
                                else {
                                    serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList())
                                            .use(filteredSpielzüge.get(i).getSpielzug().getDirection());
                                }break;
                case "attackMe": if(filteredSpielzüge.get(i).getEntityType().equals("monster")){
                                serverModel.getMonsters().get(filteredSpielzüge.get(i).getIndexInList()).attack();

                                }
                                 else{
                                    serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList()).
                                            attackme(filteredSpielzüge.get(i).getSpielzug().getDirection());
                                    System.out.println("AttackMe");
                                }
                                damagedFields.add(filteredSpielzüge.get(i));break;
                case "attackRa": if(filteredSpielzüge.get(i).getEntityType().equals("monster")){
                                        serverModel.getMonsters().get(filteredSpielzüge.get(i).getIndexInList()).attack();
                                    }
                                    else{
                                        serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList()).
                                                attackra(filteredSpielzüge.get(i).getSpielzug().getDirection());
                                    }
                                 damagedFields.add(filteredSpielzüge.get(i));break;
                case "attackMa": if(filteredSpielzüge.get(i).getEntityType().equals("monster")){
                                    serverModel.getMonsters().get(filteredSpielzüge.get(i).getIndexInList()).attack();
                                }
                                else{
                                    serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList()).
                                            attackma(filteredSpielzüge.get(i).getSpielzug().getDirection(), "attackMa");
                                    System.out.println("AttackMa");
                                }
                            damagedFields.add(filteredSpielzüge.get(i));break;
                case "attackIce": if(filteredSpielzüge.get(i).getEntityType().equals("monster")){
                                    serverModel.getMonsters().get(filteredSpielzüge.get(i).getIndexInList()).attack();
                                }
                                else{
                                    serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList()).
                                            attackma(filteredSpielzüge.get(i).getSpielzug().getDirection(), "attackIce");
                                }
                                    damagedFields.add(filteredSpielzüge.get(i));break;
                case "attackWind": if(filteredSpielzüge.get(i).getEntityType().equals("monster")){
                                        serverModel.getMonsters().get(filteredSpielzüge.get(i).getIndexInList()).attack();
                                    }
                                    else{
                                        serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList()).
                                                attackma(filteredSpielzüge.get(i).getSpielzug().getDirection(), "attackWind");
                                        System.out.println("AttackWind");
                                    }
                                        damagedFields.add(filteredSpielzüge.get(i));break;
                case "wait":  if(filteredSpielzüge.get(i).getEntityType().equals("monster")){
                                }
                                else{
                                    serverModel.getPlayers().get(filteredSpielzüge.get(i).getIndexInList()).relax();
                                }
            }
        }

        for(int i=0; i<serverModel.getChests().size(); i++){
            serverModel.getChests().get(i).move();
        }

        serverModel.setMapChange(serverModel.getMapChange()+1);
    }

    public SpielzugMessage[] repackToSpielzug() {

        SpielzugMessage[] result = new SpielzugMessage[filteredSpielzüge.size()];

        for(int i=0; i<filteredSpielzüge.size();i++) {
            ActingInformation cur = filteredSpielzüge.get(i);
            result[i]= cur.getSpielzug();
        }
        return result;
    }

    public List<ActingInformation> getDamagedFields() {
        return damagedFields;
    }
}
