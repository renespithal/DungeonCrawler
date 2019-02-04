package view.designHP;


import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.designHP.presenter.MainPresenterHP;
import view.designHP.scenes.choosePlayerHP.ChoosePlayerPresenterHP;
import view.designHP.scenes.gameOverHP.GameOverSceneHP;

/**
 * Created by Jenny on 09.01.2016.
 */

public class MainHP extends Application {

    private MainPresenterHP presenter;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //this.presenter = new MainPresenterHP(primaryStage);
        primaryStage.setTitle("Harry Potter - DungeonCrawler");
        primaryStage.getIcons().add(new Image("file:resources/hp/media/hedwig.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
