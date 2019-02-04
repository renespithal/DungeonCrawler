package view.designHP.scenes.choosePlayerHP;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entity.player.playersHP.*;
import util.ViewUtilsHP;

/**
 * Created by Jenny on 18.01.2016.
 */
public class ChoosePlayerSceneHP extends BorderPane {

	private Button back,choose,mainMenu;
	private VBox ba,ch, main;
	private Scene actualScene;

	private Button goLeft, goRight;

	private StringProperty wandName, dexPoints, strPoints, wisPoints, hpPoints;

	private String chooser;

	public ChoosePlayerSceneHP() {

		actualScene =  ViewUtilsHP.createScene(this);
		ViewUtilsHP.setBackground(this, "hp/media/background/ChoosePlayerHarryBG.jpg");
		this.chooser = "harry";

		ViewUtilsHP.configureCSS(actualScene);

		this.setBottom(createButton());
		this.setCenter(createStats());
		this.setRight(createRight());
		this.setLeft(createLeft());
		setHarry();
	}

	public VBox createStats() {
		initStatProperties();
		Label stats  = ViewUtilsHP.newH4("Stats");

		Label wand = ViewUtilsHP.newH5("Wand:");
		Label wandContent = ViewUtilsHP.newH6();
		wandContent.textProperty().bind(wandName);

		Label dex = ViewUtilsHP.newH5("Dexterity:");
		Label dexContent = ViewUtilsHP.newH6();
		dexContent.textProperty().bind(dexPoints);

		Label str = ViewUtilsHP.newH5("Strength:");
		Label strContent = ViewUtilsHP.newH6();
		strContent.textProperty().bind(strPoints);

		Label wis = ViewUtilsHP.newH5("Wisdom:");
		Label wisContent = ViewUtilsHP.newH6();
		wisContent.textProperty().bind(wisPoints);

		Label hp = ViewUtilsHP.newH5("Health Points:");
		Label hpContent = ViewUtilsHP.newH6();
		hpContent.textProperty().bind(hpPoints);

		VBox vBox1 = new VBox(wand, dex,str,wis,hp);
		VBox vBox2 = new VBox(wandContent,dexContent,strContent,wisContent,hpContent);
		vBox2.setPadding(new Insets(0,0,0,50));
		vBox2.setSpacing(4.5);

		VBox top = new VBox(stats);
		top.setPadding(new Insets(50,0,0,0));
		HBox bottom = new HBox(vBox1,vBox2);

		VBox result = new VBox(top,bottom);
		result.setPadding(new Insets(150,0,0,550));

		return result;
	}

	private void initStatProperties() {
		wandName = new SimpleStringProperty();
		dexPoints = new SimpleStringProperty();
		strPoints = new SimpleStringProperty();
		wisPoints = new SimpleStringProperty();
		hpPoints = new SimpleStringProperty();
	}

	public HBox createLeft() {
		goLeft = ViewUtilsHP.createButton("", "leftArrow");
		HBox left = new HBox(goLeft);
		left.setPadding(new Insets(250,0,0,-30));
		goLeft.setPrefWidth(128);
		goLeft.setPrefHeight(128);

		return left;
	}

	public HBox createRight() {

		goRight = ViewUtilsHP.createButton("", "rightArrow");
		HBox right = new HBox(goRight);
		right.setPadding(new Insets(250,-30,0,0));
		goRight.setPrefWidth(128);
		goRight.setPrefHeight(128);

		return right;
	}

	public HBox createButton() {
		back = ViewUtilsHP.createButton("Back");
		ba = ViewUtilsHP.buttonSurrounding(back);
		mainMenu = ViewUtilsHP.createButton("Back to menu");
		main = ViewUtilsHP.buttonSurrounding(mainMenu);
		choose = ViewUtilsHP.createButton("Choose");
		ch = ViewUtilsHP.buttonSurrounding(choose);

		HBox hBox1 = new HBox(main, ba);
		hBox1.setPadding(new Insets(0,200,0,0));
		HBox hBox2 = new HBox(ch);
		hBox1.setSpacing(100);
		hBox2.setSpacing(100);
		HBox hBox = new HBox(hBox1,hBox2);
		hBox.setSpacing(150);
		hBox.setPadding(new Insets(0,180,10,10));
		hBox.setAlignment(Pos.CENTER);

		return hBox;
	}


	/* Display right stats depending on chosen character */
	public void setHarry() {
		Harry p = new Harry();
		setWandName("Holly, Phoenix feather, 11'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setRon() {
		Ron p = new Ron();
		setWandName("Willow, Unicorn hair, 14'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setHermine() {
		Hermine p = new Hermine();
		setWandName("Vine, Dragon heartstring, 10 3/4'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setGinny() {
		Ginny p = new Ginny();
		setWandName("Ash, Unicorn hair, 12'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setNeville() {
		Neville p = new Neville();
		setWandName("Fir, Dreagon heartstring, 11'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setHagrid() {
		Hagrid p = new Hagrid();
		setWandName("Umbrella");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setDeumbledore() {
		Dumbledore p = new Dumbledore();
		setWandName("Elder, Thestral tail-hair, 11'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setMoody() {
		Moody p = new Moody();
		setWandName("Walnut, Dragon heartstring, 10'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}

	public void setSnape() {
		Snape p = new Snape();
		setWandName("Hawthorn, Unicorn hair, 10'");
		setDexPoints(p.getDex() + "");
		setHpPoints(p.getHpmax() + "");
		setWisPoints(p.getWis() + "");
		setStrPoints(p.getStr() + "");
	}


	/* Getter & Setter */

	public Button getBack() {
		return back;
	}

	public void setBack(Button back) {
		this.back = back;
	}

	public Button getChoose() {
		return choose;
	}

	public void setChoose(Button choose) {
		this.choose = choose;
	}

	public Button getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(Button mainMenu) {
		this.mainMenu = mainMenu;
	}

	public VBox getBa() {
		return ba;
	}

	public void setBa(VBox ba) {
		this.ba = ba;
	}

	public VBox getCh() {
		return ch;
	}

	public void setCh(VBox ch) {
		this.ch = ch;
	}

	public VBox getMain() {
		return main;
	}

	public void setMain(VBox main) {
		this.main = main;
	}

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene(Scene actualScene) {
		this.actualScene = actualScene;
	}

	public Button getGoLeft() {
		return goLeft;
	}

	public void setGoLeft(Button goLeft) {
		this.goLeft = goLeft;
	}

	public Button getGoRight() {
		return goRight;
	}

	public void setGoRight(Button goRight) {
		this.goRight = goRight;
	}

	public String getWandName() {
		return wandName.get();
	}

	public StringProperty wandNameProperty() {
		return wandName;
	}

	public void setWandName(String wandName) {
		this.wandName.set(wandName);
	}

	public String getDexPoints() {
		return dexPoints.get();
	}

	public StringProperty dexPointsProperty() {
		return dexPoints;
	}

	public void setDexPoints(String dexPoints) {
		this.dexPoints.set(dexPoints);
	}

	public String getStrPoints() {
		return strPoints.get();
	}

	public StringProperty strPointsProperty() {
		return strPoints;
	}

	public void setStrPoints(String strPoints) {
		this.strPoints.set(strPoints);
	}

	public String getWisPoints() {
		return wisPoints.get();
	}

	public StringProperty wisPointsProperty() {
		return wisPoints;
	}

	public void setWisPoints(String wisPoints) {
		this.wisPoints.set(wisPoints);
	}

	public String getHpPoints() {
		return hpPoints.get();
	}

	public StringProperty hpPointsProperty() {
		return hpPoints;
	}

	public void setHpPoints(String hpPoints) {
		this.hpPoints.set(hpPoints);
	}

	public String getChooser() {
		return chooser;
	}

	public void setChooser(String chooser) {
		this.chooser = chooser;
	}
}