package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Chat;
import view.Presenter;
import view.designHP.scenes.choosePlayerHP.ChoosePlayerSceneHP;
import view.designHP.scenes.gamelistHP.GameListSceneHP;


public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception {

       // Stage stage = new Stage();
        //MainPresenter mainPresenter = new MainPresenter(stage);
        Presenter presenter = new Presenter(stage);
        /*
        BorderPane pane = new BorderPane();
        Chat c = new Chat();
        VBox h = new VBox();
        Button b = new Button("SETNOTIFY");
        b.setOnAction(e -> {
            c.setNotify(true);
        });
        h.getChildren().addAll(c.getAll(),c.getIcon(),b);
        stage.setScene(new Scene(h));*/
        // ich glaube man kann icons nur einmal setzen. heisst wir koennen erst bei auswahl der version setzen
        //stage.getIcons().add(new Image("file:resources/images/game.png"));
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

