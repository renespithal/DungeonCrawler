package view.design.scenes.startscreen;

import javafx.scene.input.KeyCode;
import view.design.MainPresenter;

/**
 * Created by Felix on 14.01.2016.
 */
public class StartScenePresenter {

    private MainPresenter mainPresenter;
    private StartScreenScene startScreenScene;


    public StartScenePresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        startScreenScene = new StartScreenScene();
        buttonhandle();
        mainPresenter.getScenesmap().put("startScreenScene",startScreenScene.getStartscene());

    }

    public void buttonhandle() {
        /*startScreenScene.getStart().setOnAction(e -> {

            mainPresenter.getStage().setActualScene(mainPresenter.getScenesmap().get("menuScene"));

        });*/

        startScreenScene.getScene().setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
            }
        });

    }
}
