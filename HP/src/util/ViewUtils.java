package util;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

public class ViewUtils {

	private static final Color BUTTON_COLOUR = Color.BLACK;

	public static void configureScene( Scene scene ) {

		String stylesUrl = "styles/scene-styles.css";
		scene.getStylesheets().add(stylesUrl);
		scene.getRoot().setId("scene");
	}

	public static Scene createScene( Pane root ) {

		Scene scene = new Scene(root, 1156, 650);
		return scene;

	}

	public static Pane createHeader( String title ) {

		Pane pane = newPanel(true);
		Label label = ViewUtils.newH1(title);
		pane.getChildren().add(label);

		return pane;
	}

	public static Pane newPanel( boolean horizontal ) {

		Pane pane = horizontal ? new HBox(10) : new VBox(10);
		pane.setPadding(new Insets(10, 0, 0, 10));

		return pane;
	}

	public static Pane newPanel( boolean horizontal, String title ) {

		Pane pane = newPanel(horizontal);
		Label label = ViewUtils.newH2(title);
		pane.getChildren().add(label);

		return pane;
	}

	public static Button newSPButton( String text ) {
		Button button = new Button(text);
		button.setPrefWidth(150);
		button.setPrefHeight(64);
		button.setId("0");
		button.setTextFill(Color.WHITE);
		button.setFont(new Font("Segoe UI Light", 25));

		button.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle( MouseEvent event ) {
				button.setTextFill(Color.web("#FFF7A4"));
			}
		});
		button.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle( MouseEvent event ) {

				if( button.getId().equals("0") ) {
					button.setTextFill(Color.WHITE);
				}
			}
		});
		button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		return button;
	}

	public static Button newButton( String text ) {

		Button button = new Button(text);
		button.setPrefHeight(48);
		button.setTextFill(BUTTON_COLOUR);
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle( MouseEvent event ) {
				button.setTextFill(Color.web("#FFF7A4"));
			}
		});
		button.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle( MouseEvent event ) {
				button.setTextFill(BUTTON_COLOUR);
			}
		});
		button.setAlignment(Pos.CENTER);

		return button;
	}

	public static Button newButton( String text, String styleClass ) {
		Button button = newButton(text);
		button.getStyleClass().add(styleClass);

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
		label.setTextFill(BUTTON_COLOUR);
		label.setFont(new Font("Segoe UI Light", textSize));

		return label;
	}

	public static Label newHSP( String text, int textSize ) {

		Label label = new Label(text);
		label.setTextFill(Color.BLACK);
		label.setFont(new Font("Segoe UI Light", textSize));

		return label;
	}

	public static Arc newArc (){
		Arc arc = new Arc(80, 50, 270, 270, 0, 90);
		arc.setType(ArcType.OPEN);
		arc.setStrokeWidth(5);
		arc.setStroke(Color.LIGHTGRAY);
		arc.setStrokeType(StrokeType.INSIDE);
		arc.setFill(null);
		arc.setRotate(-180.0);

		return arc;

	}

	public static <S, T> TableColumn<S, T> createColumn( TableView<S> table, String label, String property, int width ) {

		TableColumn<S, T> column = new TableColumn<S, T>(label);
		column.setMinWidth(width);
		column.setCellValueFactory(new PropertyValueFactory<S, T>(property));
		column.getStyleClass().add("table");

		table.getColumns().add(column);

		return column;
	}

	public static Alert alertConfirm( String title, String msg ) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(msg);
		return alert;
	}

	public static Alert alertExit() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm exit");
		alert.setHeaderText("Are you sure you want to quit the game?");
		alert.setContentText("Your connection will be disconnected");
		return alert;
	}

	public static Alert alertError( String title, String msg ) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(msg);
		return alert;
	}

	public static TextArea createTextArea() {
		TextArea area = new TextArea();
		area.setPrefWidth(450);
		//area.setPrefHeight(800);
		area.setEditable(false);

		return area;
	}

}
