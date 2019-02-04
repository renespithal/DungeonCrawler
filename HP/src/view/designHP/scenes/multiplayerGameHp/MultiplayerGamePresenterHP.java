package view.designHP.scenes.multiplayerGameHp;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import master.GameMaster;
import model.ClientModel;
import model.ServerModel;
import model.entity.Player;
import model.entity.player.playersHP.*;
import network.client.communication.threads.OutputHandler;
import network.messages.SpielzugMessage;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Chat;
import view.design.scenes.MusicPlayer;
import view.designHP.presenter.MainPresenterHP;
import view.designHP.scenes.gameOverHP.GameOverPresenterHP;
import view.designHP.scenes.singleplayerGameHP.ImageHolderHP;
import view.designHP.scenes.singleplayerGameHP.SinglePlayerGameWon;
import view.designHP.scenes.singleplayerGameHP.SingleplayerGameSceneHP;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Adrian on 17.02.2016.
 */
public class MultiplayerGamePresenterHP {
    private static final Logger logger = LoggerFactory.getLogger(MultiplayerGamePresenterHP.class);

    private SingleplayerGameSceneHP singlePlayerGameScene;
    String chosenClass;
    String selButton = "wait";
    private MusicPlayer mediaplayer = new MusicPlayer();
    public static GameMaster master;
    public final SimpleStringProperty action = new SimpleStringProperty("wait");
    public final SimpleStringProperty dir = new SimpleStringProperty("s");
    public static ClientModel serverModel;
    private boolean notyetSubmitted = true;
    private OutputHandler outputHandler;
    private int gameID;
    private Chat chat;

    MultiplayerGameSceneHP singleplayerGameScene;
    List<SpielzugMessage> spielzüge = new LinkedList<SpielzugMessage>();
    int moveCount;
    private MainPresenterHP mainPresenter;
    ImageHolderHP2 imageHolder = new ImageHolderHP2();


    public MultiplayerGamePresenterHP(MainPresenterHP mainPresenter, ClientModel serverModel) {
        this.serverModel = serverModel;
        this.chat = new Chat();
        this.mainPresenter = mainPresenter;
        this.outputHandler = mainPresenter.getClient().getCom().getSend();
        startGame(serverModel.getSelfPlayer().getKlasse());
        addListener();
        moveCount = 0;
    }

    public HBox posChat() {
        HBox vBox = new HBox();

        vBox.getChildren().addAll(getChat().getAll(), getChat().getIcon());
        vBox.setPadding(new Insets(150,-260,35,5));

        getChat().getInput().setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                String msg = getChat().getInput().getText();
                String client = mainPresenter.getClient().getNick();
                try {
                    mainPresenter.getClient().getCom().getSend().sendJSON(mainPresenter.getClient().getCom().getSend().getEncoder().chatMessage(msg,client));
                    getChat().getInput().clear();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });

        getChat().getAll().setVisible(true);

        return vBox;
    }


    private void onKeyTyped(KeyEvent e) throws IOException {

        KeyCode code;
        code = e.getCode();

        if (code.equals(KeyCode.ENTER)) {
            submitPressed();
        }
        if (code.equals(KeyCode.DIGIT1)) {
            action.set("move");
        }
        if (code.equals(KeyCode.DIGIT2)) {
            action.set("use");
        }
        if (code.equals(KeyCode.DIGIT3)) {
            action.set("attackMa");
        }

        if (code.equals(KeyCode.LEFT) || code.equals(KeyCode.A)) {
            dir.set("a");
            singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        } else if (code.equals(KeyCode.RIGHT) || code.equals(KeyCode.D)) {
            dir.set("d");
            singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        } else if (code.equals(KeyCode.UP) || code.equals(KeyCode.W)) {
            dir.set("w");
            singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        } else if (code.equals(KeyCode.DOWN) || code.equals(KeyCode.S)) {
            dir.set("s");
            singleplayerGameScene.repaintSight(singleplayerGameScene.sight);
        }
    }


    public void startGame(String chosenClass) {
        this.singleplayerGameScene = new MultiplayerGameSceneHP(serverModel, this);
        mainPresenter.getScenesMap().put("multiplayerGameSceneHP", singleplayerGameScene.getScene());
        mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("multiplayerGameSceneHP"));


        singleplayerGameScene.getScene().setOnKeyPressed(e -> {
            try {
                onKeyTyped(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        singleplayerGameScene.potImage.setOnAction(e -> {
            this.action.set("use");
            this.dir.set("g");
            buttonPressed("use");
        });

        singleplayerGameScene.move.setOnAction(e -> {
            this.action.set("move");
            System.out.println("MOVE");
            buttonPressed("move");
        });

        singleplayerGameScene.use.setOnAction(e -> {
            this.action.set("use");
            System.out.println("USE");
            buttonPressed("use");
        });

        singleplayerGameScene.magic.setOnAction(e -> {
            this.action.set("attackMa");
            buttonPressed("attackMa");
        });

        singleplayerGameScene.exit.setOnAction(e -> {
            mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("singlePlayerGameLobbyScene"));
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
                spielzüge.add(new SpielzugMessage(serverModel.getSelfPlayer().getNick(), "move", dir.get()));
                break;
            case "use":
                spielzüge.add(new SpielzugMessage(serverModel.getSelfPlayer().getNick(), "use", dir.get()));
                break;
            case "attackMe":
                spielzüge.add(new SpielzugMessage(serverModel.getSelfPlayer().getNick(), "attackMe", dir.get()));
                break;
            case "attackMa":
                spielzüge.add(new SpielzugMessage(serverModel.getSelfPlayer().getNick(), "attackMa", dir.get()));
                break;
            case "attackRa":
                spielzüge.add(new SpielzugMessage(serverModel.getSelfPlayer().getNick(), "attackRa", dir.get()));
                break;
            default:
                spielzüge.add(new SpielzugMessage(serverModel.getSelfPlayer().getNick(), "wait", "s"));
                break;
        }
        try {
            //if (hasChosen && !sent) {
            //    sent = true;
            if (notyetSubmitted) {
                notyetSubmitted = false;
                Gson gson = new Gson();
                spielzüge.get(0).setGameID(gameID);
                String message = gson.toJson(spielzüge.get(0));
                outputHandler.sendJSON(message);
            }

            //  }else{}
        } catch (JSONException e) {
            e.printStackTrace();
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

    public void addListener() {
        this.mainPresenter.getClient().getCom().getRcv().getClientDecoder().getPlayerDecoder().youreDeadProperty().addListener((observable, oldValue, newValue) -> showGameOverScreen());
        this.mainPresenter.getClient().getCom().getRcv().getClientDecoder().getPlayerDecoder().bossDeadProperty().addListener((observable, oldValue, newValue) -> showGameWonScreen());
    }

    public void showGameOverScreen() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //TODO: GameOver Screen laden
                        mainPresenter.setGameOverPresenterHP(new GameOverPresenterHP(mainPresenter));
                        mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("GameOverScene"));
                    }
                });

                return null;
            }

        };
        Thread thread = new Thread(task);
        thread.start();

    }

    public void showGameWonScreen() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mainPresenter.setGameOverPresenterHP(new GameOverPresenterHP(mainPresenter));
                        mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("GameOverScene"));

                    }
                });

                return null;
            }

        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public MultiplayerGameSceneHP getSingleplayerGameScene() {
        return singleplayerGameScene;
    }

    public void setSingleplayerGameScene(MultiplayerGameSceneHP singleplayerGameScene) {
        this.singleplayerGameScene = singleplayerGameScene;
    }

    public boolean isNotyetSubmitted() {
        return notyetSubmitted;
    }

    public void setNotyetSubmitted(boolean notyetSubmitted) {
        this.notyetSubmitted = notyetSubmitted;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

