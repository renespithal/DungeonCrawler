package view.design.scenes.serverRoom;

import util.ViewUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;

/**
 * Created by Rene on 07.01.2016.
 */
public class ServerRoomScene extends BorderPane {

    //controls

    GridPane chooseClass = new GridPane();
    private Label titleLabel;
    private Button connect;
    private Button create;
    private Button mainMenu;
    private Scene actualScene;

    private ScrollPane sp;

    Image imageWarrior = new Image("file:src/images/Warrior.png", 32, 32, true, true);
    ImagePattern warriorPattern = new ImagePattern(imageWarrior);
    ImageView warriorView = new ImageView(imageWarrior);
    Image imageRanger = new Image("file:src/images/Ranger.png", 32, 32, true, true);
    ImagePattern rangerPattern = new ImagePattern(imageRanger);
    ImageView rangerView = new ImageView(imageRanger);
    Image imageMage = new Image("file:src/images/Mage.png", 32, 32, true, true);
    ImagePattern magePattern = new ImagePattern(imageMage);
    ImageView mageView = new ImageView(imageMage);
    Image imageTourist = new Image("file:src/images/Tourist.png", 32, 32, true, true);
    ImagePattern touristPattern = new ImagePattern(imageTourist);
    ImageView touristView = new ImageView(imageTourist);
    Image imageStudent = new Image("file:src/images/Student.png", 32, 32, true, true);
    ImagePattern studentPattern = new ImagePattern(imageStudent);
    ImageView studentView = new ImageView(imageStudent);
    Image imageProf = new Image("file:src/images/Prof.png", 32, 32, true, true);
    ImagePattern profPattern = new ImagePattern(imageProf);
    ImageView profView = new ImageView(imageProf);

    Button warrior = new Button("Warrior", warriorView);
    Button ranger = new Button("Ranger", rangerView);
    Button mage = new Button("Mage", mageView);
    Button tourist = new Button("Tourist", touristView);
    Button student = new Button("Student", studentView);
    Button prof = new Button("Prof", profView);




    public ServerRoomScene(){

    	titleLabel = ViewUtils.newH1("Server Room");
        connect = ViewUtils.newButton("Join Game");
        mainMenu = ViewUtils.newButton("Back to Main");
        create = ViewUtils.newButton("Create Game");

        chooseClass.add(warrior,0,0);
        chooseClass.add(ranger,0,1);
        chooseClass.add(mage,1,0);
        chooseClass.add(tourist,1,1);
        chooseClass.add(student,2,0);
        chooseClass.add(prof,2,1);

        VBox serverList = new VBox();

        sp = new ScrollPane();
        sp.setContent(serverList);

        //Create Boxes
        HBox hBox = new HBox(titleLabel);
        hBox.setAlignment(Pos.CENTER);

        HBox serverListAndClass = new HBox(sp, chooseClass);
        serverListAndClass.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(hBox, serverListAndClass, create, connect, mainMenu);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        //add Boxes to BorderPane
        this.setCenter(vBox);

        actualScene = new Scene(this);
        ViewUtils.configureScene(actualScene);


    }

    public void setConnect(Button connect) {
        this.connect = connect;
    }

    public void setCreate(Button create) {
        this.create = create;
    }

    public void setMainMenu(Button mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Scene getActualScene() {
        return actualScene;
    }

    public void setActualScene(Scene actualScene) {
        this.actualScene = actualScene;
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

    public Button getConnect(){
        return connect;
    }

    public Button getMainMenu(){
        return mainMenu;
    }

    public Button getCreate(){
        return create;
    }

}