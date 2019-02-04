package view.designHP.scenes.singleplayerGameHP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import master.GameMaster;
import model.ServerModel;
import model.entity.Player;
import model.entity.player.playersHP.Dumbledore;
import model.entity.player.playersHP.Ginny;
import model.entity.player.playersHP.Hagrid;
import model.entity.player.playersHP.Harry;
import model.entity.player.playersHP.Hermine;
import model.entity.player.playersHP.Moody;
import model.entity.player.playersHP.Neville;
import model.entity.player.playersHP.Ron;
import model.entity.player.playersHP.Snape;
import network.messages.SpielzugMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.design.scenes.MusicPlayer;
import view.designHP.presenter.MainPresenterHP;

/**
 * Created by guter Felix on 10.12.2015.
 */
public class SingleplayerGamePresenterHP {

    private static final Logger logger = LoggerFactory.getLogger(SingleplayerGamePresenterHP.class);
    
    private SingleplayerGameSceneHP singlePlayerGameScene;
    String chosenClass;
    String selButton = "wait";
    private MusicPlayer mediaplayer = new MusicPlayer();
    public static GameMaster master;
    public final SimpleStringProperty action = new SimpleStringProperty("wait");
    public final SimpleStringProperty dir = new SimpleStringProperty("s");
    public static ServerModel serverModel;

    SingleplayerGameSceneHP singleplayerGameScene;
    List<SpielzugMessage> spielzüge = new LinkedList<SpielzugMessage>();
    int moveCount;
    private MainPresenterHP mainPresenter;
    ImageHolderHP imageHolder = new ImageHolderHP();


    public SingleplayerGamePresenterHP(MainPresenterHP mainPresenter){
        this.mainPresenter = mainPresenter;
        //mainPresenter.getScenesList().add(null);
        moveCount=0;
    }

    private void createModel(String chosenClass) {
    	this.chosenClass=chosenClass;
        Player player = null;
        switch(chosenClass){
            case "harry": player = new Harry();break;
            case "ron": player = new Ron();break;
            case "hermine": player = new Hermine();break;
            case "ginny": player = new Ginny(); break;
            case "neville": player = new Neville();break;
            case "hagrid": player = new Hagrid();break;
            case "dumbledore": player = new Dumbledore();break;
            case "moody": player = new Moody();break;
            case "snape": player = new Snape();break;
            default: player = new Harry();break;
        }

        try {
            List<File> levels = new LinkedList<>();
            File spLevel0;
            File spLevel1;
            File spLevel2;

            spLevel0 = load("spLevel0.txt");
            if (!spLevel0.exists()) {
                throw new IOException("NOT FOUND");
            }

            spLevel1 = load("spLevel1.txt");
            if (!spLevel1.exists()) {
                throw new IOException("NOT FOUND");
            }

            spLevel2 = load("spLevel2.txt");
            if (!spLevel2.exists()) {
                throw new IOException("NOT FOUND");
            }

            levels.add(spLevel0);
            levels.add(spLevel1);
            levels.add(spLevel2);
            List<Player> players= new LinkedList<>();
            players.add(player);
            serverModel = new ServerModel(levels, players, true);
        }
        catch(IOException e){
            logger.debug(e.getMessage());
        }
    }

    private File load(String string){
        File file = null;
        String resource = "/"+string ;
        URL res = getClass().getResource(resource);
        if (res.toString().startsWith("jar:")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);
                file = File.createTempFile(new Date().getTime()+"", ".html");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                input.close();
                file.deleteOnExit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            //this will probably work in your IDE, but not from a JAR
            file = new File(res.getFile());
        }
        return file;
    }

    private void onKeyTyped(KeyEvent e) throws IOException {

        KeyCode code;
        code = e.getCode();

        if(code.equals(KeyCode.ENTER)){submitPressed();}
        if(code.equals(KeyCode.DIGIT1)){action.set("move");}
        if(code.equals(KeyCode.DIGIT2)){action.set("use");}
        if(code.equals(KeyCode.DIGIT3)){action.set("attackMa");}

        if( code.equals(KeyCode.LEFT) || code.equals(KeyCode.A)) {
           dir.set("a"); singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        } else if( code.equals(KeyCode.RIGHT)|| code.equals(KeyCode.D) ) {
            dir.set("d");singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        } else if( code.equals(KeyCode.UP)|| code.equals(KeyCode.W) ) {
           dir.set("w");singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        } else if( code.equals(KeyCode.DOWN) || code.equals(KeyCode.S)) {
            dir.set("s");singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        }
    }


    public void startGame(String chosenClass) {
    	createModel(chosenClass);
        this.singleplayerGameScene = new SingleplayerGameSceneHP(serverModel, this);
        //mainPresenter.getScenesList().add(0,singleplayerGameScene.getActualScene());
        mainPresenter.getScenesMap().put("singlePlayerGameScene",singleplayerGameScene.getScene());
        //mainPresenter.getScenesList().remove(0);

        master = new GameMaster(serverModel);

       singleplayerGameScene.getScene().setOnKeyPressed(e -> {
            try {
                onKeyTyped(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        singleplayerGameScene.potImage.setOnAction(e ->{
            this.action.set("use");
            this.dir.set("g");
            buttonPressed("use");
        });

        singleplayerGameScene.move.setOnAction(e -> {
            this.action.set("move");
            buttonPressed("move");
        });

        singleplayerGameScene.use.setOnAction(e -> {
            this.action.set("use");
            buttonPressed("use");
        });


        singleplayerGameScene.magic.setOnAction(e -> {
            this.action.set("attackMa");
            buttonPressed("attackMa");
        });
        singleplayerGameScene.exit.setOnAction(e ->{
            mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("choosePlayerScene"));
        });

//        singleplayerGameScene.gameOverScreen.restart.setOnAction(e ->{
//                mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("singlePlayerGameLobbyScene"));
//        });
//        singleplayerGameScene.gameOverScreen.exitToMenu.setOnAction(e ->{
//            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
//        });


    }


    private void submitPressed() {
        spielzüge.clear();
        switch (action.get()) {
            case "move":
                mediaplayer.playWalk();
                spielzüge.add(new SpielzugMessage("player" + moveCount, "move", dir.get()));break;
            case "use":
                spielzüge.add(new SpielzugMessage("player" + moveCount, "use", dir.get()));break;
            case "attackMe":
                spielzüge.add(new SpielzugMessage("player" + moveCount, "attackMe", dir.get()));break;
            case "attackMa":
                spielzüge.add(new SpielzugMessage("player" + moveCount, "attackMa", dir.get()));break;
            case "attackRa":
                spielzüge.add(new SpielzugMessage("player" + moveCount, "attackRa", dir.get()));break;
            default: spielzüge.add(new SpielzugMessage("player"+moveCount, "wait", "s"));break;
        }
        master.addSpielzug(spielzüge.get(0));
        try {
            master.calculateEverything();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void buttonPressed(String button){
        singleplayerGameScene.magic.setBackground(new Background(new BackgroundFill(imageHolder.attackMaPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        selButton=button;

        switch(button){
            case "use":  singleplayerGameScene.use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "move":  singleplayerGameScene.move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "attackMa":  singleplayerGameScene.magic.setBackground(new Background(new BackgroundFill(imageHolder.attackMaSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
        }
    }
}
