package view.design.scenes.infoHelp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import util.ViewUtils;
import view.design.scenes.singleplayerGame.ImageHolder;

/**
 * Created by Felix on 17.02.2016.
 */
public class InfoHelpScene extends BorderPane {

    private Scene actualScene;
    private Button backButton;
    Rectangle infoRect = new Rectangle(500,263);
    Image info500 = new Image((getClass().getResource("/images/TUTORIAL500.gif").toExternalForm()), 500, 263, true, true);
    ImagePattern info500Pattern = new ImagePattern(info500);


    public InfoHelpScene() {
        infoRect.setFill(info500Pattern);
        backButton = new Button("Back to Menu");
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(infoRect,backButton);
        vBox.setAlignment(Pos.CENTER);
        this.setCenter(vBox);






        String herbert = ("Controls \n" +
                "if you don't want to use your mouse, press 1 for \"move\",\n" +
                "press 2 for \"use\" and 3 for \"attack\". To submit your chosen\n"+
                "turn, press \"Enter\". \n \n "+ " " );

       /* -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);
        -fx-stroke: black;
        -fx-stroke-width: 1;
        );*/
        Label label = new Label(herbert);
        label.setWrapText(true);
        label.setStyle("-fx-font-family: \"Segoe UI Semibold\"; -fx-font-size: 20; -fx-text-fill: white;-fx-background-color: transparent;");





        actualScene = ViewUtils.createScene(this);
        ViewUtils.configureScene(actualScene);

    }

    public Scene getActualScene() {
        return actualScene;
    }

    public void setActualScene(Scene actualScene) {
        this.actualScene = actualScene;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }
}
