package view.design.scenes.singleplayerGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.MiniMap;
import model.entity.Player;
import model.util.AttackInfo;
import model.util.Field;
import model.util.Position;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.ServerModel;
import model.Sight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ViewUtils;
import view.design.scenes.MusicPlayer;

import java.io.IOException;


/**
 * Created by Felix on 10.12.2015.
 */
public class SingleplayerGameScene extends BorderPane {

    private static final Logger logger = LoggerFactory.getLogger(SingleplayerGameScene.class);
    private MiniMap miniMap;
    // Important constant Variables

    private SingleplayerGamePresenter presenter;
    private Scene actualScene;
    private MusicPlayer mediaplayer = new MusicPlayer();
    private ServerModel serverModel;
    public ImageHolder imageHolder = new ImageHolder();
    public Sight sight;

    // MainScreen
    GridPane sightGrid = new GridPane();
    AnchorPane mainAnchor;

    BorderPane menuPane = new BorderPane();
    GridPane miniMapGrid = new GridPane();
    GridPane miniMapMainGrid = new GridPane();

    BorderPane exitGrid = new BorderPane();
    Button exit = new Button("Exit the Game");

    AnchorPane charAnchor = new AnchorPane();
    ProgressBar hpBar = new ProgressBar(1);
    ProgressBar xpBar = new ProgressBar(0);
    Label level = new Label("1");
    Circle charPicture;
    Button viewStats = new Button();
    GridPane stats = new GridPane();
    Rectangle weaImage = new Rectangle(65,65);
    Button potImage = new Button("");


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
    Button info = new Button("");

    Rectangle infoRect = new Rectangle(190,100);

    Label infoSmall = new Label();
    Label dexInt = ViewUtils.newHSP("Dex:",12);
    Label strInt = ViewUtils.newHSP("Str:",12);
    Label wisInt = ViewUtils.newHSP("Wis:",12);
    Label weaInt = ViewUtils.newHSP("No Weapon",12);
    Label potInt = ViewUtils.newHSP("No Potion",12);

    private boolean paintEffects = false;
    Timeline effects;
    SinglePlayerGameOver gameOverScreen = new SinglePlayerGameOver();
    SinglePlayerGameWon gameWonScreen = new SinglePlayerGameWon();


    public SingleplayerGameScene(ServerModel serverModel, SingleplayerGamePresenter presenter) {
        mediaplayer.stopIntro();
        mediaplayer.playLevel1();
        this.presenter = presenter;
        this.serverModel = serverModel;
        miniMap = new MiniMap(serverModel, serverModel.getCurrentFloor().getXSize(), serverModel.getCurrentFloor().getYSize());
        miniMap.setFields(serverModel.getCurrentFloor().getXSize(),serverModel.getCurrentFloor().getYSize());

        sight = new Sight(serverModel.getCurrentFloor(), serverModel.getPlayers().get(0).getPos(), serverModel);


        KeyFrame frame = new KeyFrame(Duration.seconds(3), e -> {paintEffects = false;repaintSight(sight);});
        effects = new Timeline(frame);
        effects.setCycleCount(Timeline.INDEFINITE);

        logger.debug(serverModel.toString());
        logger.debug(serverModel.getCurrentFloor().toString());


        initMainWindow();
        initButtons();
        initMenus();
        initCharacterPicture();
        initStats();
        initInfo();
        initEvents();
        initMapChangeListener();

        //  ViewUtils.configureScene(actualScene);
        actualScene = ViewUtils.createScene(this);


        try {
            serverModel.setMapChange(serverModel.getMapChange()+1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initInfo(){

        infoRect.setFill(imageHolder.info190Pattern);
        infoRect.setVisible(true);

    }

    private void initMenus() {

        // init MiniMap Slide Out

        miniMapMainGrid.setBackground(new Background(new BackgroundFill(imageHolder.miniMapSmallBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        miniMapMainGrid.setMaxSize(50,450);
        miniMapMainGrid.setMinSize(50,450);

        miniMapGrid.setMinSize(350,500);
        miniMapGrid.setMaxSize(350,500);

        Rectangle border = new Rectangle(80,380, Color.TRANSPARENT);
        Rectangle borderHor = new Rectangle(500,20, Color.TRANSPARENT);

        Label miniMapLabel = ViewUtils.newHSP("Mini Map:",30);
        miniMapLabel.setTextFill(Color.DARKRED);
        miniMapLabel.setPrefWidth(350);
        miniMapLabel.setMaxSize(350, 30);
        miniMapLabel.setAlignment(Pos.CENTER);
        miniMapLabel.setTextAlignment(TextAlignment.CENTER);

        miniMapMainGrid.add(miniMapLabel,1,1);
        miniMapMainGrid.add(miniMapGrid,1,2);
        miniMapMainGrid.add(border,0,0,1,3);
        miniMapMainGrid.add(borderHor,1,0);


        // init Exit Slide Out
        exit.setFont(new Font("SEGOE SCRIPT", 30));
        exit.setAlignment(Pos.CENTER);
        exit.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setTextFill(Color.DARKRED);
        exit.setOnMouseEntered(e -> exit.setTextFill(Color.WHITE));
        exit.setOnMouseExited(e -> exit.setTextFill(Color.DARKRED));

        exitGrid.setMaxSize(50,150);
        exitGrid.setMinSize(50,150);
        exitGrid.setBackground(new Background(new BackgroundFill(imageHolder.miniMapSmallBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    private void initStats() {

        stats.setBackground(new Background(new BackgroundFill(imageHolder.miniMapBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        stats.setVisible(false);
        stats.setPrefWidth(250);

        Rectangle border = new Rectangle(50,200, Color.TRANSPARENT);

        Label charStats = ViewUtils.newHSP("Character Stats",16);
        charStats.setTextFill(Color.DARKRED);
        Label dex = ViewUtils.newHSP("Dexterity:",12);dex.setPrefWidth(100);dex.setTextFill(Color.DARKRED);
        Label str = ViewUtils.newHSP("Strenth:",12);str.setTextFill(Color.DARKRED);
        Label wis = ViewUtils.newHSP("Wisdom:",12);wis.setTextFill(Color.DARKRED);
        Label wea = ViewUtils.newHSP("Weapon",12);wea.setTextFill(Color.DARKRED);
        Label pot = ViewUtils.newHSP("Potion",12);pot.setTextFill(Color.DARKRED);

        stats.add(border,0,0,1,6);
        stats.add(charStats, 1,0,2,1);
        stats.add(str, 1,1);
        stats.add(strInt, 2,1);
        stats.add(dex, 1,2);stats.add(dexInt, 2,2);
        stats.add(wis, 1,3);stats.add(wisInt, 2,3);
        stats.add(wea, 1,4);stats.add(weaInt, 2,4);
        stats.add(pot, 1,5);stats.add(potInt, 2,5);
    }

    private void initEvents() {
        miniMapMainGrid.setOnMouseEntered(e-> {
            miniMapMainGrid.setMinWidth(500);
            miniMapMainGrid.setBackground(new Background(new BackgroundFill(imageHolder.miniMapBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        miniMapMainGrid.setOnMouseExited(e ->{
            miniMapMainGrid.setMinWidth(50);
            miniMapMainGrid.setMaxWidth(50);
            miniMapMainGrid.setBackground(new Background(new BackgroundFill(imageHolder.miniMapSmallBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        info.setBackground(new Background(new BackgroundFill(imageHolder.infoButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        info.setMinWidth(50);
        info.setMaxWidth(50);
        info.setMinHeight(50);
        info.setMaxHeight(50);

        exitGrid.setOnMouseEntered(e-> {
            exitGrid.setMinWidth(400);
            exitGrid.setBackground(new Background(new BackgroundFill(imageHolder.miniMapBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            exitGrid.setCenter(exit);
        });
        exitGrid.setOnMouseExited(e ->{
            exitGrid.setMinWidth(50);
            exitGrid.setMaxWidth(50);
            exitGrid.setBackground(new Background(new BackgroundFill(imageHolder.miniMapSmallBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
            exitGrid.setCenter(null);
        });

        viewStats.setOnAction(e -> {
            if(!stats.isVisible()){stats.setVisible(true);
                viewStats.setBackground(new Background(new BackgroundFill(imageHolder.hideStatsPattern, CornerRadii.EMPTY, Insets.EMPTY)));;}
            else{
                stats.setVisible(false);
                viewStats.setBackground(new Background(new BackgroundFill(imageHolder.showStatsPattern, CornerRadii.EMPTY, Insets.EMPTY)));;
            }
        });
        xpBar.setOnMouseEntered(e ->{
            infoSmall.setVisible(true);
            infoSmall.setText("XP: " + serverModel.getPlayers().get(0).getExp()+ "|"+ serverModel.getPlayers().get(0).getNextLevelExp());
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());

        });
        xpBar.setOnMouseExited(e -> infoSmall.setVisible(false));
        xpBar.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        hpBar.setOnMouseEntered(e ->{
            infoSmall.setVisible(true);
            infoSmall.setText("HP: "+ serverModel.getPlayers().get(0).getHpcur() + "|"+ serverModel.getPlayers().get(0).getHpmax());
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });
        hpBar.setOnMouseExited(e -> infoSmall.setVisible(false));
        hpBar.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        move.setOnMouseEntered(e ->{
            infoSmall.setVisible(true);
            infoSmall.setText("Move");
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        move.setOnMouseExited(e ->{ infoSmall.setVisible(false);
            if(!presenter.selButton.equals("move")){ move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));}});
        move.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        use.setOnMouseEntered(e ->{
            infoSmall.setVisible(true);
            infoSmall.setText("Use");
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        use.setOnMouseExited(e -> {infoSmall.setVisible(false);
            if(!presenter.selButton.equals("use")){use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));}});
        use.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        melee.setOnMouseEntered(e ->{
            infoSmall.setVisible(true);
            infoSmall.setText("Melee");
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            melee.setBackground(new Background(new BackgroundFill(imageHolder.strButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        melee.setOnMouseExited(e ->{ infoSmall.setVisible(false);
            if(!presenter.selButton.equals("attackMe")){melee.setBackground(new Background(new BackgroundFill(imageHolder.strPattern, CornerRadii.EMPTY, Insets.EMPTY)));}});
        melee.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        magic.setOnMouseEntered(e ->{
            infoSmall.setVisible(true);
            infoSmall.setText("Magic");
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            magic.setBackground(new Background(new BackgroundFill(imageHolder.wisSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        magic.setOnMouseExited(e ->  {infoSmall.setVisible(false);
                magic.setBackground(new Background(new BackgroundFill(imageHolder.wisPattern, CornerRadii.EMPTY, Insets.EMPTY)));});
        magic.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

        maFire.setOnMouseEntered(e ->{maFire.setBackground(new Background(new BackgroundFill(imageHolder.fireMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));});
        maFire.setOnMouseExited(e ->{  if(!presenter.selButton.equals("maFire")){
            maFire.setBackground(new Background(new BackgroundFill(imageHolder.fireMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        }});
        maWind.setOnMouseEntered(e ->{maWind.setBackground(new Background(new BackgroundFill(imageHolder.windMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));});
        maWind.setOnMouseExited(e ->{ if(!presenter.selButton.equals("maWind")){
            maWind.setBackground(new Background(new BackgroundFill(imageHolder.windMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        }});
        maIce.setOnMouseEntered(e ->{maIce.setBackground(new Background(new BackgroundFill(imageHolder.iceMagicSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));});
        maIce.setOnMouseExited(e ->{ if(!presenter.selButton.equals("maIce")){
            maIce.setBackground(new Background(new BackgroundFill(imageHolder.iceMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        }});


        range.setOnMouseEntered(e ->{
            infoSmall.setVisible(true);
            infoSmall.setText("Range");
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
            range.setBackground(new Background(new BackgroundFill(imageHolder.dexButtonSelectedPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        });
        range.setOnMouseExited(e -> {infoSmall.setVisible(false);
            if(!presenter.selButton.equals("attackRa")){range.setBackground(new Background(new BackgroundFill(imageHolder.dexPattern, CornerRadii.EMPTY, Insets.EMPTY)));}});
        range.setOnMouseMoved(e -> {
            AnchorPane.setRightAnchor(infoSmall, 1090-e.getSceneX());
            AnchorPane.setTopAnchor(infoSmall, e.getSceneY());
        });

    }

    private void initMainWindow() {

        mainAnchor= new AnchorPane();

        AnchorPane.setRightAnchor(charAnchor, 600.0);
        AnchorPane.setTopAnchor(charAnchor, 0.0);
        AnchorPane.setBottomAnchor(buttonAnchor, 0.0);
        AnchorPane.setRightAnchor(buttonAnchor, 750.0);
        AnchorPane.setRightAnchor(miniMapMainGrid, 0.0);
        AnchorPane.setBottomAnchor(miniMapMainGrid,20.0);
        //AnchorPane.setRightAnchor(sightGrid, 300.0);
        //AnchorPane.setBottomAnchor(sightGrid, 40.0);
        AnchorPane.setRightAnchor(sightGrid, 360.0);
        AnchorPane.setBottomAnchor(sightGrid, 225.0);
        AnchorPane.setBottomAnchor(exitGrid,480.0);
        AnchorPane.setRightAnchor(exitGrid,0.0);
        AnchorPane.setBottomAnchor(infoRect, 225.0);
        AnchorPane.setLeftAnchor(infoRect, 160.0);


        infoSmall.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        mainAnchor.getChildren().addAll(sightGrid,miniMapMainGrid, exitGrid, charAnchor, buttonAnchor, infoSmall, infoRect);

        this.setCenter(mainAnchor);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    private void initCharacterPicture() {

        // Define variables
        double charWidth = 120;
        double topDistance = charWidth+20;
        double edgeDistance = 20;

        // set Width, Length, Stroke, Font etc for everything
        charPicture = new Circle(charWidth/2, this.getCorrectFaceFill(0));
        Circle charBackground = new Circle(charWidth/2, Color.WHITE);//imageHolder.pictureBackgroundPattern );
        Rectangle backBackground = new Rectangle(charWidth, charWidth/2+60, Color.WHITE);

        charPicture.setStroke(Color.BLACK);charPicture.setStrokeWidth(0.5);
        level.setAlignment(Pos.CENTER);level.setPrefHeight(50);level.setPrefWidth(charWidth);
        level.setFont(new Font("AR DESTIN", 35));level.setTextFill(Color.BLACK);
        level.setBackground(new Background(new BackgroundFill(imageHolder.levelBackgroundPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        hpBar.setPrefHeight(25);hpBar.setMinWidth(150);
        xpBar.setPrefHeight(25);xpBar.setMinWidth(150); xpBar.setStyle("-fx-accent: gold");
        viewStats.setPrefWidth(150);
        viewStats.setMaxHeight(25);
        viewStats.setBackground(new Background(new BackgroundFill(imageHolder.showStatsPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add to Anchor
        AnchorPane.setLeftAnchor(stats, topDistance+150);
        AnchorPane.setTopAnchor(stats, edgeDistance);
        AnchorPane.setLeftAnchor(viewStats, topDistance);
        AnchorPane.setTopAnchor(viewStats, topDistance+50);
        AnchorPane.setLeftAnchor(charBackground, edgeDistance);
        AnchorPane.setTopAnchor(charBackground,edgeDistance);
        AnchorPane.setLeftAnchor(charPicture, edgeDistance);
        AnchorPane.setTopAnchor(charPicture,edgeDistance);
        AnchorPane.setLeftAnchor(level, edgeDistance);
        AnchorPane.setTopAnchor(level,topDistance);
        AnchorPane.setLeftAnchor(backBackground, edgeDistance);
        AnchorPane.setTopAnchor(backBackground, charWidth/2+10);
        AnchorPane.setTopAnchor(weaImage, topDistance-65);
        AnchorPane.setLeftAnchor(weaImage, topDistance+5);
        AnchorPane.setTopAnchor(potImage, topDistance-65);
        AnchorPane.setLeftAnchor(potImage, topDistance+65+15);
        AnchorPane.setLeftAnchor(hpBar, topDistance);
        AnchorPane.setTopAnchor(hpBar,topDistance);
        AnchorPane.setLeftAnchor(xpBar, topDistance);
        AnchorPane.setTopAnchor(xpBar,topDistance+25);

        charAnchor.getChildren().addAll(backBackground,charBackground, charPicture,level,
                hpBar, xpBar,stats, viewStats, potImage, weaImage );

    }

    private void initButtons() {

        buttonAnchor.setMaxWidth(300);
        maFire.setVisible(false);
        maIce.setVisible(false);
        maWind.setVisible(false);

        info.setShape((new Circle(25.0)));
        info.setMaxSize(50,50);
        info.setMinSize(50,50);
        info.setBackground(new Background(new BackgroundFill(imageHolder.infoButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));


        move.setShape((new Circle(40.0)));
        move.setMaxSize(80,80);
        move.setMinSize(80,80);
        move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        use.setShape((new Circle(40.0)));
        use.setMaxSize(80,80);
        use.setMinSize(80,80);
        use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        melee.setShape((new Circle(40.0)));
        melee.setMaxSize(80,80);
        melee.setMinSize(80,80);
        melee.setBackground(new Background(new BackgroundFill(imageHolder.strPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        range.setShape((new Circle(40.0)));
        range.setMaxSize(80,80);
        range.setMinSize(80,80);
        range.setBackground(new Background(new BackgroundFill(imageHolder.dexPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        magic.setMaxSize(80,80);
        magic.setMinSize(80,80);
        magic.setShape((new Circle(40.0)));
        magic.setBackground(new Background(new BackgroundFill(imageHolder.wisPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        maIce.setShape((new Circle(40.0)));
        maIce.setMaxSize(50,50);
        maIce.setMinSize(50,50);
        maIce.setBackground(new Background(new BackgroundFill(imageHolder.iceMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        maWind.setShape((new Circle(40.0)));
        maWind.setMaxSize(50,50);
        maWind.setMinSize(50,50);
        maWind.setBackground(new Background(new BackgroundFill(imageHolder.windMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        maFire.setShape((new Circle(40.0)));
        maFire.setMaxSize(50,50);
        maFire.setMinSize(50,50);
        maFire.setBackground(new Background(new BackgroundFill(imageHolder.fireMagicBGPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        submit.setPrefSize(180,60 );

        AnchorPane.setLeftAnchor(arc1,40.0);
        AnchorPane.setBottomAnchor(arc1,40.0);
        AnchorPane.setLeftAnchor(arc2,60.0);
        AnchorPane.setBottomAnchor(arc2,60.0);
        AnchorPane.setBottomAnchor(move, 270.0);
        AnchorPane.setLeftAnchor(move, 20.0);
        AnchorPane.setBottomAnchor(use, 160.0);
        AnchorPane.setLeftAnchor(use, 40.0);
        AnchorPane.setBottomAnchor(magic, 20.0);
        AnchorPane.setLeftAnchor(magic, 240.0);
        AnchorPane.setBottomAnchor(maFire, 115.0);
        AnchorPane.setLeftAnchor(maFire,255.0);
        AnchorPane.setBottomAnchor(maWind, 90.0);
        AnchorPane.setLeftAnchor(maWind,320.0);
        AnchorPane.setBottomAnchor(maIce, 25.0);
        AnchorPane.setLeftAnchor(maIce,340.0);
        AnchorPane.setBottomAnchor(melee, 65.0);
        AnchorPane.setLeftAnchor(melee, 115.0);
        AnchorPane.setBottomAnchor(range, 20.0);
        AnchorPane.setLeftAnchor(range, 240.0);
        AnchorPane.setBottomAnchor(info, 70.0);
        AnchorPane.setLeftAnchor(info, 20.0);

        buttonAnchor.getChildren().addAll(arc1, arc2, move, use, info);

        addCorrectButtons();


    }

    private void addCorrectButtons() {
        switch(serverModel.getPlayers().get(0).getKlasse()){
            case "Warrior": arc1.setLength(50.0); arc2.setLength(50.0);
                        AnchorPane.setBottomAnchor(arc1,100.0);
                        AnchorPane.setBottomAnchor(arc2,120.0);
                        buttonAnchor.getChildren().addAll(melee, maIce);break;
            case "Tourist": arc1.setLength(50.0); arc2.setLength(50.0);
                AnchorPane.setBottomAnchor(arc1,100.0);
                AnchorPane.setBottomAnchor(arc2,120.0);
                buttonAnchor.getChildren().addAll(melee, maIce);break;
            case "Student":arc1.setLength(50.0); arc2.setLength(50.0);
                AnchorPane.setBottomAnchor(arc1,100.0);
                AnchorPane.setBottomAnchor(arc2,120.0);
                buttonAnchor.getChildren().addAll(melee, maIce);break;
            case "Mage": arc2.setLength(78.0);
                AnchorPane.setBottomAnchor(range, 65.0);
                AnchorPane.setLeftAnchor(range, 115.0);
                buttonAnchor.getChildren().addAll(range,magic, maIce, maWind, maFire);break;
            case "Professor": arc2.setLength(78.0);
                buttonAnchor.getChildren().addAll(melee, magic, maIce, maWind, maFire);break;
            case "Ranger":arc2.setLength(78.0);
                buttonAnchor.getChildren().addAll(range,melee, maIce, maWind, maFire);break;
        }
    }

    private void initMapChangeListener() {

        serverModel.getMapChangeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                Player cur = serverModel.getPlayers().get(0);
                if(cur.getHpcur()>0) {


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

                    sight.updateSight(serverModel.getCurrentFloor(), serverModel.getPlayers().get(0).getPos());
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

                    for(int i=0; i<serverModel.getMonsters().size(); i++){
                        if(serverModel.getMonsters().get(i).getNick().contains("Boss")){
                            if(serverModel.getMonsters().get(i).getHpcur()<=0){
                                presenter.singleplayerGameScene.setCenter(gameWonScreen);
                                mediaplayer.stopLevel4();
                                mediaplayer.playVictory();
                            }
                        }
                    }
                }
                else{
                    presenter.singleplayerGameScene.setCenter(gameOverScreen);
                    mediaplayer.stopLevel1();
                    mediaplayer.stopLevelBeforeFinalBoss();
                    mediaplayer.stopLevel4();
                    mediaplayer.playGameOver();
                }

            }
        });


        serverModel.getFloorChange().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                miniMap.setFields(serverModel.getCurrentFloor().getXSize(),serverModel.getCurrentFloor().getYSize());
                miniMapGrid.getChildren().clear();
                sight.updateSight(serverModel.getCurrentFloor(), serverModel.getPlayers().get(0).getPos());
                repaintSight(sight);
                miniMap.floorChangeMiniMap(sight);
                repaintMiniMap(sight);
                switch(serverModel.getCurrentFloor().getLevel()){
                    case 1: mediaplayer.stopLevel1();mediaplayer.playLevelBeforeFinalBoss();break;
                    case 2: mediaplayer.stopLevelBeforeFinalBoss();mediaplayer.playLevel4();break;
                }
            }
        });
    }

    public ImagePattern getCorrectFaceFill(int i){
        ImagePattern result =imageHolder.emptyFacePattern;
        switch(serverModel.getPlayers().get(i).getKlasse()){
            case "Professor":result = imageHolder.profFacePattern;break;
            case "Warrior":result = imageHolder.warriorFacePattern;break;
            case "Ranger":result = imageHolder.rangerFacePattern;break;
            case "Mage":result = imageHolder.mageFacePattern;break;
            case "Student":result = imageHolder.studentFacePattern;break;
            case "Tourist":result = imageHolder.touristFacePattern;break;
        }

        return result;
    }

    public ImagePattern getCorrectFill(int i){
        ImagePattern result =null;
        Player cur = serverModel.getPlayers().get(i);
        switch(cur.getKlasse()){
            case "Mage":  switch(presenter.dir.get()){
                case "a": result = imageHolder.gifMageLeftPattern;break;
                case "s": result = imageHolder.gifMageFrontPattern;break;
                case "d": result = imageHolder.gifMageRightPattern;break;
                case "w": result = imageHolder.gifMageBackPattern;break;
            }break;
            case "Professor": switch(presenter.dir.get()){
                case "a": result = imageHolder.gifProfLeftPattern;break;
                case "s": result = imageHolder.gifProfFrontPattern;break;
                case "d": result = imageHolder.gifProfRightPattern;break;
                case "w": result = imageHolder.gifProfBackPattern;break;
            }break;
            case "Ranger":  switch(presenter.dir.get()){
                case "a": result = imageHolder.gifRangerLeftPattern;break;
                case "s": result = imageHolder.gifRangerFrontPattern;break;
                case "d": result = imageHolder.gifRangerRightPattern;break;
                case "w": result = imageHolder.gifRangerBackPattern;break;
            }break;
            case "Student":  switch(presenter.dir.get()){
                case "a": result = imageHolder.gifStudentLeftPattern;break;
                case "s": result = imageHolder.gifStudentFrontPattern;break;
                case "d": result = imageHolder.gifStudentRightPattern;break;
                case "w": result = imageHolder.gifStudentBackPattern;break;
            }break;
            case "Tourist": switch(presenter.dir.get()){
                case "a": result = imageHolder.gifTouristLeftPattern;break;
                case "s": result = imageHolder.gifTouristFrontPattern;break;
                case "d": result = imageHolder.gifTouristRightPattern;break;
                case "w": result = imageHolder.gifTouristBackPattern;break;
            }break;
            case "Warrior":
                switch(presenter.dir.get()){
                    case "a": result = imageHolder.gifWarriorLeftPattern;break;
                    case "s": result = imageHolder.gifWarriorFrontPattern;break;
                    case "d": result = imageHolder.gifWarriorRightPattern;break;
                    case "w": result = imageHolder.gifWarriorBackPattern;break;
                }break;
        }
        return result;
    }

    public ImagePattern getCorrectPotion(int i){
        ImagePattern result =null;
        try {
            switch (serverModel.getPlayers().get(i).getPotion().getName()) {
                case "Small Potion":
                    result = imageHolder.smallPotionPattern;
                    break;
                case "Big Potion":
                    result = imageHolder.bigPotionPattern;
                    break;
            }
        }catch(NullPointerException e){}
        return result;
    }

    public ImagePattern getCorrectWeapon(int i){
        ImagePattern result =null;
        try{
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
        }  catch(NullPointerException e){}
        return result;
    }

    public void repaintSight2(Sight sight){

        for(int i=0; i<17;i++){
            for(int j=0; j<17;j++){
                int xOrg = sight.getTranslation()[8][8].getOrgPosition().getX()-8+j;
                int yOrg = sight.getTranslation()[8][8].getOrgPosition().getY()-8+i;
                Rectangle rect = new Rectangle();
                StackPane stack = new StackPane();
                Text text = new Text("");
                text.setFill(Color.RED);
                stack.getChildren().addAll(rect);
                if(paintEffects) {
                    {
                        if (xOrg > 0 && xOrg < serverModel.getCurrentFloor().getXSize() && yOrg > 0 && yOrg < serverModel.getCurrentFloor().getYSize()) {
                            if (serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo().isAttacked()) {
                                AttackInfo cur = serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo();

                                Rectangle damage = new Rectangle(32, 32);

                                switch (cur.getAttackTyp()){

                                    case "attackMe": damage.setFill(imageHolder.attackMePattern);
                                        mediaplayer.playAttackMe();
                                        break;
                                    case "attackIce": damage.setFill(imageHolder.iceMagicPattern);
                                        mediaplayer.playIceMagic();break;
                                    case "attackMa": damage.setFill(imageHolder.windMagicPattern);
                                        mediaplayer.playAttackMa();break;
                                    case "attackRaDown": damage.setFill(imageHolder.attackRaDownPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackRaUp": damage.setFill(imageHolder.attackRaUpPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackRaLeft": damage.setFill(imageHolder.attackRaLeftPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackRaRight": damage.setFill(imageHolder.attackRaRightPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackGoblin": damage.setFill(imageHolder.attackGoblinPattern);
                                        mediaplayer.playAttackMonster();break;
                                    case "attackWolf": damage.setFill(imageHolder.attackWolfPattern);
                                        mediaplayer.playAttackMonster(); break;
                                    case "attackFinalBoss": damage.setFill(imageHolder.attackFinalBossPattern);
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
                sightGrid.add(stack, j, i);
                switch(sight.getField()[j][i].getContent()){
                    case WALL: rect.setFill(imageHolder.wallPattern); break;
                    case FLOOR: rect.setFill(imageHolder.floorPattern);break;
                    case EMPTY: rect.setFill(Color.BLACK);break;
                    case CHESTCLOSED: rect.setFill(imageHolder.chestClosedPattern);break;
                    case CHESTOPEN: rect.setFill(imageHolder.chestOpenedPattern);break;
                    case DOORCLOSED: rect.setFill(imageHolder.doorClosedPattern);break;
                    case DOOROPEN: rect.setFill(imageHolder.doorOpenedPattern);break;
                    case STAIRDOWN: rect.setFill(imageHolder.stairDownPattern);break;
                    case SKELETT: rect.setFill(imageHolder.skeletonPattern);break;
                    case GOBLIN: rect.setFill(imageHolder.goblinPattern);break;
                    case WOLF: rect.setFill(imageHolder.wolfPattern);break;
                    case BOSS: rect.setFill(imageHolder.bossPattern);break;
                    case PLAYER1: rect.setFill(getCorrectFill(0));break;
                    case PLAYER2: rect.setFill(getCorrectFill(1));break;
                    case PLAYER3: rect.setFill(getCorrectFill(2));break;
                    case PLAYER4: rect.setFill(getCorrectFill(3));break;
                    case PLAYER5: rect.setFill(getCorrectFill(4));break;
                    case PLAYER6: rect.setFill(getCorrectFill(5));break;
                    case PLAYER7: rect.setFill(getCorrectFill(6));break;
                    case PLAYER8: rect.setFill(getCorrectFill(7));break;
                }

            }
        }
    }

    public void showInfo(){
        infoRect.setVisible(true);
    }

    public void hideInfo(){
        infoRect.setVisible(false);
    }

    public void repaintSight(Sight sight){
        sightGrid.getChildren().clear();
        for (Field field : sight.getFieldArrayList()){
                int xOrg = sight.getTranslation()[8][8].getOrgPosition().getX()-8+field.getPosX();
                int yOrg = sight.getTranslation()[8][8].getOrgPosition().getY()-8+field.getPosY();
                Rectangle rect = new Rectangle();
                StackPane stack = new StackPane();
                Text text = new Text("");
                text.setFill(Color.RED);
                stack.getChildren().addAll(rect);
                if(paintEffects) {
                    {
                        if (xOrg > 0 && xOrg < serverModel.getCurrentFloor().getXSize() && yOrg > 0 && yOrg < serverModel.getCurrentFloor().getYSize()) {
                            if (serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo().isAttacked()) {
                                AttackInfo cur = serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo();

                                Rectangle damage = new Rectangle(32, 32);

                                switch (cur.getAttackTyp()){

                                    case "attackMe": damage.setFill(imageHolder.attackMePattern);
                                        mediaplayer.playAttackMe();break;
                                    case "attackMa": damage.setFill(imageHolder.attackMaPattern);
                                        mediaplayer.playAttackMa();break;
                                    case "attackIce": damage.setFill(imageHolder.iceMagicPattern);
                                        mediaplayer.playIceMagic();break;
                                    case "attackWind": damage.setFill(imageHolder.windMagicPattern);
                                        mediaplayer.playWindMagic();break;
                                    case "attackRaDown": damage.setFill(imageHolder.attackRaDownPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackRaUp": damage.setFill(imageHolder.attackRaUpPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackRaLeft": damage.setFill(imageHolder.attackRaLeftPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackRaRight": damage.setFill(imageHolder.attackRaRightPattern);
                                        mediaplayer.playAttackRa();break;
                                    case "attackGoblin": damage.setFill(imageHolder.attackGoblinPattern);
                                        mediaplayer.playAttackMonster();break;
                                    case "attackWolf": damage.setFill(imageHolder.attackWolfPattern);
                                        mediaplayer.playAttackMonster(); break;
                                    case "attackFinalBoss": damage.setFill(imageHolder.attackFinalBossPattern);
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
                switch(sight.getField()[field.getPosX()][field.getPosY()].getContent()){
                    case WALL: rect.setFill(imageHolder.wallPattern); break;
                    case FLOOR: rect.setFill(imageHolder.floorPattern);break;
                    case EMPTY: rect.setFill(Color.BLACK);break;
                    case CHESTCLOSED: rect.setFill(imageHolder.chestClosedPattern);break;
                    case CHESTOPEN: rect.setFill(imageHolder.chestOpenedPattern);break;
                    case DOORCLOSED: rect.setFill(imageHolder.doorClosedPattern);break;
                    case DOOROPEN: rect.setFill(imageHolder.doorOpenedPattern);break;
                    case STAIRDOWN: rect.setFill(imageHolder.stairDownPattern);break;
                    case SKELETT: rect.setFill(imageHolder.skeletonPattern);break;
                    case GOBLIN: rect.setFill(imageHolder.goblinPattern);break;
                    case WOLF: rect.setFill(imageHolder.wolfPattern);break;
                    case BOSS: rect.setFill(imageHolder.bossPattern);break;
                    case PLAYER1: rect.setFill(getCorrectFill(0));break;
                    case PLAYER2: rect.setFill(getCorrectFill(1));break;
                    case PLAYER3: rect.setFill(getCorrectFill(2));break;
                    case PLAYER4: rect.setFill(getCorrectFill(3));break;
                    case PLAYER5: rect.setFill(getCorrectFill(4));break;
                    case PLAYER6: rect.setFill(getCorrectFill(5));break;
                    case PLAYER7: rect.setFill(getCorrectFill(6));break;
                    case PLAYER8: rect.setFill(getCorrectFill(7));break;
                }

            }
        }

    public void repaintMiniMap(Sight sight){
        for(Field miniMapField : miniMap.getFieldArrayList()){
                Rectangle rect = new Rectangle();
                StackPane stack = new StackPane();

                Text text = new Text("");
                stack.getChildren().addAll(rect);
                rect.setHeight(350/serverModel.getCurrentFloor().getXSize());
                rect.setWidth(350/serverModel.getCurrentFloor().getXSize());
                miniMapGrid.add(stack, miniMapField.getPosX(), miniMapField.getPosY());
                switch(miniMapField.getContent()){
                    case WALL: rect.setFill(imageHolder.wallPattern); break;
                    case FLOOR: rect.setFill(imageHolder.floorPattern);break;
                    case EMPTY: rect.setFill(Color.TRANSPARENT);break;
                    case CHESTCLOSED: rect.setFill(imageHolder.chestClosedPattern);
                        text.setText("C");break;
                    case CHESTOPEN: rect.setFill(imageHolder.chestOpenedPattern);
                        text.setText("O");break;
                    case DOORCLOSED: rect.setFill(imageHolder.doorClosedPattern);
                        text.setText("C");break;
                    case DOOROPEN: rect.setFill(imageHolder.doorOpenedPattern);
                        text.setText("O");break;
                    case STAIRDOWN: rect.setFill(imageHolder.stairDownPattern);
                        text.setText("SD");break;
                    case SKELETT: rect.setFill(imageHolder.skeletonPattern);
                        text.setText("S");break;
                    case GOBLIN: rect.setFill(imageHolder.goblinPattern);
                        text.setText("G");break;
                    case WOLF: rect.setFill(imageHolder.wolfPattern);
                        text.setText("W");break;
                    case PLAYER1: rect.setFill(getCorrectFill(0));
                        text.setText("1");break;
                    case PLAYER2: rect.setFill(getCorrectFill(1));
                        text.setText("2");break;
                    case PLAYER3: rect.setFill(getCorrectFill(2));
                        text.setText("3");break;
                    case PLAYER4: rect.setFill(getCorrectFill(3));
                        text.setText("4");break;
                    case PLAYER5: rect.setFill(getCorrectFill(4));
                        text.setText("5");break;
                    case PLAYER6: rect.setFill(getCorrectFill(5));
                        text.setText("6");break;
                    case PLAYER7: rect.setFill(getCorrectFill(6));
                        text.setText("7");break;
                    case PLAYER8: rect.setFill(getCorrectFill(7));
                        text.setText("8");break;

                }
            }
        }


    public Scene getActualScene() {
        return actualScene;
    }

    public Button getInfo(){
        return info;
    }

    /*
    public void playDoorOpenMusic()
    {
        doorOpen.play();
    }

    public void playChestOpendMusic()
    {
        chestOpen.play();
    }

    public void playAttackMeMusic()
    {
        attackMe.play();
    }

    public void playPlayerDamageMusic() {playerDamag.play();}
*/
}
