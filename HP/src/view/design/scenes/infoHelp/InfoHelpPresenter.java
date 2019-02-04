package view.design.scenes.infoHelp;

import view.design.MainPresenter;

/**
 * Created by Felix on 17.02.2016.
 */
public class InfoHelpPresenter {

    private MainPresenter mainPresenter;
    private InfoHelpScene infoHelpScene;



    public InfoHelpPresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;

        this.infoHelpScene = new InfoHelpScene();

        mainPresenter.getScenesmap().put("infoHelpScene",infoHelpScene.getActualScene());
        buttonhandle();
    }



    public void buttonhandle(){

        infoHelpScene.getBackButton().setOnAction(e-> {

        mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));

    });

    }

}
