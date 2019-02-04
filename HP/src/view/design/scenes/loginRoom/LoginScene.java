package view.design.scenes.loginRoom;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ViewUtils;


/**
 * Created by Rene on 07.01.2016.
 */
public class LoginScene extends BorderPane {

    private Label titleLabel;
    private Button login,submit;
    final TextField nickname;
    private Button mainMenu;
    private Scene actualScene;


    public LoginScene() {

    	titleLabel = ViewUtils.newH1("Login");
        
        login = ViewUtils.newButton("Continue");
        login.setDisable(true);

        nickname = new TextField();
        nickname.setMaxWidth(200);
        nickname.setPromptText("Enter your nickname");
        
        mainMenu = ViewUtils.newButton("Back to Menu");
        submit = ViewUtils.newButton("submit");
        submit.setDisable(true);
        
        //Create Boxes
        HBox hBox = new HBox(titleLabel);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(hBox, nickname,submit,login, mainMenu);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        this.setCenter(vBox);

        actualScene = ViewUtils.createScene(this);
        ViewUtils.configureScene(actualScene);
    }

    public Scene getActualScene() {
        return actualScene;
    }

    public void setActualScene(Scene actualScene) {
        this.actualScene = actualScene;
    }

    public Button getLogin(){
        return login;
    }
    
    public TextField getNicknameField() {
    	return nickname;
    }

    public String getNickname(){
        return nickname.getText();
    }

    public Button getBackToMenu(){
        return mainMenu;
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }

    public void setLogin(Button login) {
        this.login = login;
    }

    public void setMainMenu(Button mainMenu) {
        this.mainMenu = mainMenu;
    }
}
