package view.design.scenes.gameOver;

import view.design.MainPresenter;

/**
 * Created by Felix on 17.02.2016.
 */
public class GameOverPresenter {

    private MainPresenter mainPresenter;
    private GameOverScene gameOverScene;

    public GameOverPresenter(MainPresenter mainPresenter){

        this.mainPresenter = mainPresenter;
        gameOverScene = new GameOverScene();

        mainPresenter.getScenesmap().put("GameOverScene",gameOverScene.getActualScene());
        buttonhandle();

    }

    public void buttonhandle(){
        gameOverScene.getExitToMenu().setOnAction(e -> {

        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));


        });


    }


}
