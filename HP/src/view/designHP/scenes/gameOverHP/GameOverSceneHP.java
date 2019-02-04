package view.designHP.scenes.gameOverHP;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import util.ViewUtilsHP;

/**
 * Created by Jenny on 01.02.2016.
 */
public class GameOverSceneHP extends BorderPane{

    private Scene scene;
    private Button back, backToMain;
    private VBox backy, main;

    public GameOverSceneHP() {
        this.setBottom(createButton());

        scene = ViewUtilsHP.createScene(this);
        ViewUtilsHP.setBackground(this, "hp/media/background/gameOverBG.jpg");
        ViewUtilsHP.configureCSS(scene);
    }

    public VBox createButton() {
        back = ViewUtilsHP.createButton("Back");
        backy = ViewUtilsHP.buttonSurrounding(back);
        backToMain = ViewUtilsHP.createButton("Back to Main");
        main = ViewUtilsHP.buttonSurrounding(backToMain);

        VBox vBox = new VBox(backy, main);
        return vBox;
    }

    /* Getter & Setter */

    public Button getBack() {
        return back;
    }

    public void setBack(Button back) {
        this.back = back;
    }

    public Button getBackToMain() {
        return backToMain;
    }

    public void setBackToMain(Button backToMain) {
        this.backToMain = backToMain;
    }

    public VBox getBacky() {
        return backy;
    }

    public void setBacky(VBox backy) {
        this.backy = backy;
    }

    public VBox getMain() {
        return main;
    }

    public void setMain(VBox main) {
        this.main = main;
    }
}
