package view.design.scenes.gameOver;

import view.design.MainPresenter;

/**
 * Created by Felix on 17.02.2016.
 */
public class GameWonPresenter {


    private MainPresenter mainPresenter;
    private GameWonScene gameWonScene;


    public  GameWonPresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
        this.gameWonScene = new GameWonScene();

        mainPresenter.getScenesmap().put("GameWonScene",gameWonScene.getActualScene());
        buttonhandle();

    }

    public void buttonhandle(){

        gameWonScene.getExitToMenu().setOnAction(e -> {

            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));


        });

    }
}
