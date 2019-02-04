package view.designHP.scenes.loginHP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.ViewUtils;
import util.ViewUtilsHP;

/**
 * Created by Rene on 07.01.2016.
 */
public class LoginSceneHP extends BorderPane {

	private Label titleLabel;
	private Button login, submit;
	private TextField nickname;
	private Button mainMenu,back;
	private Scene actualScene;

	private VBox ba, backMain, sub, cont;

	public LoginSceneHP() {

		this.actualScene = ViewUtilsHP.createScene(this);
		ViewUtilsHP.setBackground(this, "hp/media/background/loginBG.jpg");
		ViewUtilsHP.configureCSS(actualScene);

		this.setBottom(createButton());
		this.setCenter(createMainArea());
	}
	
	private GridPane createMainArea() {

        GridPane main = new GridPane();
        main.add(createLogin(), 0, 0);
        main.setPrefHeight(300);
        main.setAlignment(Pos.CENTER);
        return main;
	}

	public Pane createLogin() {

		Pane login = ViewUtils.newPanel(false, "Login");
        login.getChildren().add(createTextField());

        return login;
	}

	public TextField createTextField() {
		nickname = new TextField();
		nickname.setMaxWidth(200);
		nickname.setPromptText("Enter your nickname");
		return nickname;
	}

	public HBox createButton() {
		login = ViewUtilsHP.createButton("Continue");
		cont = ViewUtilsHP.buttonSurrounding(login);
		login.setDisable(true);

		mainMenu = ViewUtilsHP.createButton("Back to Menu");
		backMain = ViewUtilsHP.buttonSurrounding(mainMenu);
		back = ViewUtilsHP.createButton("Back");
		ba = ViewUtilsHP.buttonSurrounding(back);
		submit = ViewUtilsHP.createButton("Submit");
		sub = ViewUtilsHP.buttonSurrounding(submit);
		submit.setDisable(true);

		HBox hBox1 = new HBox(backMain,ba);
		HBox hBox2 = new HBox(sub, cont);
		hBox1.setSpacing(100);
		hBox2.setSpacing(100);
		HBox hBox = new HBox(hBox1,hBox2);
		hBox.setSpacing(280);
		hBox.setPadding(new Insets(0,0,10,100));
		hBox.setAlignment(Pos.CENTER);

		return hBox;
	}

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene( Scene actualScene ) {
		this.actualScene = actualScene;
	}

	public Button getLogin() {
		return login;
	}

	public TextField getNicknameField() {
		return nickname;
	}

	public String getNickname() {
		return nickname.getText();
	}

	public Button getBackToMenu() {
		return mainMenu;
	}

	public Button getSubmit() {
		return submit;
	}

	public void setSubmit( Button submit ) {
		this.submit = submit;
	}

	public void setLogin( Button login ) {
		this.login = login;
	}

	public void setMainMenu( Button mainMenu ) {
		this.mainMenu = mainMenu;
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	public void setNickname(TextField nickname) {
		this.nickname = nickname;
	}

	public Button getMainMenu() {
		return mainMenu;
	}

	public Button getBack() {
		return back;
	}

	public void setBack(Button back) {
		this.back = back;
	}

	public VBox getBa() {
		return ba;
	}

	public void setBa(VBox ba) {
		this.ba = ba;
	}

	public VBox getBackMain() {
		return backMain;
	}

	public void setBackMain(VBox backMain) {
		this.backMain = backMain;
	}

	public VBox getSub() {
		return sub;
	}

	public void setSub(VBox sub) {
		this.sub = sub;
	}

	public VBox getCont() {
		return cont;
	}

	public void setCont(VBox cont) {
		this.cont = cont;
	}
}
