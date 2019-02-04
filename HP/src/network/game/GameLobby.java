package network.game;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import master.GameMaster;
import model.ServerModel;
import model.entity.Player;
import model.entity.player.*;
import model.entity.player.playersHP.*;
import model.util.Field;
import network.messages.SpielInfo;
import network.messages.SpielerInfo;
import network.server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import util.ArrayConverter;

/**
 * Created by SchalkeFelix on 17.12.2015.
 */
public class GameLobby {

	private final IntegerProperty playersCount = new SimpleIntegerProperty(0);

    private Server server;
    private SimpleIntegerProperty gameID;
    private ObservableList<Player> playerList;
    private ObservableList<SpielerInfo> playerInfoList;
    private SimpleStringProperty gamestate;
    private int depth,difficulty;
    private PlayerLobbyConvert playerLobbyConvert;
    private boolean dumbbool = true;
    private ServerModel model;

    private SpielInfo spielInfo;
    private GameMaster master;

    private boolean visible;
    private Timeline timeline;
    private int gameIDint;
    private boolean harry;

    public GameLobby(int gameId, String creatorClass, int depth, int difficulty, String nick, Server server, boolean harry) {
        this.harry = harry;
        this.gameIDint = gameId;
        this.server = server;
        this.playerList = FXCollections.observableArrayList();
        this.playerInfoList = FXCollections.observableArrayList();
        this.gameID = new SimpleIntegerProperty();
        this.gameID.set(gameId);
        this.gameID.setValue(gameId);
        this.depth = depth;
        this.difficulty = difficulty;
        this.gamestate = new SimpleStringProperty();
        this.gamestate.setValue("not started");
        this.playerLobbyConvert = new PlayerLobbyConvert();
        addPlayerInfo(nick, creatorClass);
        addPlayer(nick, creatorClass);
      
        
        SpielerInfo[] spielerInfoArray = ArrayConverter.spielerInfoToArray(playerInfoList);
        this.spielInfo = new SpielInfo(gameId, gamestate.getValue(), spielerInfoArray, depth, difficulty);
    }

    public void startGame() throws IOException, JSONException {
        model = new ServerModel(readFiles(), getPlayerList(), harry);
        master = new GameMaster(model);

        SpielerInfo[] spielerInfos = playerLobbyConvert.infoConvert(master.getServerModel().getPlayers(),master.getServerModel().getMonsters());
        String message = server.getEncoder().stateupdate(gameID.getValue(),spielerInfos,null,master.getServerModel().getMap());
        JSONObject jsonObject = new JSONObject(message);
        server.getCom().sendToGame(jsonObject);
        startTimeline();

    }
    public void startTimeline() throws IOException, JSONException {
        KeyFrame frame = new KeyFrame(Duration.seconds(3.0), e -> {
            try {
                master.calculateEverything();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            SpielerInfo[] spielerInfos2 = playerLobbyConvert.infoConvert(master.getServerModel().getPlayers(), master.getServerModel().getMonsters());
            String message2 = server.getEncoder().stateupdate(gameID.getValue(), spielerInfos2, master.getOutput(), master.getServerModel().getMap());
            JSONObject jsonObject2 = new JSONObject();
            try {
                jsonObject2 = new JSONObject(message2);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                server.getCom().sendToGame(jsonObject2);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        });
        if(!dumbbool) {
            timeline.stop();
            master.calculateEverything();
            SpielerInfo[] spielerInfos = playerLobbyConvert.infoConvert(master.getServerModel().getPlayers(), master.getServerModel().getMonsters());
            String message = server.getEncoder().stateupdate(gameIDint, spielerInfos, master.getOutput(), master.getServerModel().getMap());
            JSONObject jsonObject = new JSONObject(message);
            server.getCom().sendToGame(jsonObject);
            this.timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(frame);

        }
        KeyFrame frame2 = new KeyFrame(Duration.seconds(10.0), e -> {
            try {
                master.calculateEverything();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            SpielerInfo[] spielerInfos2 = playerLobbyConvert.infoConvert(master.getServerModel().getPlayers(),master.getServerModel().getMonsters());
            String message2 = server.getEncoder().stateupdate(gameIDint, spielerInfos2, master.getOutput(), master.getServerModel().getMap());
            JSONObject jsonObject2 = new JSONObject();
            try {
                jsonObject2 = new JSONObject(message2);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                server.getCom().sendToGame(jsonObject2);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        });

        this.timeline = new Timeline(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        dumbbool = false;

    }

    public void endGame(String nick) {
        model = null;
        List<Player> p = playerList.stream().filter(c -> !c.getNick().equals(nick)).collect(Collectors.toList());
        playerList = (ObservableList<Player>) p;
        List<SpielerInfo> pI = playerInfoList.stream().filter(c -> !c.getName().equals(nick)).collect(Collectors.toList());
        playerInfoList = (ObservableList<SpielerInfo>) pI;
        int newPlayersCount = playersCount.intValue() - 1;
        playersCount.set(newPlayersCount);
        if(playerList.isEmpty()) {
            setVisible(false);
            playersCount.set(0);
        }
    }

    public List<File> readFiles() throws IOException {
        File level1 = new File(GameLobby.class.getClassLoader().getResource("level2.txt").getPath());
        if (!level1.exists()){
            throw new IOException("NOT FOUND");
        }

        File level2 =  new File(GameLobby.class.getClassLoader().getResource("level2.txt").getPath());
        if (!level2.exists()){
            throw new IOException("NOT FOUND");
        }
        //File level3 = new File(ConsoleTest.class.getClassLoader().getResource("level3.txt").getPath());

        List<File> result = new LinkedList<>();
        result.add(level1);
        result.add(level2);

        return result;
    }


    public void gatherPlayers(){}
    public void createGame(){

    }
    
    public Player playerConvert(String kind,String nick){
        Player res;
        switch (kind){
            case "mage": res = new Mage();
                break;
            case "warrior": res = new Warrior();
                break;
            case "student": res = new Student();
                break;
            case "ranger": res = new Ranger();
                break;
            case "tourist": res = new Tourist();
                break;
            case "professor": res = new Professor();
                break;
            case "harry": res = new Harry();
                break;
            case "ron": res = new Ron();
                break;
            case "hermine": res = new Hermine();
                break;
            case "ginny": res = new Ginny();
                break;
            case "neville": res = new Neville();
                break;
            case "moody": res = new Moody();
                break;
            case "snape": res = new Snape();
                break;
            case "hagrid": res = new Hagrid();
                break;
            case "dumbledore": res = new Dumbledore();
                break;
            default: res = new Mage();
            
        }
        res.setNick(nick);
        return res;
    }

    public void addPlayer(String nick, String charClass) {
        playerList.add(playerConvert(charClass,nick)); /* TODO: Name wieder in Konstruktor */
        playersCount.set(playersCount.get() + 1);

//        spielInfo.setPlayersCount(spielInfo.getPlayersCount() + 1);

        spielInfo.setPlayersCount(spielInfo.getPlayersCount() + 1);
    }

    public void addPlayerInfo(String nick, String charClass) {
        SpielerInfo p = new SpielerInfo(nick, charClass,0,0); /* TODO: get default position */
        playerInfoList.add(p);
        SpielerInfo[] spielerinfoArray = ArrayConverter.spielerInfoToArray(playerInfoList);
        spielInfo = new SpielInfo(gameIDint, gamestate.getValue(), spielerinfoArray, depth, difficulty);
    }

    public int getGameID() {
        return gameID.get();
    }

    public SimpleIntegerProperty gameIDProperty() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID.setValue(gameID);
    }

    public ObservableList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ObservableList<Player> playerList) {
        this.playerList = playerList;
    }

    public List<SpielerInfo> getPlayerInfoList() {
        return playerInfoList;
    }
    
    public SpielerInfo[] getPlayerInfoListArray() {
    	return ArrayConverter.spielerInfoToArray(playerInfoList);
    }

    public void setPlayerInfoList(ObservableList<SpielerInfo> playerInfoList) {
        this.playerInfoList = playerInfoList;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getGamestate() {
        return gamestate.get();
    }

    public SimpleStringProperty gamestateProperty() {
        return gamestate;
    }

    public void setGamestate(String gamestate) {
        this.gamestate.set(gamestate);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public SpielInfo getSpielInfo() {
        return spielInfo;
    }

    public void setSpielInfo(SpielInfo spielInfo) {
        this.spielInfo = spielInfo;
    }

    public ServerModel getModel() {
        return model;
    }

    public void setModel(ServerModel model) {
        this.model = model;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public GameMaster getMaster() {
        return master;
    }

    public void setMaster(GameMaster master) {
        this.master = master;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public int getPlayersCount() {
        return playersCount.get();
    }

    public IntegerProperty playersCountProperty() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount.set(playersCount);
    }

}
