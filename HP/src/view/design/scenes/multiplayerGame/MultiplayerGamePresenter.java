package view.design.scenes.multiplayerGame;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import master.GameMaster;
import model.ClientModel;
import network.client.communication.threads.OutputHandler;
import network.messages.LeaveGameMessage;
import network.messages.SpielzugMessage;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.design.MainPresenter;
import view.design.scenes.MusicPlayer;
import view.design.scenes.singleplayerGame.SinglePlayerGameOver;
import view.design.scenes.singleplayerGame.SinglePlayerGameWon;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Adrian on 07.02.2016.
 */
public class MultiplayerGamePresenter {

    private static final Logger logger = LoggerFactory.getLogger(MultiplayerGamePresenter.class);
    String chosenClass;
    private MusicPlayer mediaplayer = new MusicPlayer();
    public static GameMaster master;
    public final SimpleStringProperty action = new SimpleStringProperty("move");
    public final SimpleStringProperty dir = new SimpleStringProperty("s");
    public static ClientModel serverModel;
    private OutputHandler outputHandler;

    private SinglePlayerGameOver singlePlayerGameOver;
    private SinglePlayerGameWon singlePlayerGameWon;

    MultiplayerGameScene singleplayerGameScene;
    List<SpielzugMessage> spielzüge = new LinkedList<SpielzugMessage>();
    int moveCount;
    private MainPresenter mainPresenter;
    private boolean notyetSubmitted = true;
    private int gameID;



    public MultiplayerGamePresenter(MainPresenter mainPresenter, ClientModel clientModel) {
        this.serverModel = clientModel;
        this.mainPresenter = mainPresenter;
        this.outputHandler = mainPresenter.getClient().getCom().getSend();
        startGame(serverModel.getSelfPlayer().getKlasse());
        moveCount=0;
        addListener();
        chatButtonHandle();
        chatShowButton();
        infoShowButton();
        singlePlayerGameOver = new SinglePlayerGameOver();
        singlePlayerGameWon = new SinglePlayerGameWon();
    }

    private void onKeyTyped(KeyEvent e) throws IOException {

        KeyCode code;
        code = e.getCode();
        if(code.equals(KeyCode.T)){singleplayerGameScene.showChat();}
        if(code.equals(KeyCode.ESCAPE)){singleplayerGameScene.hideChat();}
        if(code.equals(KeyCode.ENTER)){submitPressed();}
        if(code.equals(KeyCode.DIGIT1)){action.set("move");}
        if(code.equals(KeyCode.DIGIT2)){action.set("use");}
        if(code.equals(KeyCode.DIGIT3)){action.set("attackMe");}

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

    private void chatShowButton(){
        singleplayerGameScene.chatShow.setOnAction(event -> {
            singleplayerGameScene.showChat();
        });
    }

    private void chatButtonHandle() {
        singleplayerGameScene.chatSend.setOnAction(e -> {
            String msg = singleplayerGameScene.getChatFieldInput().getText();
            String client = mainPresenter.getClient().getNick();
            try {
                mainPresenter.getClient().getCom().getSend().sendJSON(mainPresenter.getClient().getCom().getSend().getEncoder().chatMessage(msg,client));
                singleplayerGameScene.getChatFieldInput().clear();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        });
    }


    public void startGame(String chosenClass) {
        this.singleplayerGameScene = new MultiplayerGameScene(serverModel, this);
        mainPresenter.getScenesmap().put("multiplayerGameScene", singleplayerGameScene.getActualScene());
        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("multiplayerGameScene"));

        singleplayerGameScene.getActualScene().setOnKeyPressed(e -> {
            try {
                onKeyTyped(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        singleplayerGameScene.potImage.setOnAction(e ->{
            this.action.set("use");
            singleplayerGameScene.maFire.setVisible(false);
            singleplayerGameScene.maIce.setVisible(false);
            singleplayerGameScene.maWind.setVisible(false);
            this.dir.set("g");
        });

        singleplayerGameScene.move.setOnAction(e -> {
            this.action.set("move");
            singleplayerGameScene.maFire.setVisible(false);
            singleplayerGameScene.maIce.setVisible(false);
            singleplayerGameScene.maWind.setVisible(false);
            });

        singleplayerGameScene.use.setOnAction(e -> {
            this.action.set("use");
            singleplayerGameScene.maFire.setVisible(false);
            singleplayerGameScene.maIce.setVisible(false);
            singleplayerGameScene.maWind.setVisible(false);
            });

        singleplayerGameScene.melee.setOnAction(e -> {
            this.action.set("attackMe");
            singleplayerGameScene.maFire.setVisible(false);
            singleplayerGameScene.maIce.setVisible(false);
            singleplayerGameScene.maWind.setVisible(false);
            });
        singleplayerGameScene.range.setOnAction(e -> {
            this.action.set("attackRa");
            singleplayerGameScene.maFire.setVisible(false);
            singleplayerGameScene.maIce.setVisible(false);
            singleplayerGameScene.maWind.setVisible(false);
        });

        singleplayerGameScene.magic.setOnAction(e -> {
            singleplayerGameScene.maFire.setVisible(true);
            singleplayerGameScene.maIce.setVisible(true);
            singleplayerGameScene.maWind.setVisible(true);
        });
        singleplayerGameScene.maFire.setOnAction(e -> {
            this.action.set("attackFire");
        });

        singleplayerGameScene.maIce.setOnAction(e -> {
            this.action.set("attackIce");
        });

        singleplayerGameScene.maWind.setOnAction(e -> {
            this.action.set("attackWind");
        });

        singleplayerGameScene.exit.setOnAction(e ->{
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("serverListScene"));
            Gson gson = new Gson();
            String message = gson.toJson(new LeaveGameMessage(getGameID(), serverModel.getSelfPlayer().getNick()));
            try {
                outputHandler.sendJSON(message);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        });
    }


    private void submitPressed() {
        spielzüge.clear();
        String nick = serverModel.getSelfPlayer().getNick();
        switch (action.get()) {
            case "move":
                mediaplayer.playWalk();
                spielzüge.add(new SpielzugMessage(nick, "move", dir.get()));break;
            case "use":
                spielzüge.add(new SpielzugMessage(nick, "use", dir.get()));break;
            case "attackMe":
                spielzüge.add(new SpielzugMessage(nick, "attackMe", dir.get()));break;
            case "attackFire":
                spielzüge.add(new SpielzugMessage(nick, "attackMa", dir.get()));
                break;
            case "attackRa":
                spielzüge.add(new SpielzugMessage(nick, "attackRa", dir.get()));break;
            case "attackIce":
                spielzüge.add(new SpielzugMessage(nick, "attackIce", dir.get()));
                break;
            case "attackWind": {
                spielzüge.add(new SpielzugMessage(nick, "attackWind", dir.get()));
                break;
            }
            default: spielzüge.add(new SpielzugMessage(nick, "wait", "s"));break;
        }
        try {
            //if (hasChosen && !sent) {
            //    sent = true;
            if(notyetSubmitted) {
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
                        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("GameOverScene"));
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

                        //TODO: GameWon Screen laden
                        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("GameWonScene"));
                    }
                });

                return null;
            }

        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public boolean isNotyetSubmitted() {
        return notyetSubmitted;
    }

    public void setNotyetSubmitted(boolean notyetSubmitted) {
        this.notyetSubmitted = notyetSubmitted;
    }

    public MultiplayerGameScene getMultiplayerGameScene() {
        return singleplayerGameScene;
    }

    public void setMultiplayerGameScene(MultiplayerGameScene singleplayerGameScene) {
        this.singleplayerGameScene = singleplayerGameScene;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}

