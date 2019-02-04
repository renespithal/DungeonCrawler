package view.designHP.scenes.mainMenuHP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.ViewUtilsHP;

/**
 * Created by Jenny on 09.01.2016.
 */
public class MainMenuSceneHP extends BorderPane {

    private Scene scene;
    private HBox menu, logo;

    private Button exit, sPlayer, mPlayer, server, play, pause;
    private VBox sP, mP, sE, eX;

    public MainMenuSceneHP() {
        setPositions();
        this.scene = ViewUtilsHP.createScene(this);
        ViewUtilsHP.setBackground(this, "hp/media/background/mainBG.jpg");
        ViewUtilsHP.configureCSS(scene);
        
        scene.getRoot().setId("menuScene");
    }

    private void setPositions() {
    	this.setBottom(createButtons());
    }

    /* Create */
    private Pane createButtons() {
    	
    	Pane pane = new Pane();
        sPlayer = ViewUtilsHP.createButton("Singleplayer");
        sP = ViewUtilsHP.buttonSurrounding(sPlayer);
        mPlayer = ViewUtilsHP.createButton("Multiplayer");
        mP = ViewUtilsHP.buttonSurrounding(mPlayer);
        server = ViewUtilsHP.createButton("Start Server");
        sE = ViewUtilsHP.buttonSurrounding(server);
        exit = ViewUtilsHP.createButton("Exit");
        eX = ViewUtilsHP.buttonSurrounding(exit);

        HBox hBox1 = new HBox(sP,mP);
        HBox hBox2 = new HBox(sE,eX);
        hBox1.setSpacing(100);
        hBox2.setSpacing(100);
        HBox hBox = new HBox(hBox1,hBox2);
        hBox.setSpacing(280);
        hBox.setPadding(new Insets(0,0,10,100));
        hBox.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(hBox);

        return pane;
    }

    private Button createPlay() {
        play = ViewUtilsHP.newButton("", "play");
        return play;
    }

    private Button createPause() {
        pause = ViewUtilsHP.createPlayButton();
        return pause;
    }

    /* Getter & Setter */
    public Scene getMainMenuScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public BorderPane getRoot() {
        return this;
    }

    public HBox getMenu() {
        return menu;
    }

    public void setMenu(HBox menu) {
        this.menu = menu;
    }

    public HBox getLogo() {
        return logo;
    }

    public void setLogo(HBox logo) {
        this.logo = logo;
    }

    public Button getExit() {
        return exit;
    }

    public void setExit(Button exit) {
        this.exit = exit;
    }

    public Button getsPlayer() {
        return sPlayer;
    }

    public void setsPlayer(Button sPlayer) {
        this.sPlayer = sPlayer;
    }

    public Button getmPlayer() {
        return mPlayer;
    }

    public void setmPlayer(Button mPlayer) {
        this.mPlayer = mPlayer;
    }

    public Button getServer() {
        return server;
    }

    public void setServer(Button server) {
        this.server = server;
    }
}
