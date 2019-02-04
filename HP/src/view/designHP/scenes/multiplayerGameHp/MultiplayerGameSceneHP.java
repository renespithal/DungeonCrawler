package view.designHP.scenes.multiplayerGameHp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.ClientModel;
import model.MiniMap;
import model.ServerModel;
import model.Sight;
import model.entity.Player;
import model.util.AttackInfo;
import model.util.Field;
import model.util.Position;
import network.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Chat;
import util.ViewUtils;
import util.ViewUtilsHP;
import view.design.scenes.MusicPlayer;
import view.design.scenes.multiplayerGame.MultiplayerGamePresenter;
import view.designHP.scenes.singleplayerGameHP.ImageHolderHP;
import view.designHP.scenes.singleplayerGameHP.SinglePlayerGameOver;
import view.designHP.scenes.singleplayerGameHP.SinglePlayerGameWon;
import view.designHP.scenes.singleplayerGameHP.SingleplayerGamePresenterHP;

import java.io.IOException;


/**
 * Created by Felix on 10.12.2015.
 */
public class MultiplayerGameSceneHP extends BorderPane {

    private static final Logger logger = LoggerFactory.getLogger(MultiplayerGameSceneHP.class);
    private Scene scene;
    private MiniMap miniMap;
    // Important constant Variables

    private MultiplayerGamePresenterHP presenter;
    private MusicPlayer mediaplayer = new MusicPlayer();
    private ClientModel serverModel;
    public ImageHolderHP2 imageHolder = new ImageHolderHP2();
    public Sight sight;


    // MainScreen
    GridPane sightGrid = new GridPane();
    AnchorPane mainAnchor;

    BorderPane menuPane = new BorderPane();
    GridPane miniMapGrid = new GridPane();

    Button miniMapButton = ViewUtilsHP.createButton("Mini Map");
    VBox vBoxMiniMap = ViewUtilsHP.buttonSurrounding(miniMapButton);
    Button exit;
    VBox vBoxExit;
//    Button chatButton = presenter.getChat().getIcon();

    HBox chat = new HBox();

    AnchorPane charAnchor = new AnchorPane();
    ProgressBar hpBar = new ProgressBar(1);
    ProgressBar xpBar = new ProgressBar(0);
    Label level = new Label("1");
    Circle charPicture;

    Button viewStats = ViewUtilsHP.createButton("View Stats");
    VBox viewStatsBox = ViewUtilsHP.buttonSurrounding(viewStats);
    GridPane stats = new GridPane();
    Rectangle weaImage = new Rectangle(65, 65);
    Button potImage = new Button("");
    Label name = new Label("Harry Potter");


    AnchorPane buttonAnchor = new AnchorPane();
    Arc arc1 = ViewUtils.newArc();
    Arc arc2 = ViewUtils.newArc();
    Button move = new Button("");
    Button use = new Button("");
    Button melee = new Button("");
    Button magic = new Button("");
    Button range = new Button("");
    Button maIce = new Button("");
    Button maWind = new Button("");
    Button maFire = new Button("");
    Button submit = new Button("Submit");

    Label infoSmall = new Label();
    Label dexInt = ViewUtils.newHSP("Dex:", 12);
    Label strInt = ViewUtils.newHSP("Str:", 12);
    Label wisInt = ViewUtils.newHSP("Wis:", 12);
    Label weaInt = ViewUtils.newHSP("No Weapon", 12);
    Label potInt = ViewUtils.newHSP("No Potion", 12);

    private boolean paintEffects = false;
    Timeline effects;
    SinglePlayerGameOver gameOverScreen = new SinglePlayerGameOver();
    SinglePlayerGameWon gameWonScreen = new SinglePlayerGameWon();
    private IntegerProperty integerProperty = new SimpleIntegerProperty();
    private IntegerProperty floorChangeProperty = new SimpleIntegerProperty();


    public MultiplayerGameSceneHP(ClientModel serverModel, MultiplayerGamePresenterHP presenter) {
        this.scene = ViewUtilsHP.createScene(this);
        mediaplayer.stopIntro();
        mediaplayer.playLevel1();
        this.presenter = presenter;
        this.serverModel = serverModel;
        miniMap = new MiniMap(serverModel, serverModel.getCurrentFloor().getXSize(), serverModel.getCurrentFloor().getYSize());
        miniMap.setFields(serverModel.getCurrentFloor().getXSize(), serverModel.getCurrentFloor().getYSize());

        sight = new Sight(serverModel.getCurrentFloor(), serverModel.getSelfPlayer().getPos(), serverModel);


        KeyFrame frame = new KeyFrame(Duration.seconds(3), e -> {
            paintEffects = false;
            repaintSight(sight);
        });
        effects = new Timeline(frame);
        effects.setCycleCount(Timeline.INDEFINITE);

        logger.debug(serverModel.toString());
        logger.debug(serverModel.getCurrentFloor().toString());


        initMenuSide();
        initMainWindow();
        initButtons();
        initMenus();
        initCharacterPicture();
        initStats();

        initEvents();
        initMapChangeListener();

        //  ViewUtils.configureScene(actualScene);

        serverModel.setMapChange(serverModel.getMapChange() + 1);
        addListener();

    }

    private void initMainWindow() {

        mainAnchor = new AnchorPane();

        AnchorPane.setLeftAnchor(charAnchor, 10.0);
        AnchorPane.setTopAnchor(charAnchor, 0.0);
        AnchorPane.setBottomAnchor(buttonAnchor, 0.0);
        AnchorPane.setLeftAnchor(buttonAnchor, 20.0);
        AnchorPane.setRightAnchor(miniMapGrid, 360.0);
        AnchorPane.setBottomAnchor(miniMapGrid, 225.0);
        AnchorPane.setRightAnchor(sightGrid, 360.0);
        AnchorPane.setBottomAnchor(sightGrid, 225.0);
        AnchorPane.setTopAnchor(vBoxExit, 140.0);
        AnchorPane.setRightAnchor(vBoxExit, 30.0);
        AnchorPane.setBottomAnchor(vBoxMiniMap, 60.0);
        AnchorPane.setRightAnchor(vBoxMiniMap, 30.0);
      //  AnchorPane.setBottomAnchor(chatButton, 120.0);
      //  AnchorPane.setRightAnchor(chatButton, 30.0);
        AnchorPane.setBottomAnchor(chat, 225.0);
        AnchorPane.setRightAnchor(chat, 360.0);

        infoSmall.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        mainAnchor.getChildren().addAll(sightGrid, miniMapGrid, vBoxExit, vBoxMiniMap, charAnchor, buttonAnchor, chat,infoSmall);

        this.setCenter(mainAnchor);
        this.setBackground(new Background(new BackgroundFill(imageHolder.mainBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    private void initMenuSide() {
        // sightGrid.setMinSize(544,544);
        // sightGrid.setMaxSize(544,544);

        exit = ViewUtilsHP.createButton("Exit");
        exit.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setPrefWidth(100);
        exit.setPrefWidth(100);
        vBoxExit = ViewUtilsHP.buttonSurrounding(exit);

      //  chatButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        miniMapButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void initMenus() {
//

        chat = presenter.posChat();
        miniMapGrid.setBackground(new Background(new BackgroundFill(imageHolder.miniMapSmallBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        miniMapGrid.setMinSize(350, 350);
        miniMapGrid.setMaxSize(350, 350);
        miniMapGrid.setVisible(false);

    }

    private void initStats() {

        stats.setBackground(new Background(new BackgroundFill(imageHolder.miniMapBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        stats.setVisible(false);
        stats.setPrefWidth(250);

        Rectangle border = new Rectangle(50, 200, Color.TRANSPARENT);

        Label charStats = ViewUtils.newHSP("Character Stats", 16);
        charStats.setTextFill(Color.DARKRED);
        Label dex = ViewUtils.newHSP("Dexterity:", 12);
        dex.setPrefWidth(100);
        dex.setTextFill(Color.DARKRED);
        Label str = ViewUtils.newHSP("Strenth:", 12);
        str.setTextFill(Color.DARKRED);
        Label wis = ViewUtils.newHSP("Wisdom:", 12);
        wis.setTextFill(Color.DARKRED);
        Label wea = ViewUtils.newHSP("Weapon", 12);
        wea.setTextFill(Color.DARKRED);
        Label pot = ViewUtils.newHSP("Potion", 12);
        pot.setTextFill(Color.DARKRED);

        stats.add(border, 0, 0, 1, 6);
        stats.add(charStats, 1, 0, 2, 1);
        stats.add(str, 1, 1);
        stats.add(strInt, 2, 1);
        stats.add(dex, 1, 2);
        stats.add(dexInt, 2, 2);
        stats.add(wis, 1, 3);
        stats.add(wisInt, 2, 3);
        stats.add(wea, 1, 4);
        stats.add(weaInt, 2, 4);
        stats.add(pot, 1, 5);
        stats.add(potInt, 2, 5);
    }

    private void initEvents() {
        miniMapButton.setOnAction(e -> {
            if (!miniMapGrid.isVisible()) {
                miniMapGrid.setVisible(true);
            } else {
                miniMapGrid.setVisible(false);
            }
        });

        viewStats.setOnAction(e -> {
            if (!stats.isVisible()) {
                stats.setVisible(true);
                viewStats.setText("Hide Stats");
            } else {
                stats.setVisible(false);
                viewStats.setText("Show Stats");
            }
        });
        xpBar.setOnMouseEntered(e -> {
            infoSmall.setVisible(true);
            infoSmall.setText("XP: " + serverModel.getSelfPlayer().getExp() + "|" + serverModel.getSelfPlayer().getNextLevelExp());
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());

        });
        xpBar.setOnMouseExited(e -> infoSmall.setVisible(false));
        xpBar.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        hpBar.setOnMouseEntered(e -> {
            infoSmall.setVisible(true);
            infoSmall.setText("HP: " + serverModel.getSelfPlayer().getHpcur() + "|" + serverModel.getSelfPlayer().getHpmax());
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });
        hpBar.setOnMouseExited(e -> infoSmall.setVisible(false));
        hpBar.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        move.setOnMouseEntered(e -> {
            infoSmall.setVisible(true);
            infoSmall.setText("Move");
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        move.setOnMouseExited(e -> {
            infoSmall.setVisible(false);
            if (!presenter.selButton.equals("move")) {
                move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        move.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        use.setOnMouseEntered(e -> {
            infoSmall.setVisible(true);
            infoSmall.setText("Use");
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        use.setOnMouseExited(e -> {
            infoSmall.setVisible(false);
            if (!presenter.selButton.equals("use")) {
                use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        use.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        melee.setOnMouseEntered(e -> {
            infoSmall.setVisible(true);
            infoSmall.setText("Melee");
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            melee.setBackground(new Background(new BackgroundFill(imageHolder.strButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        melee.setOnMouseExited(e -> {
            infoSmall.setVisible(false);
            if (!presenter.selButton.equals("attackMe")) {
                melee.setBackground(new Background(new BackgroundFill(imageHolder.strPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        melee.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        magic.setOnMouseEntered(e -> {
            infoSmall.setVisible(true);
            infoSmall.setText("Magic");
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            magic.setBackground(new Background(new BackgroundFill(imageHolder.attackMaPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        magic.setOnMouseExited(e -> {
            infoSmall.setVisible(false);
            magic.setBackground(new Background(new BackgroundFill(imageHolder.attackMaPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        magic.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        maFire.setOnMouseEntered(e -> {
            maFire.setBackground(new Background(new BackgroundFill(imageHolder.fireMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        maFire.setOnMouseExited(e -> {
            if (!presenter.selButton.equals("maFire")) {
                maFire.setBackground(new Background(new BackgroundFill(imageHolder.fireMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        maWind.setOnMouseEntered(e -> {
            maWind.setBackground(new Background(new BackgroundFill(imageHolder.windMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        maWind.setOnMouseExited(e -> {
            if (!presenter.selButton.equals("maWind")) {
                maWind.setBackground(new Background(new BackgroundFill(imageHolder.windMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        maIce.setOnMouseEntered(e -> {
            maIce.setBackground(new Background(new BackgroundFill(imageHolder.iceMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        maIce.setOnMouseExited(e -> {
            if (!presenter.selButton.equals("maIce")) {
                maIce.setBackground(new Background(new BackgroundFill(imageHolder.iceMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });


        range.setOnMouseEntered(e -> {
            infoSmall.setVisible(true);
            infoSmall.setText("Range");
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            range.setBackground(new Background(new BackgroundFill(imageHolder.dexButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        range.setOnMouseExited(e -> {
            infoSmall.setVisible(false);
            if (!presenter.selButton.equals("attackRa")) {
                range.setBackground(new Background(new BackgroundFill(imageHolder.dexPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        range.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090 - e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

    }

    private void initCharacterPicture() {

        // Define variables
        double charWidth = 120;
        double topDistance = charWidth + 20;
        double edgeDistance = 20;

        // set Width, Length, Stroke, Font etc for everything
        charPicture = new Circle(charWidth / 2, this.getCorrectFaceFill(0));

        //  charPicture.setStroke(Color.BLACK);charPicture.setStrokeWidth(0.5);
        level.setAlignment(Pos.CENTER);
        level.setPrefHeight(50);
        level.setPrefWidth(charWidth);
        level.setFont(new Font("AR DESTIN", 35));
        level.setTextFill(Color.BLACK);
        level.setBackground(new Background(new BackgroundFill(imageHolder.levelBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        hpBar.setPrefHeight(32);
        hpBar.setMinWidth(196);
        xpBar.setPrefHeight(32);
        xpBar.setMinWidth(196);
        xpBar.setStyle("-fx-accent: gold");
        viewStatsBox.setPrefWidth(120);
        viewStatsBox.setMaxHeight(25);
        viewStats.setPrefWidth(120);
        viewStats.setMaxHeight(25);
        viewStats.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        viewStats.setText("Show Stats");
        name.setTextFill(Color.WHITE);

        // Add to Anchor
        AnchorPane.setLeftAnchor(stats, topDistance + 200);
        AnchorPane.setTopAnchor(stats, edgeDistance);
        AnchorPane.setLeftAnchor(viewStatsBox, topDistance + 50);
        AnchorPane.setTopAnchor(viewStatsBox, topDistance - 10);
        AnchorPane.setLeftAnchor(charPicture, edgeDistance);
        AnchorPane.setTopAnchor(charPicture, edgeDistance);
        AnchorPane.setTopAnchor(weaImage, topDistance - 65);
        AnchorPane.setLeftAnchor(weaImage, topDistance + 5);
        AnchorPane.setTopAnchor(potImage, topDistance - 65);
        AnchorPane.setLeftAnchor(potImage, topDistance + 65 + 15);
        AnchorPane.setLeftAnchor(hpBar, topDistance);
        AnchorPane.setTopAnchor(hpBar, edgeDistance + 46);
        AnchorPane.setLeftAnchor(xpBar, topDistance);
        AnchorPane.setTopAnchor(xpBar, edgeDistance + 46 + 32);
        AnchorPane.setLeftAnchor(name, topDistance);
        AnchorPane.setTopAnchor(name, edgeDistance + 14);

        charAnchor.getChildren().addAll(charPicture, name,
                hpBar, xpBar, stats, viewStatsBox, potImage, weaImage);

    }

    private void initButtons() {

        buttonAnchor.setMaxWidth(300);

        move.setShape((new Circle(40.0)));
        move.setMaxSize(80, 80);
        move.setMinSize(80, 80);
        move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        use.setShape((new Circle(40.0)));
        use.setMaxSize(80, 80);
        use.setMinSize(80, 80);
        use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        magic.setMaxSize(70, 80);
        magic.setMinSize(70, 80);
        magic.setShape((new Circle(40.0)));
        magic.setBackground(new Background(new BackgroundFill(imageHolder.attackMaPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        AnchorPane.setBottomAnchor(move, 100.0);
        AnchorPane.setLeftAnchor(move, 70.0);
        AnchorPane.setBottomAnchor(use, 170.0);
        AnchorPane.setLeftAnchor(use, 0.0);
        AnchorPane.setBottomAnchor(magic, 20.0);
        AnchorPane.setLeftAnchor(magic, 160.0);

        buttonAnchor.getChildren().addAll(move, use, magic);

    }

    private void initMapChangeListener() {

        serverModel.getMapChangeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                Player cur = serverModel.getSelfPlayer();
                if (cur.getHpcur() > 0) {


                    effects.stop();
                    paintEffects = true;
                    effects.play();


                    int weaponStr = 0;
                    String weapon = "No Weapon";
                    weaImage.setVisible(false);
                    if (cur.getWeapon() != null) {
                        weaImage.setVisible(true);
                        weaponStr = cur.getWeapon().getStr();
                        weapon = cur.getWeapon().getName() + "(" + cur.getWeapon().getStr() + ")";
                    }

                    String potion = "No Potion";
                    potImage.setVisible(false);
                    if (cur.getPotion() != null) {
                        potImage.setVisible(true);
                        potion = cur.getPotion().getName();
                    }

                    sight.updateSight(serverModel.getCurrentFloor(), serverModel.getSelfPlayer().getPos());
                    repaintSight(sight);
                    miniMap.updateMiniMap(sight);
                    repaintMiniMap(sight);

                    potImage.setPrefSize(65, 65);

                    weaImage.setFill(getCorrectWeapon(0));
                    potImage.setBackground(new Background(new BackgroundFill(getCorrectPotion(0), CornerRadii.EMPTY, Insets.EMPTY)));
                    level.setText("" + cur.getLevel());
                    xpBar.setProgress(cur.getExp() / cur.getNextLevelExp());
                    hpBar.setProgress(cur.getHpcur() / cur.getHpmax());
                    strInt.setText("" + cur.getStr() + "(+ " + weaponStr + ")");
                    wisInt.setText("" + cur.getWis());
                    dexInt.setText("" + cur.getDex());
                    level.setText("" + cur.getLevel());
                    potInt.setText("" + potion);
                    weaInt.setText("" + weapon);

                } else {
                    presenter.singleplayerGameScene.setCenter(gameOverScreen);
                }

            }
        });
    }

    public ImagePattern getCorrectFaceFill(int i) {
        ImagePattern result = imageHolder.emptyFacePattern;
        switch (serverModel.getPlayers().get(i).getKlasse()) {
            case "Harry":
                result = imageHolder.harryFacePattern;
                break;
            case "Ron":
                result = imageHolder.ronFacePattern;
                break;
            case "Hermine":
                result = imageHolder.hermineFacePattern;
                break;
            case "Hagrid":
                result = imageHolder.hagridFacePattern;
                break;
            case "Dumbledore":
                result = imageHolder.dumbledoreFacePattern;
                break;
            case "Ginny":
                result = imageHolder.ginnyFacePattern;
                break;
            case "Neville":
                result = imageHolder.nevilleFacePattern;
                break;
            case "Moody":
                result = imageHolder.moodyFacePattern;
                break;
            case "Snape":
                result = imageHolder.snapeFacePattern;
                break;
        }

        return result;
    }

    public ImagePattern getCorrectFill(int i) {
        ImagePattern result = null;
        Player cur = serverModel.getPlayers().get(i);
        System.out.println(cur.getKlasse());
        switch (cur.getKlasse()) {
            case "Harry":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifHarryLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifHarryFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifHarryRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifHarryBackPattern;
                        break;
                }
                break;
            case "Ron":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifRonLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifRonFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifProfRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifProfBackPattern;
                        break;
                }
                break;
            case "Hermine":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifHermineLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifHermineFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifHermineRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifHermineBackPattern;
                        break;
                }
                break;
            case "Hagrid":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifHagridLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifHagridFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifHagridRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifHagridBackPattern;
                        break;
                }
                break;
            case "Dumbledore":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifDumbledoreLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifDumbledoreFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifDumbledoreRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifDumbledoreBackPattern;
                        break;
                }
                break;
            case "Ginny":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifGinnyLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifGinnyFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifGinnyRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifGinnyBackPattern;
                        break;
                }
                break;
            case "Neville":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifNevilleLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifNevilleFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifNevilleRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifNevilleBackPattern;
                        break;
                }
                break;
            case "Moody":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifMoodyLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifMoodyFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifMoodyRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifMoodyBackPattern;
                        break;
                }
                break;
            case "Snape":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.gifSnapeLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.gifSnapeFrontPattern;
                        break;
                    case "d":
                        result = imageHolder.gifSnapeRightPattern;
                        break;
                    case "w":
                        result = imageHolder.gifSnapeBackPattern;
                        break;
                }
                break;
        }
        return result;
    }

    public ImagePattern getCorrectPotion(int i) {
        ImagePattern result = null;
        try {
            switch (serverModel.getPlayers().get(i).getPotion().getName()) {
                case "Small Potion":
                    result = imageHolder.smallPotionPattern;
                    break;
                case "Big Potion":
                    result = imageHolder.bigPotionPattern;
                    break;
            }
        } catch (NullPointerException e) {
        }
        return result;
    }

    public ImagePattern getCorrectWeapon(int i) {
        ImagePattern result = null;
        try {
            switch (serverModel.getPlayers().get(i).getWeapon().getName()) {
                case "Sword":
                    result = imageHolder.swordPattern;
                    break;
                case "Axe":
                    result = imageHolder.axePattern;
                    break;
                case "Spear":
                    result = imageHolder.spearPattern;
                    break;
            }
        } catch (NullPointerException e) {
        }
        return result;
    }


    public void repaintSight(Sight sight) {
        System.out.println("In der Sight werden " + sight.getFieldArrayList().size() + " Felder gezeichnet");
        sightGrid.getChildren().clear();

        for (Field field : sight.getFieldArrayList()) {
            int xOrg = sight.getTranslation()[8][8].getOrgPosition().getX() - 8 + field.getPosX();
            int yOrg = sight.getTranslation()[8][8].getOrgPosition().getY() - 8 + field.getPosY();
            Rectangle rect = new Rectangle();
            StackPane stack = new StackPane();
            Text text = new Text("");
            text.setFill(Color.RED);
            stack.getChildren().addAll(rect);
            if (paintEffects) {
                {
                    if (xOrg > 0 && xOrg < serverModel.getCurrentFloor().getXSize() && yOrg > 0 && yOrg < serverModel.getCurrentFloor().getYSize()) {
                        if (serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo().isAttacked()) {
                            AttackInfo cur = serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo();

                            Rectangle damage = new Rectangle(32, 32);

                            switch (cur.getAttackTyp()) {

                                case "attackMe":
                                    damage.setFill(imageHolder.gifSwordSlashPattern);
                                    mediaplayer.playAttackMe();
                                    break;
                                case "attackMa":
                                    damage.setFill(imageHolder.iceMagicPattern);
                                    mediaplayer.playIceMagic();
                                    break;
                                case "attackRaDown":
                                    damage.setFill(imageHolder.attackRaDownPattern);
                                    mediaplayer.playAttackRa();
                                    break;
                                case "attackRaUp":
                                    damage.setFill(imageHolder.attackRaUpPattern);
                                    mediaplayer.playAttackRa();
                                    break;
                                case "attackRaLeft":
                                    damage.setFill(imageHolder.attackRaLeftPattern);
                                    mediaplayer.playAttackRa();
                                    break;
                                case "attackRaRight":
                                    damage.setFill(imageHolder.attackRaRightPattern);
                                    mediaplayer.playAttackRa();
                                    break;
                                case "attackGoblin":
                                    damage.setFill(imageHolder.attackGoblinPattern);
                                    mediaplayer.playAttackMonster();
                                    break;
                                case "attackWolf":
                                    damage.setFill(imageHolder.attackWolfPattern);
                                    mediaplayer.playAttackMonster();
                                    break;
                                case "attackFinalBoss":
                                    damage.setFill(imageHolder.attackFinalBossPattern);
                                    mediaplayer.playAttackFinalBoss();
                            }
                            damage.setOpacity(0.8);
                            stack.getChildren().clear();
                            text.setText("-" + cur.getWithStr());
                            text.setFont(new Font("AR DESTINE", 20));
                            text.setStroke(Color.BLACK);
                            text.setStrokeWidth(2.0);
                            stack.getChildren().addAll(rect, damage);
                            serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo().reset();
                        }
                    }
                }
            }
            rect.setHeight(32);
            rect.setWidth(32);
            sightGrid.add(stack, field.getPosX(), field.getPosY());
            switch (sight.getField()[field.getPosX()][field.getPosY()].getContent()) {
                case WALL:
                    rect.setFill(imageHolder.wallPattern);
                    break;
                case FLOOR:
                    rect.setFill(imageHolder.floorPattern);
                    break;
                case EMPTY:
                    rect.setFill(Color.TRANSPARENT);
                    break;
                case CHESTCLOSED:
                    rect.setFill(imageHolder.chestClosedPattern);
                    break;
                case CHESTOPEN:
                    rect.setFill(imageHolder.chestOpenedPattern);
                    break;
                case DOORCLOSED:
                    rect.setFill(imageHolder.doorClosedPattern);
                    break;
                case DOOROPEN:
                    rect.setFill(imageHolder.doorOpenedPattern);
                    break;
                case STAIRDOWN:
                    rect.setFill(imageHolder.stairDownPattern);
                    break;
                case DEMENTOR:
                    rect.setFill(imageHolder.dementorPattern);
                    break;
                case BELLATRIX:
                    rect.setFill(imageHolder.bellatrixPattern);
                    break;
                case DRACO:
                    rect.setFill(imageHolder.dracoPattern);
                    break;
                case BOSS:
                    rect.setFill(imageHolder.bossPattern);
                    break;
                case LUCIUS:
                    rect.setFill(imageHolder.luciusPattern);break;
                case VOLDEMORT:
                    rect.setFill(imageHolder.voldemortPattern);break;
                case PLAYER1:
                    rect.setFill(getCorrectFill(0));
                    break;
                case PLAYER2:
                    rect.setFill(getCorrectFill(1));
                    break;
                case PLAYER3:
                    rect.setFill(getCorrectFill(2));
                    break;
                case PLAYER4:
                    rect.setFill(getCorrectFill(3));
                    break;
                case PLAYER5:
                    rect.setFill(getCorrectFill(4));
                    break;
                case PLAYER6:
                    rect.setFill(getCorrectFill(5));
                    break;
                case PLAYER7:
                    rect.setFill(getCorrectFill(6));
                    break;
                case PLAYER8:
                    rect.setFill(getCorrectFill(7));
                    break;
            }

        }
    }

    public void repaintMiniMap(Sight sight) {
        for (Field miniMapField : miniMap.getFieldArrayList()) {
            Rectangle rect = new Rectangle();
            StackPane stack = new StackPane();

            Text text = new Text("");
            stack.getChildren().addAll(rect);
            rect.setHeight(350 / serverModel.getCurrentFloor().getXSize());
            rect.setWidth(350 / serverModel.getCurrentFloor().getXSize());
            miniMapGrid.add(stack, miniMapField.getPosX(), miniMapField.getPosY());
            switch (miniMapField.getContent()) {
                case WALL:
                    rect.setFill(imageHolder.wallPattern);
                    break;
                case FLOOR:
                    rect.setFill(imageHolder.floorPattern);
                    break;
                case EMPTY:
                    rect.setFill(Color.TRANSPARENT);
                    break;
                case CHESTCLOSED:
                    rect.setFill(imageHolder.chestClosedPattern);
                    text.setText("C");
                    break;
                case CHESTOPEN:
                    rect.setFill(imageHolder.chestOpenedPattern);
                    text.setText("O");
                    break;
                case DOORCLOSED:
                    rect.setFill(imageHolder.doorClosedPattern);
                    text.setText("C");
                    break;
                case DOOROPEN:
                    rect.setFill(imageHolder.doorOpenedPattern);
                    text.setText("O");
                    break;
                case STAIRDOWN:
                    rect.setFill(imageHolder.stairDownPattern);
                    text.setText("SD");
                    break;
                case SKELETT:
                    rect.setFill(imageHolder.dementorPattern);
                    text.setText("S");
                    break;
                case GOBLIN:
                    rect.setFill(imageHolder.bellatrixPattern);
                    text.setText("G");
                    break;
                case WOLF:
                    rect.setFill(imageHolder.dracoPattern);
                    text.setText("W");
                    break;
                case PLAYER1:
                    rect.setFill(getCorrectFill(0));
                    text.setText("1");
                    break;
                case PLAYER2:
                    rect.setFill(getCorrectFill(1));
                    text.setText("2");
                    break;
                case PLAYER3:
                    rect.setFill(getCorrectFill(2));
                    text.setText("3");
                    break;
                case PLAYER4:
                    rect.setFill(getCorrectFill(3));
                    text.setText("4");
                    break;
                case PLAYER5:
                    rect.setFill(getCorrectFill(4));
                    text.setText("5");
                    break;
                case PLAYER6:
                    rect.setFill(getCorrectFill(5));
                    text.setText("6");
                    break;
                case PLAYER7:
                    rect.setFill(getCorrectFill(6));
                    text.setText("7");
                    break;
                case PLAYER8:
                    rect.setFill(getCorrectFill(7));
                    text.setText("8");
                    break;
                case DEMENTOR:
                    rect.setFill(imageHolder.dementorPattern);break;
                case DRACO:
                    rect.setFill(imageHolder.dracoPattern);break;
                case LUCIUS:
                    rect.setFill(imageHolder.luciusPattern);break;
                case VOLDEMORT:
                    rect.setFill(imageHolder.voldemortPattern);break;
                case BELLATRIX:
                    rect.setFill(imageHolder.bellatrixPattern);break;
            }
        }
    }

    public void addListener() {
        integerProperty.addListener((observable, oldValue, newValue) -> startThread());
        floorChangeProperty.addListener((observable, oldValue, newValue) -> startFloorChangeThread());
    }

    public void startFloorChangeThread() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        miniMap.setFields(serverModel.getCurrentFloor().getXSize(), serverModel.getCurrentFloor().getYSize());
                        miniMapGrid.getChildren().clear();
                        miniMap.floorChangeMiniMap(sight);
                        repaintMiniMap(sight);
                    }
                });

                return null;
            }

        };
        Thread thread = new Thread(task);
        thread.start();

    }


    public void startThread() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        effects.stop();
                        paintEffects = true;
                        effects.play();

                        Player cur = serverModel.getSelfPlayer();
                        if (cur.getHpcur() > 0) {


                            effects.stop();
                            paintEffects = true;
                            effects.play();


                            int weaponStr = 0;
                            String weapon = "No Weapon";
                            weaImage.setVisible(false);
                            if (cur.getWeapon() != null) {
                                weaImage.setVisible(true);
                                weaponStr = cur.getWeapon().getStr();
                                weapon = cur.getWeapon().getName() + "(" + cur.getWeapon().getStr() + ")";
                            }

                            String potion = "No Potion";
                            potImage.setVisible(false);
                            if (cur.getPotion() != null) {
                                potImage.setVisible(true);
                                potion = cur.getPotion().getName();
                            }

                            sight.updateSight(serverModel.getCurrentFloor(), serverModel.getSelfPlayer().getPos());
                            repaintSight(sight);
                            miniMap.updateMiniMap(sight);
                            repaintMiniMap(sight);

                            potImage.setPrefSize(65, 65);

                            weaImage.setFill(getCorrectWeapon(0));
                            potImage.setBackground(new Background(new BackgroundFill(getCorrectPotion(0), CornerRadii.EMPTY, Insets.EMPTY)));
                            level.setText("" + cur.getLevel());
                            xpBar.setProgress(cur.getExp() / cur.getNextLevelExp());
                            hpBar.setProgress(cur.getHpcur() / cur.getHpmax());
                            strInt.setText("" + cur.getStr() + "(+ " + weaponStr + ")");
                            wisInt.setText("" + cur.getWis());
                            dexInt.setText("" + cur.getDex());
                            level.setText("" + cur.getLevel());
                            potInt.setText("" + potion);
                            weaInt.setText("" + weapon);

                            for (int i = 0; i < serverModel.getMonsters().size(); i++) {
                                if (serverModel.getMonsters().get(i).getNick().contains("Boss")) {
                                    if (serverModel.getMonsters().get(i).getHpcur() <= 0) {
                                        presenter.singleplayerGameScene.setCenter(gameWonScreen);
                                    }
                                }
                            }
                        } else {
                            presenter.singleplayerGameScene.setCenter(gameOverScreen);
                        }

                    }
                });


                return null;
            }

        };
        Thread thread = new Thread(task);
        thread.start();

    }

    public int getFloorChangeProperty() {
        return floorChangeProperty.get();
    }

    public IntegerProperty floorChangePropertyProperty() {
        return floorChangeProperty;
    }

    public void setFloorChangeProperty(int floorChangeProperty) {
        this.floorChangeProperty.set(floorChangeProperty);
    }

    public int getIntegerProperty() {
        return integerProperty.get();
    }

    public IntegerProperty integerPropertyProperty() {
        return integerProperty;
    }

    public void setIntegerProperty(int integerProperty) {
        this.integerProperty.set(integerProperty);
    }
}
