package view.designHP.scenes.startSceneHP;

import javafx.scene.input.KeyCode;
import view.designHP.presenter.MainPresenterHP;

/**
 * Created by Jenny on 27.01.2016.
 */
public class StartScenePresenterHP {

    private MainPresenterHP mainPresenter;
    private StartSceneHP scene;

    public StartScenePresenterHP(MainPresenterHP mainPresenter) {
        this.scene = new StartSceneHP();
        this.mainPresenter = mainPresenter;

        mainPresenter.getScenesMap().put("startScene", scene.getScene());
        handleSceneChange();
    }

    public void handleSceneChange() {
        scene.getScene().setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("menuScene"));
            }
        });

        scene.getScene().setOnMouseClicked(e -> {
            mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("menuScene"));
        });
    }

    /* Getter & Setter */
    public StartSceneHP getScene() {
        return scene;
    }

    public void setScene(StartSceneHP scene) {
        this.scene = scene;
    }
}
