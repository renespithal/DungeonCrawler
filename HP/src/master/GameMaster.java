package master;

import model.ServerModel;
import network.messages.SpielzugMessage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Felix on 13.12.2015.
 */
public class GameMaster {

    GameFilter gameFilter;
    GameCalculator gameCalculator;
    ServerModel serverModel;

    List<SpielzugMessage> input = new LinkedList<>();
    List<ActingInformation> filteredList;
    SpielzugMessage[] output;
    private List<ActingInformation> damagedFields = new LinkedList<>();

    public GameMaster( ServerModel serverModel){
        this.serverModel = serverModel;

        gameFilter = new GameFilter(serverModel);
        gameCalculator = new GameCalculator(serverModel);
    }

    public void calculateEverything() throws IOException {
        output = new SpielzugMessage[0];
        monsterCalc();
        filteredList = gameFilter.filter(input);
        gameCalculator.calculateRound(filteredList);
        damagedFields = gameCalculator.getDamagedFields();
        output = gameCalculator.repackToSpielzug();
        filteredList.clear();
        input.clear();
    }

    public void monsterCalc () {
        for(int i = 0; i< serverModel.getMonsters().size(); i++) {
            if(serverModel.getMonsters().get(i).isAlive()) {
                SpielzugMessage monsterSpielzug = serverModel.getMonsters().get(i).think();
                input.add(monsterSpielzug);
            }else{
        }
        }

    }

    public SpielzugMessage[] getOutput(){
        return output;
    }

    public void addSpielzug(SpielzugMessage zug){
        input.add(zug);
    }

    public GameFilter getGameFilter() {
        return gameFilter;
    }

    public void setOutput(SpielzugMessage[] output) {
        this.output = output;
    }

    public void setGameFilter(GameFilter gameFilter) {
        this.gameFilter = gameFilter;
    }

    public GameCalculator getGameCalculator() {
        return gameCalculator;
    }

    public void setGameCalculator(GameCalculator gameCalculator) {
        this.gameCalculator = gameCalculator;
    }

    public ServerModel getServerModel() {
        return serverModel;
    }

    public void setServerModel(ServerModel serverModel) {
        this.serverModel = serverModel;
    }

    public List<SpielzugMessage> getInput() {
        return input;
    }

    public void setInput(List<SpielzugMessage> input) {
        this.input = input;
    }

    public List<ActingInformation> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<ActingInformation> filteredList) {
        this.filteredList = filteredList;
    }

    public List<ActingInformation> getDamagedFields() {
        return damagedFields;
    }
}
