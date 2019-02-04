package view.design.scenes.singleplayerLobby;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import util.ViewUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import view.design.scenes.MusicPlayer;

/**
 * Created by Rusty on 11.01.2016.
 */
public class SingleplayerGameLobbyScene extends BorderPane{

    //controls

    GridPane chooseClass = new GridPane();

    SimpleIntegerProperty x = new SimpleIntegerProperty(0);
    private MusicPlayer mediaplayer = new MusicPlayer();

    private Button back;
    private Button start;
    private Label titleLabel;
    private Scene actualScene;
    Button plus;
    Button minus;
    Label title = new Label();

    Image imageWarrior = new Image("images/Warrior.png", 32, 32, true, true);
    ImagePattern warriorPattern = new ImagePattern(imageWarrior);
    ImageView warriorView = new ImageView(imageWarrior);
    Image imageRanger = new Image("images/Ranger.png", 32, 32, true, true);
    ImagePattern rangerPattern = new ImagePattern(imageRanger);
    ImageView rangerView = new ImageView(imageRanger);
    Image imageMage = new Image("images/Mage.png", 32, 32, true, true);
    ImagePattern magePattern = new ImagePattern(imageMage);
    ImageView mageView = new ImageView(imageMage);
    Image imageTourist = new Image("images/Tourist.png", 32, 32, true, true);
    ImagePattern touristPattern = new ImagePattern(imageTourist);
    ImageView touristView = new ImageView(imageTourist);
    Image imageStudent = new Image("images/Student.png", 32, 32, true, true);
    ImagePattern studentPattern = new ImagePattern(imageStudent);
    ImageView studentView = new ImageView(imageStudent);
    Image imageProf = new Image("images/Prof.png", 32, 32, true, true);
    ImagePattern profPattern = new ImagePattern(imageProf);
    ImageView profView = new ImageView(imageProf);

    Image warriorFace = new Image("images/WarriorFace.png", 200, 200 ,true, true);
    ImageView warriorFaceView = new ImageView(warriorFace);
    Image mageFace = new Image("images/MageFace.png", 200, 200 ,true, true);
    ImageView mageFaceView = new ImageView(mageFace);
    Image rangerFace = new Image("images/RangerFace.png", 200, 200,true, true);
    ImageView rangerFaceView = new ImageView(rangerFace);
    Image profFace = new Image("images/ProfFace.png", 200, 200 ,true, true);
    ImageView profFaceView = new ImageView(profFace);
    Image studentFace = new Image("images/StudentFace.png", 200, 200 ,true, true);
    ImageView studentFaceView = new ImageView(studentFace);
    Image touristFace = new Image("images/TouristFace.png", 200, 200 ,true, true);
    ImageView touristFaceView = new ImageView(touristFace);
    Image emptyFace = new Image("images/EmptyFace.png",200, 200, true, true);
    ImageView emptyFaceView = new ImageView(emptyFace);

    Image str = new Image("images/Strength.png", 40,40, true, true);
    ImageView strView = new ImageView(str);
    Image dex = new Image("images/Dexterity.png", 40, 40, true, true);
    ImageView dexView = new ImageView(dex);
    Image wis = new Image("images/Wisdom.png", 40, 40, true, true);
    ImageView wisView = new ImageView(wis);
    Image health = new Image("images/Health.png", 40, 40, true, true);
    ImageView healthView = new ImageView(health);
    Image classPic = new Image("images/ClassPic.png", 40, 40, true, true);
    ImageView classPicView = new ImageView(classPic);


    Button warrior = new Button("Warrior", warriorView);
    Button ranger = new Button("Ranger", rangerView);
    Button mage = new Button("Mage", mageView);
    Button tourist = new Button("Tourist", touristView);
    Button student = new Button("Student", studentView);
    Button prof = new Button("Prof", profView);

    VBox main = new VBox();



    public SingleplayerGameLobbyScene(){


        titleLabel = ViewUtils.newH1("Singleplayer");
        back = ViewUtils.newButton("Back");
        start = ViewUtils.newButton("Start Game");
        
        chooseClass.add(warrior,0,0);
        chooseClass.add(ranger,0,1);
        chooseClass.add(mage,1,0);
        chooseClass.add(tourist,1,1);
        chooseClass.add(student,2,0);
        chooseClass.add(prof,2,1);



        // new Layout
        plus = ViewUtils.newButton("", "rightArrow");
		plus.setPrefWidth(100);
		minus = ViewUtils.newButton("", "leftArrow");
		minus.setPrefWidth(100);



        plus.setOnAction(event -> {plus();
            this.setCenter(getActualClass());
            mediaplayer.playClick();
        });


        minus.setOnAction(event -> {minus();
            this.setCenter(getActualClass());
            mediaplayer.playClick();
        });


        VBox left = new VBox();
        left.setAlignment(Pos.CENTER_LEFT);
        left.getChildren().addAll(warriorFaceView);
        left.setOpacity(0.5);

        VBox mid = new VBox();
        mid.setAlignment(Pos.CENTER);
        mid.getChildren().addAll(mageFaceView);

        VBox right = new VBox();
        right.setAlignment(Pos.CENTER_RIGHT);
        right.getChildren().addAll(rangerFaceView);
        right.setOpacity(0.5);





        //Create Boxes
        HBox hBox = new HBox(titleLabel);
        hBox.setAlignment(Pos.CENTER);

        HBox choose = new HBox(chooseClass);
        choose.setAlignment(Pos.CENTER);

        HBox character = new HBox(20);
        character.getChildren().addAll(minus,left,mid,right,plus);
        character.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(hBox, choose, back, start);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        //add Boxes to BorderPane


        HBox actions = new HBox(back, start);
        actions.setAlignment(Pos.CENTER);
        actions.setSpacing(10);

        this.setCenter(getActualClass());
        this.setBottom(actions);
        this.setTop(title);


        actualScene = ViewUtils.createScene(this);
        ViewUtils.configureScene(actualScene);
    }



    public HBox getActualClass() {

        Label classInfoLeft = new Label();
        classInfoLeft.setFont(new Font("AR DESTINE", 25));
        classInfoLeft.setTextFill(Color.GRAY);
        Label classInfoRight = new Label();
        classInfoRight.setFont(new Font("AR DESTINE", 25));
        classInfoRight.setTextFill(Color.WHITE);
        Label classInfoMid = new Label();
        classInfoMid.setFont(new Font("AR DESTINE", 25));
        classInfoMid.setTextFill(Color.WHITE);
        HBox blabla = new HBox(20);
        blabla.setAlignment(Pos.CENTER);
        VBox left = new VBox();
        left.setAlignment(Pos.CENTER_LEFT);
        left.setOpacity(0.5);
        Rectangle rect = new Rectangle();
        rect.setHeight(40);
        rect.setWidth(40);




        Label warriorClass = new Label("Warrior");
        warriorClass.setTextFill(Color.WHITE);
        warriorClass.setFont(new Font("AR DESTINE", 25));
        Label warriorStr = new Label("8");
        warriorStr.setTextFill(Color.WHITE);
        warriorStr.setFont(new Font("AR DESTINE", 25));
        Label warriorDex = new Label("4");
        warriorDex.setTextFill(Color.WHITE);
        warriorDex.setFont(new Font("AR DESTINE", 25));
        Label warriorWis = new Label("1");
        warriorWis.setTextFill(Color.WHITE);
        warriorWis.setFont(new Font("AR DESTINE", 25));
        Label warriorHealth = new Label("8");
        warriorHealth.setTextFill(Color.WHITE);
        warriorHealth.setFont(new Font("AR DESTINE", 25));
        HBox warriorClassBox = new HBox(classPicView,warriorClass);
        HBox warriorStrBox = new HBox(strView,warriorStr);
        HBox warriorDexBox = new HBox(dexView,warriorDex);
        HBox warriorWisBox = new HBox(wisView,warriorWis);
        HBox warriorHealthBox = new HBox(healthView, warriorHealth);



        /*
        Label className = new Label();
        Label str = new Label();
        Label dex = new Label();
        Label wis = new Label();
        Label health = new Label();

        HBox classBox = new HBox();
        className = new Label("Warrior");
        classBox.getChildren().addAll(classPicView,className);
        HBox strBox = new HBox();
        str = new Label("Str: 8");
        strBox.getChildren().addAll(strView,str);
        HBox dexBox = new HBox();
        dex = new Label("Dex: 4");
        dexBox.getChildren().addAll(dexView,dex);
        HBox wisBox = new HBox();
        wis = new Label("Wis: 1");
        wisBox.getChildren().addAll(wisView,wis);
        HBox healthBox = new HBox();
        health = new Label("HP: 8");
        healthBox.getChildren().addAll(healthView,health);
        VBox warriorBox = new VBox();
        warriorBox.getChildren().addAll(classBox,strBox,dexBox,wisBox,healthBox);
        */


        VBox mid = new VBox();
        mid.setAlignment(Pos.CENTER);

        VBox right = new VBox();
        right.setAlignment(Pos.CENTER_RIGHT);
        right.setOpacity(0.5);

        switch (x.get()) {

            case 0:
                classInfoLeft.setText("Class\nMelee\nRanged\nMagic\nHealth");
                classInfoMid.setText("Warrior\nStrength: 8\nDexterity: 4\nWisdom: 1\nHitpoints: 8");
                classInfoRight.setText("Mage\nStrength: 3\nDexterity: 4\nWisdom: 10\nHitpoints: 4");
                emptyFaceView.setOpacity(0.1);


                Label str = new Label("Melee");
                str.setFont(new Font("AR DESTINE", 25));
                str.setTextFill(Color.WHITE);
                Label dex = new Label("Ranged");
                dex.setFont(new Font("AR DESTINE", 25));
                dex.setTextFill(Color.WHITE);
                Label wis = new Label("Magic");
                wis.setFont(new Font("AR DESTINE", 25));
                wis.setTextFill(Color.WHITE);
                Label className = new Label("Class");
                className.setTextFill(Color.WHITE);
                className.setFont(new Font("AR DESTINE", 25));
                Label hpName = new Label("Health");
                hpName.setTextFill(Color.WHITE);
                hpName.setFont(new Font("AR DESTINE", 25));
                HBox classBox = new HBox();
                classBox.getChildren().addAll(classPicView, className);
                HBox strBox = new HBox();
                strBox.getChildren().addAll(strView,str);
                HBox dexBox = new HBox();
                dexBox.getChildren().addAll(dexView,dex);
                HBox wisBox = new HBox();
                wisBox.getChildren().addAll(wisView,wis);
                HBox healthBox = new HBox();
                healthBox.getChildren().addAll(healthView,hpName);

                left.getChildren().addAll(emptyFaceView,classBox,strBox, dexBox, wisBox,healthBox);
                left.setAlignment(Pos.CENTER);
                mid.getChildren().addAll(warriorFaceView,classInfoMid);
                right.getChildren().addAll(mageFaceView,classInfoRight);

                blabla.getChildren().addAll(minus,left,mid,right,plus);break;

            case 1:

                classInfoLeft.setText("Warrior\nStrength: 8\nDexterity: 4\nWisdom: 1\nHitpoints: 8" );
                classInfoMid.setText("Mage\nStrength: 3\nDexterity: 4\nWisdom: 10\nHitpoints: 4");
                classInfoRight.setText("Ranger\nStrength: 3\nDexterity: 9\nWisdom: 2\nHitpoints: 7");
                left.getChildren().addAll(warriorFaceView, classInfoLeft);
                mid.getChildren().addAll(mageFaceView,classInfoMid);
                right.getChildren().addAll(rangerFaceView,classInfoRight);

                blabla.getChildren().addAll(minus,left,mid,right,plus);break;

            case 2:

                classInfoLeft.setText("Mage\nStrength: 3\nDexterity: 4\nWisdom: 10\nHitpoints: 4");
                classInfoMid.setText("Ranger\nStrength: 3\nDexterity: 9\nWisdom: 2\nHitpoints: 7");
                classInfoRight.setText("Professor\nStrength: 3\nDexterity: 3\nWisdom: 9\nHitpoints: 6 ");
                left.getChildren().addAll(mageFaceView, classInfoLeft);
                mid.getChildren().addAll(rangerFaceView,classInfoMid);
                right.getChildren().addAll(profFaceView,classInfoRight);

                blabla.getChildren().addAll(minus,left,mid,right,plus);break;

            case 3:

                classInfoLeft.setText("Ranger\nStrength: 3\nDexterity: 9\nWisdom: 2\nHitpoints: 7");
                classInfoMid.setText("Professor\nStrength: 3\nDexterity: 3\nWisdom: 9\nHitpoints: 6");
                classInfoRight.setText("Student\nStrength: 5\nDexterity: 5\nWisdom: 5\nHitpoints: 6");
                left.getChildren().addAll(rangerFaceView,classInfoLeft);
                mid.getChildren().addAll(profFaceView,classInfoMid);
                right.getChildren().addAll(studentFaceView,classInfoRight);

                blabla.getChildren().addAll(minus,left,mid,right,plus);break;

            case 4:

                classInfoLeft.setText("Professor\nStrength: 3\nDexterity: 3\nWisdom: 9\nHitpoints: 6 ");
                classInfoMid.setText("Student\nStrength: 5\nDexterity: 5\nWisdom: 5\nHitpoints: 6");
                classInfoRight.setText("Tourist\nStrength: 1\nDexterity: 1\nWisdom: 1\nHitpoints: 18");
                left.getChildren().addAll(profFaceView,classInfoLeft);
                mid.getChildren().addAll(studentFaceView,classInfoMid);
                right.getChildren().addAll(touristFaceView,classInfoRight);

                blabla.getChildren().addAll(minus,left,mid,right,plus);break;

            case 5:


               str = new Label("Melee");
                str.setFont(new Font("AR DESTINE", 25));
                str.setTextFill(Color.WHITE);
                dex = new Label("Ranged");
                dex.setFont(new Font("AR DESTINE", 25));
                dex.setTextFill(Color.WHITE);
               wis = new Label("Magic");
                wis.setFont(new Font("AR DESTINE", 25));
                wis.setTextFill(Color.WHITE);
               className = new Label("Class");
                className.setTextFill(Color.WHITE);
                className.setFont(new Font("AR DESTINE", 25));
               hpName = new Label("Health");
                hpName.setTextFill(Color.WHITE);
                hpName.setFont(new Font("AR DESTINE", 25));
               classBox = new HBox();
                classBox.getChildren().addAll(classPicView, className);
               strBox = new HBox();
                strBox.getChildren().addAll(strView,str);
                 dexBox = new HBox();
                dexBox.getChildren().addAll(dexView,dex);
                wisBox = new HBox();
                wisBox.getChildren().addAll(wisView,wis);
                healthBox = new HBox();
                healthBox.getChildren().addAll(healthView,hpName);

                classInfoLeft.setText("Student\nStrength: 5\nDexterity: 5\nWisdom: 5\nHitpoints: 6");
                classInfoMid.setText("Tourist\nStrength: 1\nDexterity: 1\nWisdom: 1\nHitpoints: 18");
                classInfoRight.setText("Class\nMelee\nRanged\nMagic\nHealth");
                emptyFaceView.setOpacity(0.1);
                left.getChildren().addAll(studentFaceView,classInfoLeft);
                mid.getChildren().addAll(touristFaceView,classInfoMid);
                right.getChildren().addAll(emptyFaceView,classBox,strBox, dexBox, wisBox,healthBox);
                right.setAlignment(Pos.CENTER);


                blabla.getChildren().addAll(minus,left,mid,right,plus);break;

        }
        return blabla;
    }




    public SimpleIntegerProperty getXProperty(){return x;}

    public void setXProperty(SimpleIntegerProperty x){this.x = x;}

    public int getXInt(){return x.get();
    }

    public void plus (){

        if(x.get()<5) {
            x.set(x.get() + 1);
        }

        }
    public void minus(){

        if(x.get()>0){
            x.set(x.get()-1);
        }
    }

    public Button getBack(){
        return back;
    }

    public Button getStart(){
        return start;
    }




    public Button getPlus(){return plus;}

    public Button getMinus(){return minus;
    }

   public Button getMage () {
        return mage;
    }

    public Button getWarrior (){
        return  warrior;
    }

    public Button getRanger () {
        return ranger;
    }

    public Button getTourist () {
        return tourist;
    }

    public Button getStudent () {
        return student;
    }

    public Button getProf () {
        return prof;
    }


    public Scene getActualScene() {
        return actualScene;
    }
}
