package view.design.scenes.singleplayerGame;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import master.GameMaster;
import model.ServerModel;
import model.Sight;
import model.entity.Player;
import model.entity.player.*;
import network.messages.SpielzugMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.design.MainPresenter;
import view.design.scenes.MusicPlayer;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by guter Felix on 10.12.2015.
 */
public class SingleplayerGamePresenter {

    private static final Logger logger = LoggerFactory.getLogger(SingleplayerGamePresenter.class);
    String chosenClass;
    String selButton = "wait";
    private MusicPlayer mediaplayer = new MusicPlayer();
    public static GameMaster master;
    public final SimpleStringProperty action = new SimpleStringProperty("wait");
    public final SimpleStringProperty dir = new SimpleStringProperty("s");
    public static ServerModel serverModel;

    ImageHolder imageHolder = new ImageHolder();
    SingleplayerGameScene singleplayerGameScene;
    List<SpielzugMessage> spielzüge = new LinkedList<SpielzugMessage>();
    int moveCount;
    private MainPresenter mainPresenter;


    public SingleplayerGamePresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
        mainPresenter.getScenesList().add(null);
        moveCount=0;
    }

    private void createModel(String chosenClass) {
        this.chosenClass=chosenClass;
        Player player = null;
        switch(chosenClass){
            case "ranger": player = new Ranger();break;
            case "professor": player = new Professor();break;
            case "warrior": player = new Warrior();break;
            case "mage": player = new Mage();break;
            case "tourist": player = new Tourist();break;
            case "student": player = new Student();break;
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
            serverModel = new ServerModel(levels, players, false);
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
            file = new File(res.getFile());}
        return file;
    }

    private void onKeyTyped(KeyEvent e) throws IOException {

        KeyCode code;
        code = e.getCode();

        if(code.equals(KeyCode.I)){singleplayerGameScene.showInfo();}
        if(code.equals(KeyCode.ESCAPE)){singleplayerGameScene.hideInfo();}
        if(code.equals(KeyCode.ENTER)){submitPressed();}
        if(code.equals(KeyCode.DIGIT1)){action.set("move");buttonPressed("move");}
        if(code.equals(KeyCode.DIGIT2)){action.set("use");buttonPressed("use");}
        if(code.equals(KeyCode.DIGIT3)){action.set("attackMe");buttonPressed("attackMe");}
        if(code.equals(KeyCode.DIGIT4)){action.set("attackRa");buttonPressed("attackRa");}
        if(code.equals(KeyCode.DIGIT5)){action.set("attackMa");buttonPressed("attackMa");}
        if(code.equals(KeyCode.DIGIT6)){action.set("attackWind");buttonPressed("attackWind");}
        if(code.equals(KeyCode.DIGIT7)){action.set("attackIce");buttonPressed("attackIce");}

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

    private void infoShowButton(){
        singleplayerGameScene.info.setOnAction(event -> {


            if(singleplayerGameScene.infoRect.isVisible()==false)
                singleplayerGameScene.showInfo();
            else
                singleplayerGameScene.hideInfo();
        });
    }



    public void startGame(String chosenClass) {
        createModel(chosenClass);
        this.singleplayerGameScene = new SingleplayerGameScene(serverModel, this);
        mainPresenter.getScenesList().add(0,singleplayerGameScene.getActualScene());
        mainPresenter.getScenesmap().put("singleplayerGameScene",singleplayerGameScene.getActualScene());
        mainPresenter.getScenesList().remove(0);

        master = new GameMaster(serverModel);

       singleplayerGameScene.getActualScene().setOnKeyPressed(e -> {
            try {
                onKeyTyped(e);
                infoShowButton();
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

        singleplayerGameScene.melee.setOnAction(e -> {
            this.action.set("attackMe");
            buttonPressed("attackMe");
        });
        singleplayerGameScene.range.setOnAction(e -> {
            this.action.set("attackRa");
            buttonPressed("attackRa");
        });

        singleplayerGameScene.magic.setOnAction(e -> {
            singleplayerGameScene.maFire.setVisible(true);
            singleplayerGameScene.maIce.setVisible(true);
            singleplayerGameScene.maWind.setVisible(true);
        });
        singleplayerGameScene.maFire.setOnAction(e -> {
           this.action.set("attackMa");
            buttonPressed("maFire");
            singleplayerGameScene.maFire.setVisible(true);
            singleplayerGameScene.maIce.setVisible(true);
            singleplayerGameScene.maWind.setVisible(true);
        });

        singleplayerGameScene.maIce.setOnAction(e -> {
            this.action.set("attackIce");
            buttonPressed("maIce");
            singleplayerGameScene.maFire.setVisible(true);
            singleplayerGameScene.maIce.setVisible(true);
            singleplayerGameScene.maWind.setVisible(true);
             });

        singleplayerGameScene.maWind.setOnAction(e -> {
            this.action.set("attackWind");
            buttonPressed("maWind");
            singleplayerGameScene.maFire.setVisible(true);
            singleplayerGameScene.maIce.setVisible(true);
            singleplayerGameScene.maWind.setVisible(true);
             });

        singleplayerGameScene.exit.setOnAction(e ->{
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("singlePlayerGameLobbyScene"));
        });

        singleplayerGameScene.gameOverScreen.restart.setOnAction(e ->{
                mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("singlePlayerGameLobbyScene"));
        });
        singleplayerGameScene.gameOverScreen.exitToMenu.setOnAction(e ->{
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
        });

        singleplayerGameScene.gameWonScreen.exitToMenu.setOnAction(e ->{
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
        });

        singleplayerGameScene.gameWonScreen.playAgain.setOnAction(e ->{
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));});


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
            case "attackIce":
                spielzüge.add(new SpielzugMessage("player" + moveCount, "attackIce", dir.get()));break;
            case "attackWind":
                spielzüge.add(new SpielzugMessage("player" + moveCount, "attackWind", dir.get()));break;
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
        singleplayerGameScene.maWind.setBackground(new Background(new BackgroundFill(imageHolder.windMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.maFire.setBackground(new Background(new BackgroundFill(imageHolder.fireMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.maIce.setBackground(new Background(new BackgroundFill(imageHolder.iceMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.melee.setBackground(new Background(new BackgroundFill(imageHolder.strPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.range.setBackground(new Background(new BackgroundFill(imageHolder.dexPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        singleplayerGameScene.maFire.setVisible(false);
        singleplayerGameScene.maIce.setVisible(false);
        singleplayerGameScene.maWind.setVisible(false);
        selButton=button;

        switch(button){
            case "maWind":  singleplayerGameScene.maWind.setBackground(new Background(new BackgroundFill(imageHolder.windMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "maFire":  singleplayerGameScene.maFire.setBackground(new Background(new BackgroundFill(imageHolder.fireMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "maIce":  singleplayerGameScene.maIce.setBackground(new Background(new BackgroundFill(imageHolder.iceMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "use":  singleplayerGameScene.use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "move":  singleplayerGameScene.move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "attackMe":  singleplayerGameScene.melee.setBackground(new Background(new BackgroundFill(imageHolder.strButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;
            case "attackRa":  singleplayerGameScene.range.setBackground(new Background(new BackgroundFill(imageHolder.dexButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));break;

        }
    }

}
