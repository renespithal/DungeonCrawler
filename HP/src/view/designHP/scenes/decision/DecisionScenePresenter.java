package view.designHP.scenes.decision;

import javafx.scene.image.Image;
import view.Presenter;
import view.design.scenes.MusicPlayer;

public class DecisionScenePresenter {

	private Presenter presenter;
	private DecisionScene decisionScene;
	private MusicPlayer mediaplayer;

	public DecisionScenePresenter(Presenter presenter) {

		this.presenter = presenter;
		decisionScene = new DecisionScene();
		buttonhandle();
		presenter.getScenesMap().put("decisionScene", decisionScene.getActualScene());
	}

	public void buttonhandle() {

		decisionScene.getHarryPotter().setOnAction(e -> {
			presenter.setHarry(true);
			presenter.getStage().getIcons().add(new Image("file:resources/hp/media/hedwig.png"));
			presenter.getStage().setScene(presenter.getMainPresenterHP().getScenesMap().get("startScene"));
			hpMusic();
		});

		decisionScene.getDungeonCrawler().setOnAction(e -> {
			presenter.getStage().getIcons().add(new Image("file:resources/images/Skeleton.png"));
			presenter.getStage().setScene(presenter.getMainPresenter().getScenesmap().get("startScreenScene"));
			dcMusic();
		});

	}


	public void hpMusic(){
		mediaplayer = new MusicPlayer();
		mediaplayer.playHarryPotterIntro();
	}

	public void dcMusic(){
		mediaplayer = new MusicPlayer();
		mediaplayer.playIntro();
	}

}
