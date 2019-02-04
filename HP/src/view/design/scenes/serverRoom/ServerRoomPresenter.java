package view.design.scenes.serverRoom;


import view.design.MainPresenter;
import javafx.stage.Stage;

/**
 * Created by Rene on 02.01.2016.
 */
public class ServerRoomPresenter {
	
	private MainPresenter mainPresenter;
    private ServerRoomScene serverRoomScene;


    
    public ServerRoomPresenter(MainPresenter mainPresenter) {
    	this.mainPresenter = mainPresenter;

        serverRoomScene = new ServerRoomScene();
        buttonhandle();
        mainPresenter.getScenesmap().put("serverRoomScene",serverRoomScene.getActualScene());

    }
    


    public void buttonhandle() {


        serverRoomScene.getMainMenu().setOnAction(e -> {

            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
        });

    }
    private void selectClass(){

    }


}
