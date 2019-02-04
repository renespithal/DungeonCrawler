package view.designHP.scenes.gameOverHP;

import view.designHP.presenter.MainPresenterHP;

/**
 * Created by Jenny on 01.02.2016.
 */
public class GameOverPresenterHP {

    private MainPresenterHP mainPresenter;
    private GameOverSceneHP scene;

    public GameOverPresenterHP(MainPresenterHP mainPresenter) {
        this.mainPresenter = mainPresenter;
        this.scene = new GameOverSceneHP();
        mainPresenter.getScenesMap().put("GameOverScene", scene.getScene());
        handle();
    }

    private void handle() {

        scene.getBack().setOnAction(e-> {
            mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("menuScene"));

        });

        scene.getBackToMain().setOnAction(e->{

                mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("menuScene"));

        });

    }

    /* Getter & Setter*/

    public MainPresenterHP getMainPresenter() {
        return mainPresenter;
    }

    public void setMainPresenter(MainPresenterHP mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public GameOverSceneHP getScene() {
        return scene;
    }

    public void setScene(GameOverSceneHP scene) {
        this.scene = scene;
    }
}
