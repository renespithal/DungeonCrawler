package view.design.scenes.singleplayerLobby;

import javafx.stage.Stage;
import view.design.MainPresenter;
import view.design.scenes.MusicPlayer;

/**
 * Created by Rusty on 11.01.2016.
 */
public class SingleplayerGameLobbyPresenter {
    private MainPresenter mainPresenter;
    private SingleplayerGameLobbyScene singleplayerGameLobbyScene;
    private Stage stage;
    private String chosenClass;
    private MusicPlayer mediaplayer = new MusicPlayer();

    public SingleplayerGameLobbyPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
        singleplayerGameLobbyScene = new SingleplayerGameLobbyScene();
        buttonhandle();
      //  this.mainPresenter.getScenesList().add(singleplayerGameLobbyScene.getActualScene());
        mainPresenter.getScenesmap().put("singlePlayerGameLobbyScene",singleplayerGameLobbyScene.getActualScene());

    }

    private void buttonhandle() {
        singleplayerGameLobbyScene.getRanger().setOnAction(e -> {chosenClass="ranger";});
        singleplayerGameLobbyScene.getProf().setOnAction(e -> {chosenClass="professor";});
        singleplayerGameLobbyScene.getMage().setOnAction(e -> {chosenClass="mage";});
        singleplayerGameLobbyScene.getTourist().setOnAction(e -> {chosenClass="tourist";});
        singleplayerGameLobbyScene.getWarrior().setOnAction(e -> {chosenClass="warrior";});
        singleplayerGameLobbyScene.getStudent().setOnAction(e -> {chosenClass="student";});
        singleplayerGameLobbyScene.getBack().setOnAction(e ->{
            mediaplayer.playClick();
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().
                get("menuScene"));});
        singleplayerGameLobbyScene.getStart().setOnAction(e -> {
            mediaplayer.playClick();
            startSingleplayer();});
    }

    private void startSingleplayer() {
        switch (singleplayerGameLobbyScene.x.get()){

            case 0: chosenClass="warrior";break;
            case 1: chosenClass="mage";break;
            case 2: chosenClass="ranger";break;
            case 3: chosenClass="professor";break;
            case 4: chosenClass="student";break;
            case 5: chosenClass="tourist";break;
        }

        mainPresenter.getSingleplayerGamePresenter().startGame(chosenClass);
        mainPresenter.getStage().setScene(mainPresenter.getScenesList().get(0));
        mainPresenter.getStage().setFullScreen(false);
        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("singleplayerGameScene"));


    }
}
