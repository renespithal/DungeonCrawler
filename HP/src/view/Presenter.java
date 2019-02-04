package view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.json.JSONException;
import util.ViewUtils;
import view.design.MainPresenter;
import view.designHP.presenter.MainPresenterHP;
import view.designHP.scenes.decision.DecisionScenePresenter;


public class Presenter {
	
	private Stage stage;
	private MainPresenter mainPresenter;
	private MainPresenterHP mainPresenterHP;
	private DecisionScenePresenter decisionScenePresenter;
	private Map<String, Scene> scenesMap;
	private boolean harry;

	public Presenter(Stage stage) {
		this.stage = stage;
		closeStage();
		this.scenesMap = new HashMap<String, Scene>();
		createPresenter();
		this.stage.setScene(scenesMap.get("decisionScene"));
	}

	private void createPresenter() {
		
		decisionScenePresenter = new DecisionScenePresenter(this);

		try {
			mainPresenter = new MainPresenter(stage,this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainPresenterHP = new MainPresenterHP(stage, this);
	}

	public void closeStage() {
		stage.setOnCloseRequest(e -> {
			Alert alert = ViewUtils.alertExit();
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){
				if(isHarry()){
					if(mainPresenterHP.getClient() != null) {
						if(mainPresenterHP.getClient().getConnect().isTcpConnected()) {
							try {
								mainPresenterHP.getClient().getCom().getSend().disconnect();
								mainPresenterHP.getClient().getConnect().setTcpConnected(false);
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
						}

					}
					if(mainPresenterHP.getServer() != null){
						if(!mainPresenterHP.getServer().getRegisteredClients().isEmpty()){
							try {
								mainPresenterHP.getServer().close();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				}else {
					if(mainPresenter.getClient() != null) {
						if(mainPresenter.getClient().getConnect().isTcpConnected()) {
							try {
								mainPresenter.getClient().getCom().getSend().disconnect();
								mainPresenter.getClient().getConnect().setTcpConnected(false);
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
						}

					}
					if(mainPresenter.getServer() != null){
						if(!mainPresenter.getServer().getRegisteredClients().isEmpty()){
							try {
								mainPresenter.getServer().close();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
				stage.close();
				System.exit(0);
			} else if(result.get() == ButtonType.CANCEL){
				e.consume();
			}

		});
	}


	public void serverCloseListener() {
		mainPresenter.getClient().serverErrorProperty().addListener((observable, oldValue, newValue) -> {
			getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
		});
	}

	public void serverCloseListenerHP() {
		mainPresenterHP.getClient().serverErrorProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("in server close listener");
			getStage().setScene(mainPresenterHP.getScenesMap().get("menuScene"));
		});
	}
	
	public Stage getStage() {
		return stage;
	}

	
	public void setStage( Stage stage ) {
		this.stage = stage;
	}

	
	public MainPresenter getMainPresenter() {
		return mainPresenter;
	}

	
	public void setMainPresenter( MainPresenter mainPresenter ) {
		this.mainPresenter = mainPresenter;
	}

	
	public MainPresenterHP getMainPresenterHP() {
		return mainPresenterHP;
	}

	
	public void setMainPresenterHP( MainPresenterHP mainPresenterHP ) {
		this.mainPresenterHP = mainPresenterHP;
	}

	
	public Map<String, Scene> getScenesMap() {
		return scenesMap;
	}

	
	public void setScenesMap( Map<String, Scene> scenesMap ) {
		this.scenesMap = scenesMap;
	}

	public boolean isHarry() {
		return harry;
	}

	public void setHarry(boolean harry) {
		this.harry = harry;
	}
}
