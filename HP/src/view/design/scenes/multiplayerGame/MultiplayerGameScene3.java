package view.design.scenes.multiplayerGame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.util.Duration;
import model.*;
import model.entity.Player;
import model.util.AttackInfo;
import model.util.Position;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import util.ViewUtils;
import view.design.scenes.MusicPlayer;


/**
 * Created by Felix on 10.12.2015.
 */
public class MultiplayerGameScene3 extends BorderPane {

    // Important constant Variables

    private MultiplayerGamePresenter3 presenter;
    private Scene actualScene;
    private MusicPlayer mediaplayer = new MusicPlayer();
    private ClientModel serverModel;
    public ImageHolder2 imageHolder = new ImageHolder2();

    // Screensize dependent constant variables
    int middleColumnWidth = 32 * 17; // 32*17 =544  -- should be 17*32 because the Sight is 17 rectangles a 32 pixels width
    double screenWidth = Screen.getPrimary().getBounds().getWidth();
    double screenHeight = Screen.getPrimary().getBounds().getHeight();
    double rightSideWidth = (screenWidth - middleColumnWidth) / 2;
    double leftSideWidth = (screenWidth - middleColumnWidth) / 2;
    int charPictureSideLength = 200;

    // is true for the first few seconds, so that the view paints the effects.
    boolean paintEffects = false;

    /*
    The Main Grid pain devides the View in 3 columns
     */
    GridPane mainGrid = new GridPane();

    // Left column holds the MiniMap and the exit button
    MiniMap miniMap;
    GridPane miniMapGrid = new GridPane();
    Button exit = ViewUtils.newSPButton("");

    // Middle column holds the game View, the timeBar and the movement buttons
    GridPane gamePane = new GridPane();
    private ProgressBar timeBar = new ProgressBar(1);

    HBox buttonsMoveUse = new HBox();
    HBox buttonsAttacks = new HBox();
    Button move = ViewUtils.newSPButton("");
    Button attackRa = ViewUtils.newSPButton("");
    Button attackMa = ViewUtils.newSPButton("");
    Button attackMe = ViewUtils.newSPButton("");
    Button use = ViewUtils.newSPButton("");
    Button submit = ViewUtils.newSPButton("");

    double buttonHeight = (screenHeight - middleColumnWidth-100) / 8;

    // Right column holds the character picture,character information and target information(if visible)

    Rectangle characterPictureDamage = new Rectangle(rightSideWidth, 0, Color.RED);
    Label hp = ViewUtils.newHSP("HP", 30);

    Label weaponDes = ViewUtils.newHSP("Weapon", 15);
    Button weaponButton = new Button();
    Rectangle weaponImage = new Rectangle(charPictureSideLength / 2, charPictureSideLength / 2, getCorrectWeapon(0));
    Label potionDes = ViewUtils.newHSP("Potion", 15);
    Button potionButton = new Button();
    Rectangle potionImage = new Rectangle(charPictureSideLength / 2, charPictureSideLength / 2, getCorrectPotion(0));

    Label level = ViewUtils.newHSP("0", 30);
    private ProgressBar expBar = new ProgressBar(0);

    Label strInt = ViewUtils.newHSP("", 20);
    Label wisInt = ViewUtils.newHSP("", 20);
    Label dexInt = ViewUtils.newHSP("", 20);

    Label targetLabel = ViewUtils.newHSP("Target:", 30);
    Label targetName = ViewUtils.newHSP("", 30);
    Label targetHP = ViewUtils.newHSP("", 20);
    Rectangle target = new Rectangle(rightSideWidth / 3, rightSideWidth / 3, Color.TRANSPARENT);
    ProgressBar targetHPBar = new ProgressBar(1);

    public MultiplayerGameScene3(ClientModel serverModel, MultiplayerGamePresenter3 presenter) {
        mediaplayer.stopIntro();
        mediaplayer.playLevel1();
        this.presenter = presenter;
        this.serverModel = serverModel;
        miniMap = new MiniMap(serverModel,serverModel.getCurrentFloor().getXSize(), serverModel.getCurrentFloor().getYSize());
        Sight sight = new Sight(serverModel.getCurrentFloor(), serverModel.getSelfPlayer().getPos(), serverModel);
        repaintSight(sight);
        startTimeline();



        bindComponents();
        configureButtons();

        // Set button backgrounds
        submit.setBackground(new Background(new BackgroundFill(imageHolder.submitButtonLargePattern, CornerRadii.EMPTY, Insets.EMPTY)));
        move.setBackground(new Background(new BackgroundFill(imageHolder.moveButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        use.setBackground(new Background(new BackgroundFill(imageHolder.useButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setBackground(new Background(new BackgroundFill(imageHolder.exitButtonPattern, CornerRadii.EMPTY, Insets.EMPTY)));

        // Set controll button height dependent on the screen size
        timeBar.setPrefWidth(middleColumnWidth);
        timeBar.setStyle("-fx-accent: orange");
        move.setPrefHeight(buttonHeight);
        use.setPrefHeight(buttonHeight);
        attackMa.setPrefHeight(buttonHeight);
        attackMe.setPrefHeight(buttonHeight);
        attackRa.setPrefHeight(buttonHeight);
        submit.setPrefHeight(buttonHeight);
        move.setMinWidth(middleColumnWidth / 2);
        use.setMinWidth(middleColumnWidth / 2);
        submit.setMinWidth(middleColumnWidth);
        exit.setPrefHeight(screenHeight - middleColumnWidth);
        exit.setPrefWidth(rightSideWidth);

        // Configute left Side
        initializeRightColumn();

        /*
        LAYOUTING MIDDLE COLUMN
         */

        VBox centerLayout = new VBox();

        buttonsMoveUse.getChildren().addAll(move, use);
        centerLayout.getChildren().addAll(gamePane, buttonsMoveUse, buttonsAttacks, submit);

        mainGrid.add(gamePane, 2, 0);
        mainGrid.add(timeBar, 2, 1);
        mainGrid.add(buttonsMoveUse, 2, 2);
        mainGrid.add(buttonsAttacks, 2, 3);
        mainGrid.add(submit, 2, 4);


        /*
        LAYOUTING RIGHT COLUMN
         */

        VBox rightLayout = new VBox();
        StackPane playerHealthPicture = new StackPane();
        Rectangle behindPichture = new Rectangle(rightSideWidth, charPictureSideLength, Color.WHITE);
        Rectangle characterPicture = new Rectangle(charPictureSideLength, charPictureSideLength, getCorrectFaceFill(0));
        playerHealthPicture.getChildren().addAll(behindPichture, characterPicture, characterPictureDamage);

        rightLayout.getChildren().add(playerHealthPicture);
        rightLayout.getChildren().add(hp);

        GridPane info = new GridPane();
        StackPane weaponStack = new StackPane();
        weaponStack.getChildren().addAll(weaponImage, weaponButton);
        StackPane potionStack = new StackPane();
        potionStack.getChildren().addAll(potionImage, potionButton);

        weaponStack.setAlignment(Pos.CENTER);
        potionStack.setAlignment(Pos.CENTER);
        info.add(weaponStack, 0, 0);
        info.add(weaponDes, 0, 1);
        info.add(potionStack, 1, 0);
        info.add(potionDes, 1, 1);

        Label levelLabel = ViewUtils.newHSP("Level:", 30);
        Label str = ViewUtils.newHSP("Strength:", 20);
        Label wis = ViewUtils.newHSP("Wisdom:", 20);
        Label dex = ViewUtils.newHSP("Dexterity:", 20);

        info.add(levelLabel, 0, 2);
        info.add(level, 1, 2);
        info.add(expBar, 0, 3, 2, 1);
        info.add(str, 0, 4);
        info.add(wis, 0, 5);
        info.add(dex, 0, 6);
        info.add(strInt, 1, 4);
        info.add(wisInt, 1, 5);
        info.add(dexInt, 1, 6);

        StackPane infoStack = new StackPane();
        Rectangle infoBack = new Rectangle(rightSideWidth, screenHeight, imageHolder.sideBackgroundPattern);
        infoStack.getChildren().addAll(infoBack, rightLayout);

        GridPane targetGrid = new GridPane();
        ColumnConstraints column = new ColumnConstraints(rightSideWidth / 2);
        targetGrid.getColumnConstraints().add(column);

        targetGrid.add(targetLabel, 0, 0);
        targetGrid.add(targetName, 1, 0);
        targetGrid.add(targetHP, 1, 1);
        targetGrid.add(targetHPBar, 1, 2, 2, 1);
        targetGrid.add(target, 0, 1, 1, 2);

        rightLayout.getChildren().addAll(info, targetGrid);
        mainGrid.add(infoStack, 3, 0, 1, 5);
        //  mainGrid.add(targetGrid,3,1,1,4);

        /*
        LAYOUTING LEFT COLUMN
         */

        StackPane leftSide = new StackPane();
        VBox leftSideBox = new VBox();
        Rectangle miniMapBack = new Rectangle(leftSideWidth, middleColumnWidth, imageHolder.miniMapBackgroundPattern);
        Rectangle leftSideBack = new Rectangle(leftSideWidth, screenHeight, imageHolder.sideBackgroundPattern);
        StackPane miniMapStack = new StackPane();
        miniMapStack.getChildren().addAll(miniMapBack, miniMapGrid);
        leftSideBox.getChildren().addAll(miniMapStack, exit);
        leftSide.getChildren().addAll(leftSideBack, leftSideBox);
        miniMapGrid.setMinHeight(middleColumnWidth);
        miniMapGrid.setMinWidth(leftSideWidth);

        Rectangle exitBackground = new Rectangle(leftSideWidth, screenHeight - middleColumnWidth, imageHolder.buttonBackgroundPattern);
        StackPane exitPane = new StackPane();
        // exitPane.getChildren().addAll(exit);

        exit.setPrefWidth(150);
        exit.setMaxWidth(rightSideWidth);
        exit.setAlignment(Pos.CENTER);
        mainGrid.add(leftSide, 1, 0, 1, 5);
        //   mainGrid.add(exitPane,1,1,1,4);

        /*
        MAIN GRID STACK
         */
        StackPane mainStack = new StackPane();
        Rectangle back = new Rectangle(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), imageHolder.buttonBackgroundPattern);
        mainStack.getChildren().addAll(back, mainGrid);


        this.setCenter(mainStack);

        actualScene = ViewUtils.createScene(this);


        //   ViewUtils.configureScene(actualScene);

       /* try {
            serverModel.setMapChange(serverModel.getMapChange() + 1);
        } */
    }

    private void initializeRightColumn() {
        characterPictureDamage.setOpacity(0.4);

        hp.setMaxWidth(rightSideWidth);
        hp.setPrefWidth(rightSideWidth);
        hp.setText(serverModel.getSelfPlayer() + "|" + serverModel.getSelfPlayer().getHpmax());
        hp.setAlignment(Pos.CENTER);
        hp.setTextAlignment(TextAlignment.CENTER);
        hp.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        weaponButton.setMinSize(charPictureSideLength / 2, charPictureSideLength / 2);
        potionButton.setMinSize(charPictureSideLength / 2, charPictureSideLength / 2);
        weaponButton.setOpacity(0.1);
        potionButton.setOpacity(0.1);
        weaponDes.setPrefSize(rightSideWidth / 2, 20);
        weaponDes.setMaxSize(rightSideWidth / 2, 20);
        potionDes.setMaxSize(rightSideWidth / 2, 20);
        potionDes.setPrefSize(rightSideWidth / 2, 20);
        weaponDes.setAlignment(Pos.CENTER);
        potionDes.setAlignment(Pos.CENTER);

        expBar.setPrefWidth(rightSideWidth);

        targetHPBar.setStyle("-fx-accent: red");
        targetHPBar.prefWidth(rightSideWidth / 2);
        targetHPBar.minWidth(rightSideWidth / 2);
        targetHPBar.setVisible(false);
    }

    private void bindComponents() {

        serverModel.getMapChangeProperty().addListener((observable, oldValue, newValue) -> {
            Sight sight = new Sight(serverModel.getCurrentFloor(), serverModel.getSelfPlayer().getPos(), serverModel);
            repaintSight(sight);
            miniMap.updateMiniMap(sight);
            //   miniMap.setFields(serverModel.getCurrentFloor().getXSize(), serverModel.getCurrentFloor().getYSize());
            repaintMiniMap(sight);
            Player cur = serverModel.getSelfPlayer();
            int weaponStr = 0;
            if (cur.getWeapon() != null) {
                weaponStr = cur.getWeapon().getStr();
            }
            hp.setText(cur.getHpcur() + "|" + cur.getHpmax());
            characterPictureDamage.setHeight(charPictureSideLength - (charPictureSideLength * cur.getHpcur() / cur.getHpmax()));
            if (characterPictureDamage.getHeight() > charPictureSideLength) {
                characterPictureDamage.setHeight(charPictureSideLength);
            }
            weaponImage.setFill(getCorrectWeapon(0));
            potionImage.setFill(getCorrectPotion(0));
            level.setText("" + cur.getLevel());
            expBar.setProgress(cur.getExp() / cur.getNextLevelExp());
            strInt.setText("" + cur.getStr() + "+ " + weaponStr);
            wisInt.setText("" + cur.getWis());
            dexInt.setText("" + cur.getDex());
            setTargetFill(cur);


        });

        presenter.getSecondCountProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                timeBar.setProgress(((double) newValue) / 100.0);

            }
        });

        presenter.dir.addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                Sight sight = new Sight(serverModel.getCurrentFloor(), serverModel.getSelfPlayer().getPos(), serverModel);
                setTargetFill(serverModel.getSelfPlayer());
                repaintSight(sight);
            }
        });
    }

    private void setTargetFill(Player cur) {
        Floor floor = serverModel.getCurrentFloor();
        Position targetPos = (cur.calcNextField(presenter.dir.get()));
        boolean monster = false;
        int count = 0;
        while (monster == false && count < 4) {
            switch (floor.getFieldAtPos(targetPos).getContent()) {
                case GOBLIN:
                    target.setFill(imageHolder.goblinPattern);
                    targetName.setText("Goblin");
                    monster = true;
                    count = 4;
                    break;
                case SKELETT:
                    target.setFill(imageHolder.skeletonPattern);
                    targetName.setText("Skeleton");
                    monster = true;
                    count = 4;
                    break;
                case WOLF:
                    target.setFill(imageHolder.wolfPattern);
                    targetName.setText("Wolf");
                    monster = true;
                    count = 4;
                    break;
                case BOSS:
                    target.setFill(imageHolder.bossPattern);
                    targetName.setText("Doom");
                    monster = true;
                    count = 4;
                    break;
                case WALL:
                    count = 4;
                    break;
                case CHESTCLOSED:
                    count = 4;
                    break;
                case CHESTOPEN:
                    count = 4;
                    break;
                case DOORCLOSED:
                    count = 4;
                    break;
                case DOOROPEN:
                    count = 4;
                    break;
            }
            count++;
            if (!monster) {
                targetPos = cur.calcNextField(presenter.dir.get(), targetPos);
            }
        }
        if (monster) {
 /*           Entity mon = serverModel.getEntityAtPosition(targetPos);
            targetHP.setText(mon.getHpcur() + "|" + mon.getHpmax());
            targetLabel.setVisible(true);
            targetName.setVisible(true);
            targetHP.setVisible(true);
            targetHPBar.setVisible(true);
            targetHPBar.setProgress(mon.getHpcur() / mon.getHpmax());*/
        } else {
            targetLabel.setVisible(false);
            target.setFill(Color.TRANSPARENT);
            targetHPBar.setVisible(false);
            targetHP.setVisible(false);
            targetName.setVisible(false);
        }
    }

    public ImagePattern getCorrectFaceFill(int i) {
        ImagePattern result = imageHolder.emptyFacePattern;
        switch (serverModel.getPlayers().get(i).getKlasse()) {
            case "Professor":
                result = imageHolder.profFacePattern;
                break;
            case "Warrior":
                result = imageHolder.warriorFacePattern;
                break;
            case "Ranger":
                result = imageHolder.rangerFacePattern;
                break;
            case "Mage":
                result = imageHolder.mageFacePattern;
                break;
            case "Student":
                result = imageHolder.studentFacePattern;
                break;
        }

        return result;
    }

    public ImagePattern getCorrectFill(int i) {
        ImagePattern result = null;
        Player cur = serverModel.getPlayers().get(i);
        switch (cur.getKlasse()) {
            case "Mage":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.mageLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.mageDownPattern;
                        break;
                    case "d":
                        result = imageHolder.mageRightPattern;
                        break;
                    case "w":
                        result = imageHolder.mageUpPattern;
                        break;
                }
                break;
            case "Professor":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.professorLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.professorDownPattern;
                        break;
                    case "d":
                        result = imageHolder.professorRightPattern;
                        break;
                    case "w":
                        result = imageHolder.professorUpPattern;
                        break;
                }
                break;
            case "Ranger":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.rangerLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.rangerDownPattern;
                        break;
                    case "d":
                        result = imageHolder.rangerRightPattern;
                        break;
                    case "w":
                        result = imageHolder.rangerUpPattern;
                        break;
                }
                break;
            case "Student":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.studentLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.studentDownPattern;
                        break;
                    case "d":
                        result = imageHolder.studentRightPattern;
                        break;
                    case "w":
                        result = imageHolder.studentUpPattern;
                        break;
                }
                break;
            case "Tourist":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.touristLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.touristDownPattern;
                        break;
                    case "d":
                        result = imageHolder.touristRightPattern;
                        break;
                    case "w":
                        result = imageHolder.touristUpPattern;
                        break;
                }
                break;
            case "Warrior":
                switch (presenter.dir.get()) {
                    case "a":
                        result = imageHolder.warriorLeftPattern;
                        break;
                    case "s":
                        result = imageHolder.warriorDownPattern;
                        break;
                    case "d":
                        result = imageHolder.warriorRightPattern;
                        break;
                    case "w":
                        result = imageHolder.warriorUpPattern;
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


    public  void startTimeline(){
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0), e -> {
            repaintSight(new Sight(serverModel.getCurrentFloor(), serverModel.getSelfPlayer().getPos(), serverModel));
        });

        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void repaintSight(Sight sight) {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {

                int xOrg = sight.getTranslation()[8][8].getOrgPosition().getX() - 8 + j;
                int yOrg = sight.getTranslation()[8][8].getOrgPosition().getY() - 8 + i;
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
                                        damage.setFill(imageHolder.attackMePattern);
                                        mediaplayer.playAttackMe();
                                        break;
                                    case "attackMa":
                                        damage.setFill(imageHolder.attackMaPattern);
                                        mediaplayer.playAttackMa();
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
                                stack.getChildren().addAll(rect, damage, text);
                                serverModel.getCurrentFloor().getFieldAtPos(new Position(xOrg, yOrg)).getAttackInfo().reset();
                            }
                        }
                    }
                }
                rect.setHeight(32);
                rect.setWidth(32);
                gamePane.add(stack, j, i);
                switch (sight.getField()[j][i].getContent()) {
                    case WALL:
                        rect.setFill(imageHolder.wallPattern);
                        break;
                    case FLOOR:
                        rect.setFill(imageHolder.floorPattern);
                        break;
                    case EMPTY:
                        rect.setFill(Color.BLACK);
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
                    case SKELETT:
                        rect.setFill(imageHolder.skeletonPattern);
                        break;
                    case GOBLIN:
                        rect.setFill(imageHolder.goblinPattern);
                        break;
                    case WOLF:
                        rect.setFill(imageHolder.wolfPattern);
                        break;
                    case BOSS:
                        rect.setFill(imageHolder.bossPattern);
                        break;
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
    }

    public void repaintMiniMap(Sight sight) {
        for (int j = 0; j < serverModel.getCurrentFloor().getXSize(); j++) {
            for (int i = 0; i < serverModel.getCurrentFloor().getYSize(); i++) {
                Rectangle rect = new Rectangle();
                StackPane stack = new StackPane();

                Text text = new Text("");
                stack.getChildren().addAll(rect);
                rect.setHeight(leftSideWidth / 30);
                rect.setWidth(leftSideWidth / 30);
                miniMapGrid.add(stack, j, i);
                switch (miniMap.getField()[j][i].getContent()) {
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
                        rect.setFill(imageHolder.skeletonPattern);
                        text.setText("S");
                        break;
                    case GOBLIN:
                        rect.setFill(imageHolder.goblinPattern);
                        text.setText("G");
                        break;
                    case WOLF:
                        rect.setFill(imageHolder.wolfPattern);
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

                }
            }
        }

    }

    public Scene getActualScene() {
        return actualScene;
    }

    private void configureButtons() {


        switch (serverModel.getSelfPlayer().getKlasse()) {
            case "Professor":
                attackRa.setDisable(true);
                attackRa.setVisible(false);
                buttonsAttacks.getChildren().addAll(attackMe, attackMa);
                attackMe.setPrefWidth(middleColumnWidth / 2);
                attackMa.setPrefWidth(middleColumnWidth / 2);
                attackMe.setBackground(new Background(new BackgroundFill(imageHolder.meleeButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
                attackMa.setBackground(new Background(new BackgroundFill(imageHolder.magicButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case "Ranger":
                attackMa.setDisable(true);
                attackMa.setVisible(false);
                buttonsAttacks.getChildren().addAll(attackMe, attackRa);
                attackMe.setPrefWidth(middleColumnWidth / 2);
                attackRa.setPrefWidth(middleColumnWidth / 2);
                attackMe.setBackground(new Background(new BackgroundFill(imageHolder.meleeButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
                attackRa.setBackground(new Background(new BackgroundFill(imageHolder.rangeButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case "Warrior":
                attackMa.setDisable(true);
                attackRa.setDisable(true);
                attackMa.setVisible(false);
                attackRa.setVisible(false);
                buttonsAttacks.getChildren().addAll(attackMe);
                attackMe.setBackground(new Background(new BackgroundFill(imageHolder.meleeButtonLargePattern, CornerRadii.EMPTY, Insets.EMPTY)));
                attackMe.setPrefWidth(middleColumnWidth);
                break;
            case "Student":
                attackRa.setDisable(true);
                attackRa.setVisible(false);
                attackMa.setVisible(false);
                attackMa.setDisable(true);
                buttonsAttacks.getChildren().addAll(attackMe);
                attackMe.setBackground(new Background(new BackgroundFill(imageHolder.meleeButtonLargePattern, CornerRadii.EMPTY, Insets.EMPTY)));
                attackMe.setPrefWidth(middleColumnWidth);
                break;
            case "Tourist":
                attackRa.setDisable(true);
                attackRa.setVisible(false);
                attackMa.setVisible(false);
                attackMa.setDisable(true);
                buttonsAttacks.getChildren().addAll(attackMe);
                attackMe.setBackground(new Background(new BackgroundFill(imageHolder.meleeButtonLargePattern, CornerRadii.EMPTY, Insets.EMPTY)));
                attackMe.setPrefWidth(middleColumnWidth);
                break;
            case "Mage":
                attackMe.setDisable(true);
                attackMe.setVisible(false);
                buttonsAttacks.getChildren().addAll(attackMa, attackRa);
                attackMa.setPrefWidth(middleColumnWidth / 2);
                attackRa.setPrefWidth(middleColumnWidth / 2);
                attackMa.setBackground(new Background(new BackgroundFill(imageHolder.magicButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
                attackRa.setBackground(new Background(new BackgroundFill(imageHolder.rangeButtonSmallPattern, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }

    }
}