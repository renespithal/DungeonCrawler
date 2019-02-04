package view.design.scenes.loginRoom;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import network.messages.SpielInfo;


import org.json.JSONException;


import util.ViewUtils;
import view.design.MainPresenter;

/**
 * Created by Rene on 07.01.2016.
 */
public class LoginPresenter {

	private MainPresenter mainPresenter;
	private LoginScene loginScene;
	
	private SimpleBooleanProperty loggedIn = new SimpleBooleanProperty(false);

	public LoginPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
		this.loginScene = new LoginScene();
		buttonDisabling();
		buttonhandle();
		mainPresenter.getScenesmap().put("loginScene", loginScene.getActualScene());
	}

	private void buttonDisabling() {

		loginScene.getNicknameField().textProperty().addListener((obs, oldValue, newValue) -> {
			loginScene.getSubmit().setDisable(newValue.isEmpty());
		});
		
		loggedIn.addListener(( obs, oldValue, newValue ) -> {
			loginScene.getLogin().setDisable(!newValue);
			loginScene.getSubmit().setDisable(true);
		});
		
		
	}

	public void buttonhandle() {

		loginScene.getBackToMenu().setOnAction(e -> {
			/*
			 * String[] s = {"egal"}; mainPresenter.getClient().setNick(
			 * "1!Qx4?/herbert95/cherryCoke15-25harryschnuersenkel-Bikinibottommeerjungfraumann"
			 * ); mainPresenter.getClient().setExpansions(s); try {
			 * mainPresenter.getClient().getCom().getSend().login(); } catch
			 * (IOException e1) { e1.printStackTrace(); } catch (JSONException
			 * e1) { e1.printStackTrace(); }
			 */
			Alert alert = ViewUtils.alertConfirm("Confirm logout", "Do you want to disconnect?");
			Optional<ButtonType> result = alert.showAndWait();
			if( result.get() == ButtonType.OK ) {
				try {
					mainPresenter.getClient().getCom().getSend().disconnect();
					mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});

		Button submit = loginScene.getSubmit();
		submit.setOnAction(e -> {

			mainPresenter.getClient().setNick(loginScene.getNickname());
			mainPresenter.getClient().getCom().getRcv().getClientDecoder().setSelfName(loginScene.getNickname());
			String[] herbert = { "einstring" };
			mainPresenter.getClient().setExpansions(herbert);
			try {
				mainPresenter.getClient().getCom().getSend().login();
				loggedIn.set(true);;
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		});

		loginScene.getLogin().setOnAction(e -> {
			ObservableList<SpielInfo> gameList = mainPresenter.getClient().getGameList();
			if( gameList != null ) {
				mainPresenter.getGameListPresenter().getGameListScene().getGamesTable().setItems(gameList);

				for (SpielInfo spielInfo : gameList) {
					}
				
			} else {
				}
			mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("gameListScene"));
		});
	}

}
