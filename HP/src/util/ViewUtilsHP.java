package util;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Created by Jenny on 27.01.2016.
 */
public class ViewUtilsHP {

	/* Configure Styles */
	public static void configureCSS( Scene scene ) {

		String stylesUrl = "hp/css/hp.css";
		scene.getStylesheets().add(stylesUrl);
		scene.getRoot().setId("scene");
	}

	private static Image configureLogo() {
		return new Image("hp/media/logo/DungeonCrawlerHPlogo.png");
	}

	private static Image configurePlay() {
		return new Image("hp\\media\\play.png");
	}

	private static Image configurePause() {
		return new Image("hp\\media\\pause.png"); /* TODO: pause */
	}

	private static String configureButton() {
		return "-fx-background-color:#090a0c,linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),linear-gradient(#20262b, #191d22),tradial-gradient(center 50% 0%radius 100%, rgba(114,131,148,0.9),rgba(255,255,255,0))-fx-background-radius: 5,4,3,5;-fx-background-insets: 0,1,2,0;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );-fx-text-fill: linear-gradient(white, #d0d0d0);-fx-text-fill: #d49c25;-fx-padding: 10 20 10 20;";
	}

	private static Image configureUnderlining() {
		return new Image("hp/media/underlining.png");
	}

	public static void configureChat() {

	}

	/* set Images */
	public static void setBackground( Pane root, String src ) {

		BackgroundImage image = new BackgroundImage(new Image(src), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		root.setBackground(new Background(image));
	}

	public static HBox setLogo() {
		BackgroundImage image = new BackgroundImage(configureLogo(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(image);

		HBox logo = new HBox();
		logo.setBackground(background);
		return logo;

	}

	/* Create */
	public static Scene createScene( Pane root ) {
		Scene scene = new Scene(root, 1200, 676);
		return scene;
	}

	public static HBox createMenuPanel() {
		HBox hBox = new HBox();

		//hBox.setStyle("-fx-background-color: #070616;-fx-border-style: solid;-fx-border-color: #d49c25");
		hBox.setAlignment(Pos.CENTER);
		hBox.setPrefHeight(200);

		return hBox;
	}

	public static Button createButton( String text ) {
		Button button = new Button(text);
		String stylesUrl = "hp/css/hp.css";
		button.getStylesheets().add(stylesUrl);
		button.setTextFill(Color.WHITE);
		button.setAlignment(Pos.CENTER);
		
		return button;
	}
	
	public static Button newMainButton( String text, String styleClass ) {

		Button button = new Button(text);
		button.setPrefWidth(200);
		button.setPrefHeight(48);
		button.setTextFill(Color.WHITE);
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle( MouseEvent event ) {
				button.setTextFill(Color.web("#FFF7A4"));
			}
		});
		button.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle( MouseEvent event ) {
				button.setTextFill(Color.WHITE);
			}
		});
		button.setAlignment(Pos.CENTER);

		button.getStyleClass().add(styleClass);

		return button;
	}

	public static VBox buttonSurrounding(Button button) {
		Image image = configureUnderlining();
		ImageView view = new ImageView(image);
		view.setFitWidth(130);
		view.setVisible(false);


		button.setOnMouseEntered(e -> {
			view.setVisible(true);
			button.setTextFill(Color.web("#96857E"));
		});

		button.setOnMouseExited(e -> {
			view.setVisible(false);
			button.setTextFill(Color.WHITE);
		});
		VBox vBox = new VBox(button, view);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(-10);
		return vBox;
	}
	
	public static Button createButton( String text, String styleClass ) {
		Button button = createButton(text);
		button.getStyleClass().add(styleClass);

		return button;
	}

	public static HBox createPlaceholder( int width, int height ) {
		HBox hbox = new HBox();

		hbox.setPrefWidth(width);
		hbox.setPrefHeight(height);

		return hbox;
	}

	public static <S, T> TableColumn<S, T> createColumn(TableView<S> table, String label, String property, int width ) {

		TableColumn<S, T> column = new TableColumn<S, T>(label);
		column.setMinWidth(width);
		column.setCellValueFactory(new PropertyValueFactory<S, T>(property));

		table.getColumns().add(column);

		return column;
	}
	
	public static Button newButton( String text, String styleClass ) {
		Button button = createButton(text);
		button.getStyleClass().add(styleClass);

		return button;
	}

	public static Button createPlayButton() {
		Button play = new Button();

		BackgroundImage image = new BackgroundImage(configurePlay(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(image);
		play.setBackground(background);

		return play;
	}

	public static Button createPauseButton() {
		Button pause = new Button();

		BackgroundImage image = new BackgroundImage(configurePlay(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(image);
		pause.setBackground(background);

		return pause;
	}

	public static TextArea createTextArea() {
		TextArea area = new TextArea();
		area.setPrefWidth(450);
		area.setEditable(false);
		area.setId("area");

		return area;
	}

	public static Pane newPanel( boolean horizontal, String title ) {

		Pane pane = newPanel(horizontal);
		Label label = ViewUtilsHP.newH2(title);
		pane.getChildren().add(label);

		return pane;
	}

	public static Pane newPanel(boolean horizontal ) {

		Pane pane = horizontal ? new HBox(10) : new VBox(10);
		pane.setPadding(new Insets(10, 0, 0, 10));

		return pane;
	}

	public static Text createH1( String content ) {
		Text text = new Text(content);

		text.setFill(Color.LIGHTGREY);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(new Font(20));

		return text;
	}

	public static Label newH1( String text ) {

		Label label = new Label(text);
		label.setTextFill(Color.web("#FFF7A4"));
		label.setFont(new Font("Segoe UI Light", 40));

		return label;
	}

	public static Label newH2( String text ) {

		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		label.setFont(new Font("Segoe UI Light", 20));

		return label;
	}

	public static Label newH3( String text, int textSize ) {

		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		label.setFont(new Font("Segoe UI Light", textSize));

		return label;
	}

	public static Label newH4( String text) {

		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		label.setFont(new Font("Segoe UI Light", 30));

		return label;
	}

	public static Label newH5( String text) {

		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		label.setFont(new Font("Harry P", 30));
		label.setLineSpacing(4.0);

		return label;
	}

	public static Label newH6() {

		Label label = new Label();
		label.setTextFill(Color.WHITE);
		label.setFont(new Font("Segoe UI Light", 20));
		label.setLineSpacing(7.0);

		return label;
	}

	/* Transition */
	public static FadeTransition createFade( int dur, Node node, boolean reverse, double fromOpacity, double toOpacity ) {
		FadeTransition fade = new FadeTransition(Duration.millis(dur), node);

		fade.setAutoReverse(reverse);
		fade.setByValue(fromOpacity);
		fade.setToValue(toOpacity);

		return fade;
	}

}
