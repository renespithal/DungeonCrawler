package view.design.scenes.multiplayerGame;

import com.google.gson.Gson;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import master.GameMaster;
import model.ClientModel;
import network.client.communication.threads.OutputHandler;
import network.messages.SpielzugMessage;
import org.json.JSONException;
import view.design.MainPresenter;
import view.design.scenes.MusicPlayer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by guter Felix on 10.12.2015.
 */
public class MultiplayerGamePresenter3 {

    String chosenClass;
    private MusicPlayer mediaplayer = new MusicPlayer();
    public static GameMaster master;
    public final SimpleStringProperty action = new SimpleStringProperty("move");
    public final SimpleStringProperty dir = new SimpleStringProperty("s");
    public static ClientModel serverModel;
    Background attackMeLargeOrg;
    Background attackMeLargeSel;
    Background attackMeOrg;
    Background attackMeSel;
    Background attackMaOrg;
    Background attackMaSel;
    Background attackRaOrg;
    Background attackRaSel;
    Background moveOrg;
    Background moveSel;
    Background useOrg;
    Background useSel;
    Background submitLargeOrg;
    Background submitLargeSel;
    MultiplayerGameScene3 singleplayerGameScene;
    List<SpielzugMessage> spielzüge = new LinkedList<SpielzugMessage>();
    int moveCount;
    private MainPresenter mainPresenter;
    private Timeline time;
    public final SimpleDoubleProperty secondCount = new SimpleDoubleProperty(100);
    private Background exitOrg;
    private Background exitSel;
    private OutputHandler outputHandler;
    private boolean notyetSubmitted = true;

    public MultiplayerGamePresenter3(MainPresenter mainPresenter, ClientModel clientModel) {
        this.serverModel = clientModel;
        this.mainPresenter = mainPresenter;
        this.outputHandler = mainPresenter.getClient().getCom().getSend();
        startGame("warrior");
        moveCount = 0;


    }


    private void onKeyTyped(KeyEvent e) throws IOException {

        KeyCode code;
        code = e.getCode();

        if (code.equals(KeyCode.DIGIT1)) {
            buttonClicked("move");
        }
        if (code.equals(KeyCode.DIGIT2)) {
            buttonClicked("use");
        }
        if (code.equals(KeyCode.DIGIT3)) {
            buttonClicked("attackMe");

        }

        if (code.equals(KeyCode.ENTER)) {
            submitPressed();
        }
        ;

        if (code.equals(KeyCode.LEFT) || code.equals(KeyCode.A)) {
            dir.set("a");
        } else if (code.equals(KeyCode.RIGHT) || code.equals(KeyCode.D)) {
            dir.set("d");
        } else if (code.equals(KeyCode.UP) || code.equals(KeyCode.W)) {
            dir.set("w");
        } else if (code.equals(KeyCode.DOWN) || code.equals(KeyCode.S)) {
            dir.set("s");
        }
    }

    public void calc() throws IOException {
        if (secondCount.get() == 0) {
            singleplayerGameScene.submit.setDisable(false);
            singleplayerGameScene.submit.setBackground(submitLargeOrg);
            if (!spielzüge.isEmpty()) {
                master.addSpielzug(spielzüge.get(0));
            }
            singleplayerGameScene.paintEffects = true;
            master.calculateEverything();
            spielzüge.clear();
            secondCount.set(100);
        } else {
            if (secondCount.get() == 85) {
                singleplayerGameScene.paintEffects = false;
                serverModel.setMapChange(serverModel.getMapChange() + 1);
                secondCount.set(secondCount.get() - 1);
            } else secondCount.set(secondCount.get() - 1);
        }
    }

    public void startGame(String chosenClass) {
        singleplayerGameScene = new MultiplayerGameScene3(serverModel, this);
        mainPresenter.getScenesmap().put("multiplayerGameScene", singleplayerGameScene.getActualScene());
        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("multiplayerGameScene"));
        //    mainPresenter.getStage().setFullScreen(true);


        attackMeLargeOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.meleeButtonLargePattern, CornerRadii.EMPTY, Insets.EMPTY));
        attackMeLargeSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.meleeButtonLargePatternSelected, CornerRadii.EMPTY, Insets.EMPTY));
        attackMeOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.meleeButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY));
        attackMeSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.meleeButtonSmallPatternSelected, CornerRadii.EMPTY, Insets.EMPTY));
        attackMaOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.magicButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY));
        attackMaSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.magicButtonSmallPatternSelected, CornerRadii.EMPTY, Insets.EMPTY));
        attackRaOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.rangeButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY));
        attackRaSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.rangeButtonSmallPatternSelected, CornerRadii.EMPTY, Insets.EMPTY));
        moveOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.moveButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY));
        moveSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.moveButtonSmallPatternSelected, CornerRadii.EMPTY, Insets.EMPTY));
        useOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.useButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY));
        useSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.useButtonSmallPatternSelected, CornerRadii.EMPTY, Insets.EMPTY));
        submitLargeOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.submitButtonLargePattern, CornerRadii.EMPTY, Insets.EMPTY));
        submitLargeSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.submitButtonLargePatternSelected, CornerRadii.EMPTY, Insets.EMPTY));
        exitOrg = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.exitButtonPattern, CornerRadii.EMPTY, Insets.EMPTY));
        exitSel = new Background(new BackgroundFill(singleplayerGameScene.imageHolder.exitButtonPatternSelected, CornerRadii.EMPTY, Insets.EMPTY));


        singleplayerGameScene.exit.setOnAction(e -> exit());
        singleplayerGameScene.exit.setOnMouseEntered(e -> exitEntered());
        singleplayerGameScene.exit.setOnMouseExited(e -> exitExited());
        singleplayerGameScene.potionButton.setOnAction(e -> buttonClicked("potionButton"));
        singleplayerGameScene.submit.setOnAction(e -> submitPressed());
        singleplayerGameScene.submit.setOnMouseEntered(e -> singleplayerGameScene.submit.setBackground(submitLargeSel));
        singleplayerGameScene.submit.setOnMouseExited(e -> {
            if (singleplayerGameScene.submit.getId().equals("0")) {
                singleplayerGameScene.submit.setBackground(submitLargeOrg);
            }
        });
        singleplayerGameScene.move.setOnAction(e -> buttonClicked("move"));
        singleplayerGameScene.move.setOnMouseEntered(e -> singleplayerGameScene.move.setBackground(moveSel));
        singleplayerGameScene.move.setOnMouseExited(e -> {
            if (singleplayerGameScene.move.getId().equals("0")) {
                singleplayerGameScene.move.setBackground(moveOrg);
            }
        });
        singleplayerGameScene.use.setOnAction(e -> buttonClicked("use"));
        singleplayerGameScene.use.setOnMouseEntered(e -> singleplayerGameScene.use.setBackground(useSel));
        singleplayerGameScene.use.setOnMouseExited(e -> {
            if (singleplayerGameScene.use.getId().equals("0")) {
                singleplayerGameScene.use.setBackground(useOrg);
            }
        });
        singleplayerGameScene.attackMe.setOnAction(e -> buttonClicked("attackMe"));
//        singleplayerGameScene.attackMe.setOnMouseEntered(e -> singleplayerGameScene.attackMe.setBackground(rightAttackButtonBackground("sel")));
        singleplayerGameScene.attackMe.setOnMouseExited(e -> {
            if (singleplayerGameScene.attackMe.getId().equals("0")) {
        //        singleplayerGameScene.attackMe.setBackground(rightAttackButtonBackground("org"));
            }
        });
        singleplayerGameScene.attackMa.setOnAction(e -> {
            buttonClicked("attackMa");
        });
        singleplayerGameScene.attackMa.setOnMouseEntered(e -> singleplayerGameScene.attackMa.setBackground(attackMaSel));
        singleplayerGameScene.attackMa.setOnMouseExited(e -> {
            if (singleplayerGameScene.attackMa.getId().equals("0")) {
                singleplayerGameScene.attackMa.setBackground(attackMaOrg);
            }
        });
        singleplayerGameScene.attackRa.setOnAction(e -> buttonClicked("attackRa"));
        singleplayerGameScene.attackRa.setOnMouseEntered(e -> singleplayerGameScene.attackRa.setBackground(attackRaSel));
        singleplayerGameScene.attackRa.setOnMouseExited(e -> {
            if (singleplayerGameScene.attackRa.getId().equals("0")) {
                singleplayerGameScene.attackRa.setBackground(attackRaOrg);
            }
        });
        singleplayerGameScene.setOnKeyPressed(e -> {
            try {
                onKeyTyped(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }

    private void exitExited() {
        singleplayerGameScene.exit.setBackground(exitOrg);
    }

    private void exitEntered() {
        singleplayerGameScene.exit.setBackground(exitSel);
    }

    private void exit() {
        serverModel = null;
        master = null;
        secondCount.set(100);
        mediaplayer.stopLevel1();
        mediaplayer.stopLevel2();
        mediaplayer.stopLevel3();
        mediaplayer.playIntro();
        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("singlePlayerGameLobbyScene"));
    }


    private void buttonClicked(String name) {
        singleplayerGameScene.attackRa.setId("0");
        // singleplayerGameScene.attackRa.setBackground(attackRaOrg);
        singleplayerGameScene.attackMe.setId("0");
        //singleplayerGameScene.attackMe.setBackground(rightAttackButtonBackground("org"));
        singleplayerGameScene.move.setId("0");
        //singleplayerGameScene.move.setBackground(moveOrg);
        singleplayerGameScene.attackMa.setId("0");
        //singleplayerGameScene.attackMa.setBackground(attackMaOrg);
        singleplayerGameScene.use.setId("0");
        //singleplayerGameScene.use.setBackground(useOrg);
        singleplayerGameScene.submit.setId("0");
        //singleplayerGameScene.submit.setBackground(submitLargeOrg);
        singleplayerGameScene.potionButton.setEffect(null);
        switch (name) {
            //System.out.println(name + "Im switch caseXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            case "submit":
                singleplayerGameScene.submit.setBackground(submitLargeSel);
                singleplayerGameScene.submit.setId("1");
                break;
            case "attackRa":
                singleplayerGameScene.attackRa.setBackground(attackRaSel);
                singleplayerGameScene.attackRa.setId("1");
                this.action.set("attackRa");
                this.action.setValue("attackRa");
                break;
            case "attackMa":
                singleplayerGameScene.attackMa.setBackground(attackMaSel);
                singleplayerGameScene.attackMa.setId("1");
                this.action.set("attackMa");
                this.action.setValue("attackMa");
                break;
            case "attackMe":
                  singleplayerGameScene.attackMe.setBackground(rightAttackButtonBackground("sel"));
                singleplayerGameScene.attackMe.setId("1");
                this.action.set("attackMe");
                this.action.setValue("attackMe");
                break;
            case "move":
                singleplayerGameScene.move.setBackground(moveSel);
                singleplayerGameScene.move.setId("1");
                this.action.set("move");
                this.action.setValue("move");
                break;
            case "use":
                singleplayerGameScene.use.setBackground(useSel);
                singleplayerGameScene.use.setId("1");
                this.action.set("use");
                this.action.setValue("use");
                break;
            case "potionButton":
                singleplayerGameScene.use.setBackground(useSel);
                singleplayerGameScene.use.setId("1");
                this.action.set("use");
                this.dir.set("g");
        }
    }

    private Background rightAttackButtonBackground(String orgOrSel) {

        Background res = null;

        if (chosenClass.equals("warrior")) {
            switch (orgOrSel) {
                case "org":
                    res = attackMeLargeOrg;
                    break;
                case "sel":
                    res = attackMeLargeSel;
                    break;
            }
        } else
            switch (orgOrSel) {
                case "org":
                    res = attackMeOrg;
                    break;
                case "sel":
                    res = attackMeSel;
                    break;
            }

        return res;
    }

    private void submitPressed() {


        singleplayerGameScene.submit.setBackground(submitLargeSel);
        //    singleplayerGameScene.submit.setDisable(true);
        spielzüge.clear();

        switch (action.getValue()) {
            case "move":
                mediaplayer.playWalk();
                spielzüge.add(new SpielzugMessage(mainPresenter.getClient().getCom().getRcv().getClientDecoder().
                        getClientModel().getSelfPlayer().getNick(), "move", dir.get()));
                break;
            case "use":
                spielzüge.add(new SpielzugMessage(mainPresenter.getClient().getCom().getRcv().getClientDecoder().
                        getClientModel().getSelfPlayer().getNick(), "use", dir.get()));
                break;
            case "attackMe":
                spielzüge.add(new SpielzugMessage(mainPresenter.getClient().getCom().getRcv().getClientDecoder().getClientModel().getSelfPlayer().getNick(), "attackMe", dir.get()));
                break;
            case "attackMa":
                spielzüge.add(new SpielzugMessage(mainPresenter.getClient().getCom().getRcv().getClientDecoder().getClientModel().getSelfPlayer().getNick(), "attackMa", dir.get()));
                break;
            case "attackRa":
                spielzüge.add(new SpielzugMessage(mainPresenter.getClient().getCom().getRcv().getClientDecoder().getClientModel().getSelfPlayer().getNick(), "attackRa", dir.get()));
                break;
        }
        secondCount.set(0);
        try {
            //if (hasChosen && !sent) {
            //    sent = true;
            if(notyetSubmitted) {
                notyetSubmitted = false;
                Gson gson = new Gson();
                String message = gson.toJson(spielzüge.get(0));
                outputHandler.sendJSON(message);
                System.out.println(this.action.getValue());
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }

            //  }else{}
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        secondCount.set(100);
    }

    public SimpleDoubleProperty getSecondCountProperty() {
        return secondCount;
    }

    public boolean isNotyetSubmitted() {
        return notyetSubmitted;
    }

    public void setNotyetSubmitted(boolean notyetSubmitted) {
        this.notyetSubmitted = notyetSubmitted;
    }
}
