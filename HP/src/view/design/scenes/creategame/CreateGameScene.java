package view.design.scenes.creategame;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import util.ViewUtils;

/**
 * Created by Felix on 15.01.2016.
 */
public class CreateGameScene extends BorderPane {

	GridPane chooseClass = new GridPane();

	SimpleIntegerProperty x = new SimpleIntegerProperty(0);

	private Button back;
	private Button proceed;
	private Button mainMenu;
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

	Image warriorFace = new Image("images/WarriorFace.png", 200, 200, true, true);
	ImageView warriorFaceView = new ImageView(warriorFace);
	Image mageFace = new Image("images/MageFace.png", 200, 200, true, true);
	ImageView mageFaceView = new ImageView(mageFace);
	Image rangerFace = new Image("images/RangerFace.png", 200, 200, true, true);
	ImageView rangerFaceView = new ImageView(rangerFace);
	Image profFace = new Image("images/ProfFace.png", 200, 200, true, true);
	ImageView profFaceView = new ImageView(profFace);
	Image studentFace = new Image("images/StudentFace.png", 200, 200, true, true);
	ImageView studentFaceView = new ImageView(studentFace);
	Image touristFace = new Image("images/TouristFace.png", 200, 200, true, true);
	ImageView touristFaceView = new ImageView(touristFace);
	Image emptyFace = new Image("images/EmptyFace.png", 200, 200, true, true);
	ImageView emptyFaceView = new ImageView(emptyFace);

	Button warrior = new Button("Warrior", warriorView);
	Button ranger = new Button("Ranger", rangerView);
	Button mage = new Button("Mage", mageView);
	Button tourist = new Button("Tourist", touristView);
	Button student = new Button("Student", studentView);
	Button prof = new Button("Prof", profView);

	VBox main = new VBox();

	public CreateGameScene() {

		titleLabel = ViewUtils.newH1("Create Game");
		back = ViewUtils.newButton("Back");
		proceed = ViewUtils.newButton("Create");
		mainMenu = ViewUtils.newButton("Back to Menu");

		chooseClass.add(warrior, 0, 0);
		chooseClass.add(ranger, 0, 1);
		chooseClass.add(mage, 1, 0);
		chooseClass.add(tourist, 1, 1);
		chooseClass.add(student, 2, 0);
		chooseClass.add(prof, 2, 1);

		// new Layout
		plus = ViewUtils.newButton("", "rightArrow");
		plus.setPrefWidth(100);
		minus = ViewUtils.newButton("", "leftArrow");
		minus.setPrefWidth(100);
		plus.setOnAction(event -> {
			plus();
			this.setCenter(getActualClass());
		});

		minus.setOnAction(event -> {
			minus();
			this.setCenter(getActualClass());
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

		// Create Boxes
		HBox hBox = new HBox(titleLabel);
		hBox.setAlignment(Pos.CENTER);

		HBox choose = new HBox(chooseClass);
		choose.setAlignment(Pos.CENTER);

		HBox character = new HBox(20);
		character.getChildren().addAll(minus, left, mid, right, plus);
		character.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(hBox, choose, proceed, back);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(15);

		// add Boxes to BorderPane

		HBox actions = new HBox(back, proceed);
		actions.setSpacing(10);
		actions.setAlignment(Pos.CENTER);

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

		VBox mid = new VBox();
		mid.setAlignment(Pos.CENTER);

		VBox right = new VBox();
		right.setAlignment(Pos.CENTER_RIGHT);
		right.setOpacity(0.5);

		switch (x.get()) {

		case 0:
			classInfoLeft.setText("Class\nMelee\nRanged\nMagic\nHealth");
			classInfoMid.setText("Warrior\nStr: 8\nDex: 4\nWis: 1\nHP: 8");
			classInfoRight.setText("Mage\nStr: 3\nDex: 4\nWis: 10\nHP: 4");
			emptyFaceView.setOpacity(0.1);
			left.getChildren().addAll(emptyFaceView, classInfoLeft);
			left.setAlignment(Pos.CENTER);
			mid.getChildren().addAll(warriorFaceView, classInfoMid);
			right.getChildren().addAll(mageFaceView, classInfoRight);

			blabla.getChildren().addAll(minus, left, mid, right, plus);
			break;

		case 1:
			classInfoLeft.setText("Warrior\nStr: 8\nDex: 4\nWis: 1\nHP: 8");
			classInfoMid.setText("Mage\nStr: 3\nDex: 4\nWis: 10\nHP: 4");
			classInfoRight.setText("Ranger\nStr: 3\nDex: 9\nWis: 2\nHP: 7");
			left.getChildren().addAll(warriorFaceView, classInfoLeft);
			mid.getChildren().addAll(mageFaceView, classInfoMid);
			right.getChildren().addAll(rangerFaceView, classInfoRight);

			blabla.getChildren().addAll(minus, left, mid, right, plus);
			break;

		case 2:

			classInfoLeft.setText("Mage\nStr: 3\nDex: 4\nWis: 10\nHP: 4");
			classInfoMid.setText("Ranger\nStr: 3\nDex: 9\nWis: 2\nHP: 7");
			classInfoRight.setText("Professor \n Str: 3 \n Dex: 3 \n Wis: 9 \n HP: 6");
			left.getChildren().addAll(mageFaceView, classInfoLeft);
			mid.getChildren().addAll(rangerFaceView, classInfoMid);
			right.getChildren().addAll(profFaceView, classInfoRight);

			blabla.getChildren().addAll(minus, left, mid, right, plus);
			break;

		case 3:

			classInfoLeft.setText("Ranger\nStr: 3\nDex: 9\nWis: 2\nHP: 7");
			classInfoMid.setText("Professor\nStr: 3\nDex: 3\nWis: 9\nHP: 6 ");
			classInfoRight.setText("Student\nStr: 5\nDex: 5\nWis: 5\nHP: 6");
			left.getChildren().addAll(rangerFaceView, classInfoLeft);
			mid.getChildren().addAll(profFaceView, classInfoMid);
			right.getChildren().addAll(studentFaceView, classInfoRight);

			blabla.getChildren().addAll(minus, left, mid, right, plus);
			break;

		case 4:

			classInfoLeft.setText("Professor\nStr: 3\nDex: 3\nWis: 9\nHP: 6 ");
			classInfoMid.setText("Student\nStr: 5\nDex: 5\nWis: 5\nHP: 6");
			classInfoRight.setText("Tourist\nStr: 1\nDex: 1\nWis: 1\nHP: 18");
			left.getChildren().addAll(profFaceView, classInfoLeft);
			mid.getChildren().addAll(studentFaceView, classInfoMid);
			right.getChildren().addAll(touristFaceView, classInfoRight);

			blabla.getChildren().addAll(minus, left, mid, right, plus);
			break;

		case 5:

			classInfoLeft.setText("Student\nStr: 5\nDex: 5\nWis: 5\nHP: 6");
			classInfoMid.setText("Tourist\nStr: 1\nDex: 1\nWis: 1\nHP: 18");
			classInfoRight.setText("Class\nMelee\nRanged\nMagic\nHealth");
			emptyFaceView.setOpacity(0.1);
			left.getChildren().addAll(studentFaceView, classInfoLeft);
			mid.getChildren().addAll(touristFaceView, classInfoMid);
			right.getChildren().addAll(emptyFaceView, classInfoRight);
			right.setAlignment(Pos.CENTER);

			blabla.getChildren().addAll(minus, left, mid, right, plus);
			break;

		}
		return blabla;
	}

	public SimpleIntegerProperty getXProperty() {
		return x;
	}

	public void setXProperty( SimpleIntegerProperty x ) {
		this.x = x;
	}

	public int getXInt() {
		return x.get();
	}

	public void plus() {

		if( x.get() < 5 ) {
			x.set(x.get() + 1);
		}
	}

	public void minus() {

		if( x.get() > 0 ) {
			x.set(x.get() - 1);
		}
	}

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene( Scene actualScene ) {
		this.actualScene = actualScene;
	}

	public Button getBack() {
		return back;
	}

	public void setBack( Button back ) {
		this.back = back;
	}

	public Button getProceed() {
		return proceed;
	}

	public void setProceed( Button proceed ) {
		this.proceed = proceed;
	}

	public Button getWarrior() {
		return warrior;
	}

	public void setWarrior( Button warrior ) {
		this.warrior = warrior;
	}

	public Button getRanger() {
		return ranger;
	}

	public void setRanger( Button ranger ) {
		this.ranger = ranger;
	}

	public Button getMage() {
		return mage;
	}

	public void setMage( Button mage ) {
		this.mage = mage;
	}

	public Button getTourist() {
		return tourist;
	}

	public void setTourist( Button tourist ) {
		this.tourist = tourist;
	}

	public Button getStudent() {
		return student;
	}

	public void setStudent( Button student ) {
		this.student = student;
	}

	public Button getProf() {
		return prof;
	}

	public void setProf( Button prof ) {
		this.prof = prof;
	}

	public Button getBackToMenu() {
		return mainMenu;
	}

}
