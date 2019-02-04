package master;


import model.ServerModel;
import model.util.Field;
import model.util.Position;
import network.messages.SpielzugMessage;
import sun.security.provider.ConfigFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GameFilter {

    ServerModel serverModel;

    List<String> playerLogin = new LinkedList<>();

    List<ActingInformation> move = new LinkedList<ActingInformation>();
    List<ActingInformation> attackMa =  new LinkedList<ActingInformation>();
    List<ActingInformation> attackMe =  new LinkedList<ActingInformation>();
    List<ActingInformation> attackRa =  new LinkedList<ActingInformation>();
    List<ActingInformation> use =  new LinkedList<ActingInformation>();
    List<ActingInformation> wait =  new LinkedList<ActingInformation>();
    List<ActingInformation> attackWind =  new LinkedList<ActingInformation>();
    List<ActingInformation> attackIce =  new LinkedList<ActingInformation>();

    List<String> noConflict = new LinkedList<String>();
    List<Conflict> foundConflicts = new LinkedList<Conflict>();
    List<ActingInformation> actingInformations = new LinkedList<ActingInformation>();

    List<SpielzugMessage> spielzüge = new LinkedList<SpielzugMessage>();
    List<ActingInformation> filteredSpielzüge = new LinkedList<ActingInformation>();

    private boolean superbool = false;
    private ActingInformation supersave;


    public GameFilter(ServerModel serverModel){
        this.serverModel = serverModel;
    }

    public List<ActingInformation> filter(List<SpielzugMessage> input) throws IOException {
        this.spielzüge=input;
        resetLists();
        packActorInformations();
        seperateListsInSublists(actingInformations);

      //  filterMoves();

        findAndSolveConflictsOfType("move");
        findAndSolveConflictsOfType("use");

        for(int q=0; q<noConflict.size();q++) {
            addSpielzugOfActor(noConflict.get(q));
        }
        for (int q=0; q<attackMe.size();q++){
            addSpielzugOfActor(attackMe.get(q).getSpielzug().getNick());
        }
        for (int q=0; q<attackRa.size();q++){
            addSpielzugOfActor(attackRa.get(q).getSpielzug().getNick());
        }
        for (int q=0; q<attackMa.size();q++){
            addSpielzugOfActor(attackMa.get(q).getSpielzug().getNick());
        }
        for (int q=0; q<attackIce.size();q++){
            addSpielzugOfActor(attackIce.get(q).getSpielzug().getNick());
        }
        for (int q=0; q<attackWind.size();q++){
            addSpielzugOfActor(attackWind.get(q).getSpielzug().getNick());
        }
        for (int q=0; q<wait.size();q++){
            addSpielzugOfActor(wait.get(q).getSpielzug().getNick());
        }
      //  // System.out.println("So viele valide Spielzüge gibt es: "+filteredSpielzüge.size());
        sortFilteredList();
        if(superbool){
            filteredSpielzüge.clear();
            filteredSpielzüge.add(supersave);
            superbool=false;
        }
        return filteredSpielzüge;
    }




    private void packActorInformations() {
        for(int i=0; i<spielzüge.size();i++ ){
            SpielzugMessage cur = spielzüge.get(i);
            String entityType = null;
            Position nextField =null;
            int index;
            if(spielzüge.get(i).getNick().startsWith("monster")){
                entityType= "monster";
                index = Integer.parseInt(cur.getNick().substring(8,9));
                nextField = serverModel.getMonsters().get(index).calcNextField(cur.getDirection());
            }else{
                entityType= "player";
                index = nickToPlayerNumber(cur.getNick());
                nextField = serverModel.getPlayers().get(index).calcNextField(cur.getDirection());
            }

            ActingInformation info = new ActingInformation(entityType, index, spielzüge.get(i), nextField);
            actingInformations.add(info);
        }


    }

    public void findAndSolveConflictsOfType(String actionType) throws IOException {
        foundConflicts.clear();
     //   // System.out.println("find and Solve " + actionType + " conflicts");

        List<ActingInformation> actionTypeList = new LinkedList<ActingInformation>();

        switch(actionType){
            case "use": actionTypeList = use;break;
            case "move": actionTypeList = move;break;
        }

        // go through action Type list and check if the Player isn't already incolved in a conflict
        for (int i = 0; i < actionTypeList.size(); i++) {

            if(actionTypeList.get(i).getSpielzug().getAction().equals("use")){
                if(serverModel.getCurrentFloor().getFieldAtPos(actionTypeList.get(i).getNextField()).getContent().equals(Field.cnt.STAIRDOWN)) {

                    supersave = actionTypeList.get(i);
                    resetLists();
                    superbool = true;
                    i = actionTypeList.size();
                    filteredSpielzüge.clear();
                    break;
                }
            }

            if(validSpielzug(serverModel.getCurrentFloor().getFieldAtPos(actionTypeList.get(i).getNextField()).getContent())) {
                if (!checkIfPlayerIsAlreadyInvolved(actionTypeList.get(i).getSpielzug().getNick())) {
                   // // System.out.println("Not already in conflict");

                    // Info about Actor number i
                    String nick = actionTypeList.get(i).getSpielzug().getNick();
                    int indexInList = actionTypeList.get(i).getIndexInList();
                    String type = actionTypeList.get(i).getEntityType();
                    Position actorNextField = actionTypeList.get(i).getNextField();


                    // Create new Conflict and add the MainActor
                    Conflict curConflict = new Conflict(serverModel, actionTypeList.get(i).getSpielzug().getAction());
                    String[] actorInfo = new String[3];
                    actorInfo[0] = nick;
                    actorInfo[1] = "" + indexInList;
                    actorInfo[2] = type;
                    curConflict.addInvolvedActor(actorInfo);

                    // Add each actor, that wants to move/use/attack the same field to the conflict
                    for (int y = 0; y < actionTypeList.size(); y++) {
                        if (actorNextField.getX() == actionTypeList.get(y).getNextField().getX()
                                && actorNextField.getY() == actionTypeList.get(y).getNextField().getY()) {
                            if (y != i) {
                                String[] curInfo = new String[3];
                                curInfo[0] = actionTypeList.get(y).getSpielzug().getNick();
                                curInfo[1] = "" + actionTypeList.get(y).getIndexInList();
                                curInfo[2] = actionTypeList.get(y).getEntityType();
                                curConflict.addInvolvedActor(curInfo);

                            } else {

                            }
                        } else {
                        }
                    }

                    // If there is a conflict that needs to be taken care of, add it to the main List
                    if (curConflict.getInvolvedActors().size() > 1) {
                        foundConflicts.add(curConflict);
                    } else {
                        noConflict.add(nick);
                    }
                }
            }
        }
        // Solve each conflict and add only the spielzug of the winning actor to the filteredSpielzüge List
        for (int z = 0; z < foundConflicts.size(); z++) {
            for (int j = 0; j < foundConflicts.get(z).getInvolvedActors().size(); j++) {
            }
            String winner = foundConflicts.get(z).solveConflict();
            addSpielzugOfActor(winner);
        }
    }

    private boolean validSpielzug(Field.cnt field) {
        boolean res = true;
        if(field.equals(Field.cnt.WALL) ){
            res=false;
        }
        return res;
    }

    private void seperateListsInSublists(List<ActingInformation> info) {
        for(int i=0; i<info.size();i++){
            ActingInformation actInfo = info.get(i);
            switch(actInfo.getSpielzug().getAction()){
                case "move": move.add(actInfo);break;
                case "attackMa": attackMa.add(actInfo);break;
                case "attackIce": attackIce.add(actInfo);break;
                case "attackWind": attackWind.add(actInfo);break;
                case "attackMe": attackMe.add(actInfo);break;
                case "attackRa": attackRa.add(actInfo);break;
                case "use": use.add(actInfo);break;
                case "wait": wait.add(actInfo);break;
            }
        }
    }

    private void addSpielzugOfActor(String nick){
        for(int i=0; i<actingInformations.size(); i++){
            if(actingInformations.get(i).getSpielzug().getNick().equals(nick)){
                filteredSpielzüge.add(actingInformations.get(i));
            }
        }
    }

    private void resetLists() {
        attackMa.clear();
        attackIce.clear();
        attackWind.clear();
        attackMe.clear();
        attackRa.clear();
        wait.clear();
        use.clear();
        move.clear();
        actingInformations.clear();
        foundConflicts.clear();
        noConflict.clear();
        filteredSpielzüge.clear();
    }

    private int nickToPlayerNumber(String nick) {
        int result=0;
        for (int i = 0; i < serverModel.getPlayers().size(); i++){
            if (nick.equals(serverModel.getPlayers().get(i).getNick())){

                result = i;
            }
        }
        System.out.println("NUMMER " + result + " wurde zugeordnet");
        return result;
    }

    private void sortFilteredList(){
        move.clear();attackIce.clear();attackWind.clear();
        use.clear();attackMa.clear();attackRa.clear();attackMe.clear();wait.clear();
        int attackMePC =0;
        int attackRaPC =0;
        int attackMaPC =0;
        int attackIcePC = 0;
        int attackWindPC = 0;
        int movePC =0;
        int usePC=0;
        int waitPC =0;

        for(int mainList =0; mainList<filteredSpielzüge.size();mainList++){
            List<ActingInformation> addList = new LinkedList<ActingInformation>();
            switch(filteredSpielzüge.get(mainList).getSpielzug().getAction()){
                case "attackMe":
                                if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                                    attackMe.add(attackMe.size(), filteredSpielzüge.get(mainList));
                                }
                                    else{
                                    attackMe.add(attackMePC, filteredSpielzüge.get(mainList));
                                    attackMePC++;
                                }
                case "attackMa":
                    if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                        attackMa.add(attackMa.size(), filteredSpielzüge.get(mainList));
                    }
                    else{
                        attackMa.add(attackMaPC, filteredSpielzüge.get(mainList));
                        attackMaPC++;
                    }break;
                case "attackIce":
                    if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                        attackIce.add(attackIce.size(), filteredSpielzüge.get(mainList));
                    }
                    else{
                        attackIce.add(attackIcePC, filteredSpielzüge.get(mainList));
                        attackIcePC++;
                    }break;
                case "attackWind":
                    if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                        attackWind.add(attackWind.size(), filteredSpielzüge.get(mainList));
                    }
                    else{
                        attackWind.add(attackWindPC, filteredSpielzüge.get(mainList));
                        attackWindPC++;
                    }break;
                case "attackRa":
                    if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                        attackRa.add(attackRa.size(), filteredSpielzüge.get(mainList));
                    }
                    else{
                        attackRa.add(attackRaPC, filteredSpielzüge.get(mainList));
                        attackRaPC++;
                    }break;

                case "use":
                    if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                        use.add(use.size(), filteredSpielzüge.get(mainList));
                    }
                    else{
                        use.add(usePC, filteredSpielzüge.get(mainList));
                        usePC++;
                    }break;

                case "move":
                    if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                        move.add(move.size(), filteredSpielzüge.get(mainList));
                    }
                    else{
                        move.add(movePC, filteredSpielzüge.get(mainList));
                        movePC++;
                    }break;

                case "wait":
                    if(filteredSpielzüge.get(mainList).getEntityType().equals("monster")){
                        wait.add(wait.size(), filteredSpielzüge.get(mainList));
                    }
                    else{
                        wait.add(waitPC, filteredSpielzüge.get(mainList));
                        waitPC++;
                    }break;
            }
        }


        filteredSpielzüge.clear();
        for(int x=0; x<attackMe.size();x++){
            filteredSpielzüge.add(attackMe.get(x));
            }
        for(int x=0; x<attackRa.size();x++){
            filteredSpielzüge.add(attackRa.get(x));
        }
        for(int x=0; x<attackMa.size();x++){
            filteredSpielzüge.add(attackMa.get(x));
        }
        for(int x=0; x<attackIce.size();x++){
            filteredSpielzüge.add(attackIce.get(x));
        }
        for(int x=0; x<attackWind.size();x++){
            filteredSpielzüge.add(attackWind.get(x));
        }
        for(int x=0; x<use.size();x++){
            filteredSpielzüge.add(use.get(x));
        }
        for(int x=0; x<move.size();x++){
            filteredSpielzüge.add(move.get(x));
        }
        for(int x=0; x<wait.size();x++){
            filteredSpielzüge.add(wait.get(x));
        }





        for(int i=0;i<filteredSpielzüge.size();i++){
        }
    }


    public boolean checkIfPlayerIsAlreadyInvolved(String name ){
        boolean result = false;
        for(int i=0;i<foundConflicts.size();i++){
            List<String[]> curInvolvedActors = foundConflicts.get(i).getInvolvedActors();

            for(int j=0; j<curInvolvedActors.size();j++) {
                if (name.equals(curInvolvedActors.get(j)[0])) {
                    result = true;
                    break;
                }
            }
            }
        return result;
    }


    public void addPlayerLogin(String nick){
        playerLogin.add(nick);
    }
}
